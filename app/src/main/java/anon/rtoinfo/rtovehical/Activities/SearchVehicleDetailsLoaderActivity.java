package anon.rtoinfo.rtovehical.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;


import anon.rtoinfo.rtovehical.Database.SearchVehicleHistoryTableAdapter;
import anon.rtoinfo.rtovehical.Database.VehicleDetailsTableAdapter;
import anon.rtoinfo.rtovehical.R;

import com.google.gson.Gson;
import anon.rtoinfo.rtovehical.datamodels.ExternalVehicleDetails;
import anon.rtoinfo.rtovehical.datamodels.ExternalVehicleDetailsResponse;
import anon.rtoinfo.rtovehical.datamodels.SearchVehicleHistory;
import anon.rtoinfo.rtovehical.datamodels.VehicleDetails;
import anon.rtoinfo.rtovehical.datamodels.VehicleDetailsDatabaseModel;
import anon.rtoinfo.rtovehical.datamodels.VehicleDetailsResponse;
import anon.rtoinfo.rtovehical.handlers.ScraperAsyncTask;
import anon.rtoinfo.rtovehical.handlers.TaskHandler;
import anon.rtoinfo.rtovehical.helpers.GlobalTracker;
import anon.rtoinfo.rtovehical.helpers.ResponseJuicer;
import anon.rtoinfo.rtovehical.helpers.VehicleDetailsLogger;
import anon.rtoinfo.rtovehical.utils.GlobalReferenceEngine;
import anon.rtoinfo.rtovehical.utils.Utils;
import anon.rtoinfo.rtovehical.widget.CustomLoaderScreen;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class SearchVehicleDetailsLoaderActivity extends AppCompatActivity {
    private String actionName;
    public int counter = 0;
    private CustomLoaderScreen customLoaderScreen;
    public int externalCounter = 0;
    private String registrationNo;
    private String type;
    private VehicleDetailsResponse vehicleDetailsResponse;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_search_result_loader);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        this.registrationNo = getIntent().getStringExtra("REGISTRATION_NO");
        this.actionName = getIntent().getStringExtra("ACTION");
        String stringExtra = getIntent().getStringExtra("TYPE");
        this.type = stringExtra;
        if (!Utils.isNullOrEmpty(this.actionName) && this.actionName.equalsIgnoreCase("SAVE")) {
            try {
                SearchVehicleHistory searchVehicleHistory = new SearchVehicleHistory();
                searchVehicleHistory.setRegistrationNo(this.registrationNo);
                new SearchVehicleHistoryTableAdapter(this).insertSearchVehicleHistory(searchVehicleHistory, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.customLoaderScreen = (CustomLoaderScreen) findViewById(R.id.customLoader);
        managePageElements();
    }

    private void managePageElements() {
        String str = this.type;
        if (str == null || str.equalsIgnoreCase("RC")) {
            try {
                VehicleDetailsDatabaseModel readVehicleDetails = new VehicleDetailsTableAdapter(this).readVehicleDetails(this.registrationNo);
                if (readVehicleDetails == null || readVehicleDetails.getData() == null) {
                    startCustomLoader();
                    return;
                }
                this.vehicleDetailsResponse = (VehicleDetailsResponse) new Gson().fromJson(readVehicleDetails.getData(), VehicleDetailsResponse.class);
                Bundle bundle = new Bundle();
                bundle.putString("VIEW_VEHICLE_DETAILS", this.registrationNo + " LOCAL_DB");
                bundle.putString("content_type", "VEHICLE_DETAILS");
                GlobalTracker.from((Context) this).sendViewItemEvent(bundle);
                showOrHideElements(true, false, false, "");
            } catch (Exception unused) {
                if (this.vehicleDetailsResponse == null) {
                    startCustomLoader();
                } else {
                    showOrHideElements(true, false, false, "");
                }
            }
        } else {
            startCustomLoader();
        }
    }

    private void startCustomLoader() {
        this.customLoaderScreen.lambda$setVisibility$2$CustomLoaderScreen(0);
        if (!Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessPoint) && GlobalReferenceEngine.dataAccessPoint.equalsIgnoreCase("WEB")) {
            this.customLoaderScreen.setCallback(new CustomLoaderScreen.Callback() {
                public final void start() {
                    SearchVehicleDetailsLoaderActivity.this.lambda$startCustomLoader$0$SearchVehicleDetailsLoaderActivity();
                }
            });
        } else if (!Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessPoint) && GlobalReferenceEngine.dataAccessPoint.equalsIgnoreCase("EXTERNAL")) {
            this.customLoaderScreen.setCallback(new CustomLoaderScreen.Callback() {
                public final void start() {
                    SearchVehicleDetailsLoaderActivity.this.lambda$startCustomLoader$1$SearchVehicleDetailsLoaderActivity();
                }
            });
        } else if (Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessPoint) || !GlobalReferenceEngine.dataAccessPoint.equalsIgnoreCase("LOCAL") || Utils.isNullOrEmpty(GlobalReferenceEngine.localSourceInitUrl) || Utils.isNullOrEmpty(GlobalReferenceEngine.localSourceFinalUrl) || Utils.isNullOrEmpty(GlobalReferenceEngine.localSourceHostUrl) || Utils.isNullOrEmpty(GlobalReferenceEngine.localSourceField1) || Utils.isNullOrEmpty(GlobalReferenceEngine.localSourceField2)) {
            this.customLoaderScreen.setCallback(new CustomLoaderScreen.Callback() {
                public final void start() {
                    SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
                }
            });
        } else {
            this.customLoaderScreen.setCallback(new CustomLoaderScreen.Callback() {
                public final void start() {
                    SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
                }
            });
        }
    }

    public void lambda$startCustomLoader$0$SearchVehicleDetailsLoaderActivity() {
        loadWebServerData(true);
    }

    public void lambda$startCustomLoader$1$SearchVehicleDetailsLoaderActivity() {
        loadExternalServerData(false);
    }

    public void loadWebServerData(boolean z) {
        if (!Utils.isNetworkConnected(this)) {
            showOrHideElements(false, false, false, "");
            return;
        }
        TaskHandler.newInstance().fetchVehicleDetails(this, this.registrationNo, false, z, false, new TaskHandler.ResponseHandler<JSONObject>() {
            public void onError(String str) {
                SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
            }

            public void onResponse(JSONObject jSONObject) {
                SearchVehicleDetailsLoaderActivity.this.manipulateJsonResponse(jSONObject);
            }
        });
    }

    public void manipulateJsonResponse(JSONObject jSONObject) {
        String str;
        try {
            this.vehicleDetailsResponse = (VehicleDetailsResponse) new Gson().fromJson(jSONObject.toString(), VehicleDetailsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str2 = this.registrationNo + " INTERNAL_SERVER ";
        VehicleDetailsResponse vehicleDetailsResponse2 = this.vehicleDetailsResponse;
        if (vehicleDetailsResponse2 == null) {
            str = str2.concat("500 ").concat("Null Response");
        } else {
            str = str2.concat(String.valueOf(vehicleDetailsResponse2.getStatusCode())).concat(" ").concat(this.vehicleDetailsResponse.getStatusMessage());
        }
        Bundle bundle = new Bundle();
        bundle.putString("VIEW_VEHICLE_DETAILS", str);
        bundle.putString("content_type", "VEHICLE_DETAILS");
        GlobalTracker.from((Context) this).sendViewItemEvent(bundle);
        VehicleDetailsResponse vehicleDetailsResponse3 = this.vehicleDetailsResponse;
        if (vehicleDetailsResponse3 == null || vehicleDetailsResponse3.getStatusCode() != 200) {
            VehicleDetailsResponse vehicleDetailsResponse4 = this.vehicleDetailsResponse;
            if (vehicleDetailsResponse4 == null || vehicleDetailsResponse4.getStatusCode() <= 200 || this.vehicleDetailsResponse.getStatusCode() >= 500 || this.vehicleDetailsResponse.getStatusCode() == 404) {
                VehicleDetailsResponse vehicleDetailsResponse5 = this.vehicleDetailsResponse;
                if (vehicleDetailsResponse5 == null || vehicleDetailsResponse5.getStatusCode() != 404) {
                    loadLocalSourceData();
                } else if (this.vehicleDetailsResponse.isExtra() && this.externalCounter < 1 && !Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessUrl) && !Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessKey) && !Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessParams)) {
                    loadExternalServerData(true);
                } else if (this.externalCounter == 1) {
                    loadLocalSourceData();
                } else {
                    showOrHideElements(true, false, true, "");
                }
            } else {
                loadLocalSourceData();
            }
        } else if (this.vehicleDetailsResponse.getDetails() == null) {
            showOrHideElements(true, false, true, "");
        } else {
            try {
                new VehicleDetailsTableAdapter(this).saveVehicleDetails(this.registrationNo, jSONObject.toString());
                SearchVehicleHistoryTableAdapter searchVehicleHistoryTableAdapter = new SearchVehicleHistoryTableAdapter(this);
                SearchVehicleHistory searchVehicleHistoryByDetails = searchVehicleHistoryTableAdapter.getSearchVehicleHistoryByDetails(this.registrationNo, true);
                if (searchVehicleHistoryByDetails == null) {
                    searchVehicleHistoryByDetails = new SearchVehicleHistory();
                    searchVehicleHistoryByDetails.setRegistrationNo(this.registrationNo);
                }
                searchVehicleHistoryByDetails.setName(this.vehicleDetailsResponse.getDetails().getOwnerName());
                searchVehicleHistoryTableAdapter.insertSearchVehicleHistory(searchVehicleHistoryByDetails, true);
                showOrHideElements(true, false, false, "");
            } catch (Exception e2) {
//                FirebaseCrashlytics.getInstance().recordException(e2);
                loadLocalSourceData();
            }
        }
    }

    public void manipulateExternalResponse(ExternalVehicleDetailsResponse externalVehicleDetailsResponse) {
        String str;
        ExternalVehicleDetails result = externalVehicleDetailsResponse.getResult();
        String str2 = this.registrationNo + " EXTERNAL_SERVER ";
        if (result == null) {
            str = str2.concat("500 ").concat("Null Response");
        } else if (result.isEmptyResponse()) {
            str = str2.concat("404 ").concat("Vehicle Info Not Found");
        } else {
            str = str2.concat(String.valueOf(externalVehicleDetailsResponse.getStatusCode())).concat(" ").concat(externalVehicleDetailsResponse.getStatusMessage());
        }
        Bundle bundle = new Bundle();
        bundle.putString("VIEW_VEHICLE_DETAILS", str);
        bundle.putString("content_type", "VEHICLE_DETAILS");
        GlobalTracker.from((Context) this).sendViewItemEvent(bundle);
        if (externalVehicleDetailsResponse.getStatusCode() != 200 || result == null) {
            if (externalVehicleDetailsResponse.getStatusCode() > 200 && externalVehicleDetailsResponse.getStatusCode() < 500 && externalVehicleDetailsResponse.getStatusCode() != 404) {
                loadLocalSourceData();
            } else if (externalVehicleDetailsResponse.getStatusCode() == 404) {
                showOrHideElements(true, false, true, "");
            } else {
                loadLocalSourceData();
            }
        } else if (result.isEmptyResponse()) {
            showOrHideElements(true, false, true, "");
        } else {
            try {
                VehicleDetailsLogger.logVehicleDetails(this, result.convertInto());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                VehicleDetailsTableAdapter vehicleDetailsTableAdapter = new VehicleDetailsTableAdapter(this);
                this.vehicleDetailsResponse = new VehicleDetailsResponse(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, "Success", result.convertInto());
                vehicleDetailsTableAdapter.saveVehicleDetails(this.registrationNo, new Gson().toJson((Object) this.vehicleDetailsResponse, (Type) VehicleDetailsResponse.class));
                SearchVehicleHistoryTableAdapter searchVehicleHistoryTableAdapter = new SearchVehicleHistoryTableAdapter(this);
                SearchVehicleHistory searchVehicleHistoryByDetails = searchVehicleHistoryTableAdapter.getSearchVehicleHistoryByDetails(this.registrationNo, true);
                if (searchVehicleHistoryByDetails == null) {
                    searchVehicleHistoryByDetails = new SearchVehicleHistory();
                    searchVehicleHistoryByDetails.setRegistrationNo(this.registrationNo);
                }
                searchVehicleHistoryByDetails.setName(result.getOwnerName());
                searchVehicleHistoryTableAdapter.insertSearchVehicleHistory(searchVehicleHistoryByDetails, true);
                showOrHideElements(true, false, false, "");
            } catch (Exception e2) {
                e2.printStackTrace();
                loadLocalSourceData();
            }
        }
    }

    private void loadExternalServerData(boolean z) {
        if (!Utils.isNetworkConnected(this)) {
            showOrHideElements(false, false, false, "");
            return;
        }
        if (z) {
            this.externalCounter++;
        }
        TaskHandler.newInstance().fetchVehicleDetails((Context) this, this.registrationNo, GlobalReferenceEngine.dataAccessParams.split(" "), false, (TaskHandler.ResponseHandler<String>) new TaskHandler.ResponseHandler<String>() {
            public void onError(String str) {
                SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
            }

            public void onResponse(String str) {
                if (Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessRouter) || GlobalReferenceEngine.dataAccessRouter.equalsIgnoreCase("EXTERNAL")) {
                    SearchVehicleDetailsLoaderActivity.this.fetchVehicleDetails(str);
                    return;
                }
                ExternalVehicleDetailsResponse responseJuice = ResponseJuicer.responseJuice(str);
                if (responseJuice == null) {
                    SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
                } else {
                    SearchVehicleDetailsLoaderActivity.this.manipulateExternalResponse(responseJuice);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void fetchVehicleDetails(final String str) {
        if (!Utils.isNetworkConnected(this)) {
            showOrHideElements(false, false, false, "");
            return;
        }
        TaskHandler.newInstance().fetchVehicleDetails((Context) this, this.registrationNo, str, false, (TaskHandler.ResponseHandler<JSONObject>) new TaskHandler.ResponseHandler<JSONObject>() {
            public void onError(String str) {
                ExternalVehicleDetailsResponse responseJuice = ResponseJuicer.responseJuice(str);
                if (responseJuice == null) {
                    SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
                } else {
                    SearchVehicleDetailsLoaderActivity.this.manipulateExternalResponse(responseJuice);
                }
            }

            public void onResponse(JSONObject jSONObject) {
                SearchVehicleDetailsLoaderActivity.this.manipulateJsonResponse(jSONObject);
            }
        });
    }

    public void loadLocalSourceData() {
        this.counter++;
        String[] splitRegistrationNo = Utils.splitRegistrationNo(this.registrationNo);
        new ScraperAsyncTask(this, "", new ScraperAsyncTask.IResponseCallback() {
            public void lambda$onNotFound$0$SearchVehicleDetailsLoaderActivity$4() {
                SearchVehicleDetailsLoaderActivity.this.showOrHideElements(true, false, true, "");
            }

            public void onNotFound() {
                SearchVehicleDetailsLoaderActivity.this.runOnUiThread(new Runnable() {
                    public final void run() {
                        lambda$onNotFound$0$SearchVehicleDetailsLoaderActivity$4();
                    }
                });
            }

            public void onError(String str) {
                SearchVehicleDetailsLoaderActivity.this.runOnUiThread(new Runnable() {
                    public final void run() {
                        lambda$onError$2$SearchVehicleDetailsLoaderActivity$4(str);
                    }
                });
            }

            public void lambda$onError$2$SearchVehicleDetailsLoaderActivity$4(String str) {
                if (SearchVehicleDetailsLoaderActivity.this.counter >= 2 || SearchVehicleDetailsLoaderActivity.this.externalCounter > 0) {
                    SearchVehicleDetailsLoaderActivity.this.runOnUiThread(new Runnable() {

                        public final void run() {
                            lambda$null$1$SearchVehicleDetailsLoaderActivity$4(str);
                        }
                    });
                } else {
                    SearchVehicleDetailsLoaderActivity.this.loadWebServerData(false);
                }
            }

            public /* synthetic */ void lambda$null$1$SearchVehicleDetailsLoaderActivity$4(String str) {
                SearchVehicleDetailsLoaderActivity.this.showOrHideElements(true, true, false, str);
            }

            public /* synthetic */ void lambda$onResponse$3$SearchVehicleDetailsLoaderActivity$4(VehicleDetails vehicleDetails) {
                SearchVehicleDetailsLoaderActivity.this.manipulateResponse(vehicleDetails);
            }

            public void onResponse(VehicleDetails vehicleDetails) {
                SearchVehicleDetailsLoaderActivity.this.runOnUiThread(new Runnable() {

                    public final void run() {
                        lambda$onResponse$3$SearchVehicleDetailsLoaderActivity$4(vehicleDetails);
                    }
                });
            }
        }).execute(new String[]{splitRegistrationNo[0], splitRegistrationNo[1]});
    }

    /* access modifiers changed from: private */
    public void manipulateResponse(VehicleDetails vehicleDetails) {
        String str;
        String str2 = this.registrationNo + " LOCAL_SOURCE ";
        if (vehicleDetails == null) {
            str = str2.concat("500 ").concat("Null Response");
        } else if (vehicleDetails.isEmptyResponse()) {
            str = str2.concat("404 ").concat("Vehicle Info Not Found");
        } else {
            str = str2.concat("200 ").concat("Success");
        }
        Bundle bundle = new Bundle();
        bundle.putString("VIEW_VEHICLE_DETAILS", str);
        bundle.putString("content_type", "VEHICLE_DETAILS");
        GlobalTracker.from((Context) this).sendViewItemEvent(bundle);
        if (vehicleDetails == null || vehicleDetails.isEmptyResponse()) {
            showOrHideElements(true, false, true, "");
            return;
        }
        try {
            VehicleDetailsLogger.logVehicleDetails(this, vehicleDetails);
        } catch (Exception e) {
//            FirebaseCrashlytics.getInstance().recordException(e);
        }
        try {
            this.vehicleDetailsResponse = new VehicleDetailsResponse(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, "Success", vehicleDetails);
            new VehicleDetailsTableAdapter(this).saveVehicleDetails(this.registrationNo, new Gson().toJson((Object) this.vehicleDetailsResponse, (Type) VehicleDetailsResponse.class));
            SearchVehicleHistoryTableAdapter searchVehicleHistoryTableAdapter = new SearchVehicleHistoryTableAdapter(this);
            SearchVehicleHistory searchVehicleHistoryByDetails = searchVehicleHistoryTableAdapter.getSearchVehicleHistoryByDetails(this.registrationNo, true);
            if (searchVehicleHistoryByDetails == null) {
                searchVehicleHistoryByDetails = new SearchVehicleHistory();
                searchVehicleHistoryByDetails.setRegistrationNo(this.registrationNo);
            }
            searchVehicleHistoryByDetails.setName(vehicleDetails.getOwnerName());
            searchVehicleHistoryTableAdapter.insertSearchVehicleHistory(searchVehicleHistoryByDetails, true);
            showOrHideElements(true, false, false, "");
        } catch (Exception e2) {
            e2.printStackTrace();
            loadWebServerData(false);
        }
    }

    /* access modifiers changed from: private */
    public void showOrHideElements(boolean z, boolean z2, boolean z3, String str) {
        Intent intent;
        CustomLoaderScreen customLoaderScreen2 = this.customLoaderScreen;
        if (customLoaderScreen2 != null && customLoaderScreen2.isLoadingStarted()) {
            this.customLoaderScreen.finishLoading();
        }
        if (!Utils.isNullOrEmpty(this.type) && this.type.equalsIgnoreCase("FINANCE")) {
            intent = new Intent(this, VehicleDetailsActivity.class);
        } else {
            intent = new Intent(this, VehicleDetailsActivity.class);
        }
        intent.putExtra("REGISTRATION_NO", this.registrationNo);
        intent.putExtra("ACTION", this.actionName);
        intent.putExtra("TYPE", this.type);
        intent.putExtra("VEHICLE_DETAILS_DATA", this.vehicleDetailsResponse);
        if (!z) {
            intent.putExtra("data_fetch_status", "no_internet");
        } else if (z3) {
            intent.putExtra("data_fetch_status", "no_data_available");
            intent.putExtra("data_fetch_status_message", str);
        } else if (z2) {
            intent.putExtra("data_fetch_status", "error");
            intent.putExtra("data_fetch_status_message", str);
        } else {
            intent.putExtra("data_fetch_status", "data_available");
        }
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
