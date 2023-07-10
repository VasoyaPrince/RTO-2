package anon.rtoinfo.rtovehical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import anon.rtoinfo.rtovehical.R;

import anon.rtoinfo.rtovehical.datamodels.VehicleDetails;
import anon.rtoinfo.rtovehical.datamodels.VehicleDetailsResponse;
import anon.rtoinfo.rtovehical.helpers.ToastHelper;
import anon.rtoinfo.rtovehical.utils.PreferencesHelper;
import anon.rtoinfo.rtovehical.utils.Utils;
import com.squareup.picasso.Picasso;

public class VehicleDetailsActivity extends AppCompatActivity {
    private String actionName;
    private TextView btnAction;
    private CardView btnCheckHiddenFinancierValue;
    private CardView btnCheckHiddenInsuranceCompanyValue;
    private CardView btnVehicleDetails;
    private LinearLayout contentLayout;
    private LinearLayout cvBodyTypeDesc;
    private LinearLayout cvChallan;
    private LinearLayout cvFinancier;
    private LinearLayout cvFitnessUpto;
    private LinearLayout cvInsuranceCompany;
    private LinearLayout cvInsuranceUpto;
    private LinearLayout cvManufactureMonthYear;
    private LinearLayout cvOwnerPopularity;
    private LinearLayout cvOwnership;
    private LinearLayout cvPromotion;
    private LinearLayout cvPuccUpto;
    private LinearLayout cvRCStatus;
    private LinearLayout cvRoadTaxPaidUpto;
    private LinearLayout cvSeatCapacity;
    private LinearLayout cvUnloadWeight;
    private LinearLayout cvVehicleAge;
    private LinearLayout cvVehicleColor;
    private LinearLayout cvVehicleInfo;
    private String dataFetchStatus;
    private String dataFetchStatusMessage;
    private LinearLayout errorContainer;
    private ImageView errorImage;
    private LinearLayout financierValueHiddenContainer;
    private LinearLayout insuranceCompanyValueHiddenContainer;
    private ImageView ivVehicleImage;
    Handler mHandler = new Handler();
    private RecyclerView recyclerView;
    private String registrationNo;
    private VehicleDetailsResponse response;
    private TextView txvBodyTypeDescValue;
    private TextView txvChassisNoValue;
    private TextView txvEngineNoValue;
    private TextView txvFinancierValue;
    private TextView txvFinancierValueHidden;
    private TextView txvFitnessUptoValue;
    private TextView txvFuelNormsValue;
    private TextView txvFuelTypeValue;
    private TextView txvInsuranceCompanyValue;
    private TextView txvInsuranceCompanyValueHidden;
    private TextView txvInsuranceUptoValue;
    private TextView txvMakerModelValue;
    private TextView txvManufactureMonthYearValue;
    private TextView txvOwnerNameValue;
    private TextView txvOwnerPopularityValue;
    private TextView txvOwnershipValue;
    private TextView txvPuccUptoValue;
    private TextView txvRCStatusValue;
    private TextView txvRegAuthorityValue;
    private TextView txvRegDateValue;
    private TextView txvRegistrationNoValue;
    private TextView txvRoadTaxPaidUptoValue;
    private TextView txvSeatCapacityValue;
    private TextView txvSubTitle;
    private TextView txvTitle;
    private TextView txvUnloadWeightValue;
    private TextView txvVehicleAgeValue;
    private TextView txvVehicleBrandName;
    private TextView txvVehicleClassValue;
    private TextView txvVehicleColorValue;
    private TextView txvVehicleModelName;
    TextView action_bar_title;
    ImageView back;
    private String type;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_vehicle_details);

        this.registrationNo = getIntent().getStringExtra("REGISTRATION_NO");
        this.actionName = getIntent().getStringExtra("ACTION");
        this.type = getIntent().getStringExtra("TYPE");
        this.dataFetchStatus = getIntent().getStringExtra("data_fetch_status");
        this.dataFetchStatusMessage = getIntent().getStringExtra("data_fetch_status_message");
        this.response = (VehicleDetailsResponse) getIntent().getSerializableExtra("VEHICLE_DETAILS_DATA");

        this.errorContainer = findViewById(R.id.errorContainer);
        this.contentLayout = (LinearLayout) findViewById(R.id.contentLayout);
        this.errorImage = (ImageView) findViewById(R.id.errorImageView);
        this.txvTitle = (TextView) findViewById(R.id.titleTextView);
        this.txvSubTitle = (TextView) findViewById(R.id.subtitleTextView);
        this.btnAction = (TextView) findViewById(R.id.actionButton);
        this.cvInsuranceUpto = (LinearLayout) findViewById(R.id.cvInsuranceUpto);
        this.cvInsuranceCompany = (LinearLayout) findViewById(R.id.cvInsuranceCompany);
        this.cvOwnership = (LinearLayout) findViewById(R.id.cvOwnership);
        this.cvFinancier = (LinearLayout) findViewById(R.id.cvFinancier);
        this.cvVehicleColor = (LinearLayout) findViewById(R.id.cvVehicleColor);
        this.cvSeatCapacity = (LinearLayout) findViewById(R.id.cvSeatCapacity);
        this.cvVehicleAge = (LinearLayout) findViewById(R.id.cvVehicleAge);
        this.cvVehicleInfo = (LinearLayout) findViewById(R.id.cvVehicleInfo);
        this.cvRoadTaxPaidUpto = (LinearLayout) findViewById(R.id.cvRoadTaxPaidUpto);
        this.cvOwnerPopularity = (LinearLayout) findViewById(R.id.cvOwnerPopularity);
        this.btnVehicleDetails = (CardView) findViewById(R.id.btnVehicleDetails);
        this.cvPuccUpto = (LinearLayout) findViewById(R.id.cvPuccUpto);
        this.cvUnloadWeight = (LinearLayout) findViewById(R.id.cvUnloadWeight);
        this.cvBodyTypeDesc = (LinearLayout) findViewById(R.id.cvBodyTypeDesc);
        this.cvManufactureMonthYear = (LinearLayout) findViewById(R.id.cvManufactureMonthYear);
        this.cvRCStatus = (LinearLayout) findViewById(R.id.cvRCStatus);
        this.cvFitnessUpto = (LinearLayout) findViewById(R.id.cvFitnessUpto);
        this.txvOwnerNameValue = (TextView) findViewById(R.id.ownerNameValue);
        this.txvMakerModelValue = (TextView) findViewById(R.id.makerModelValue);
        this.txvRegDateValue = (TextView) findViewById(R.id.regDateValue);
        this.txvFuelTypeValue = (TextView) findViewById(R.id.fuelTypeValue);
        this.txvVehicleClassValue = (TextView) findViewById(R.id.vehicleClassValue);
        this.txvRegAuthorityValue = (TextView) findViewById(R.id.regAuthorityValue);
        this.txvEngineNoValue = (TextView) findViewById(R.id.engineNoValue);
        this.txvChassisNoValue = (TextView) findViewById(R.id.chassisNoValue);
        this.txvInsuranceUptoValue = (TextView) findViewById(R.id.insuranceUptoValue);
        this.txvInsuranceCompanyValue = (TextView) findViewById(R.id.insuranceCompanyValue);
        this.txvFitnessUptoValue = (TextView) findViewById(R.id.fitnessUptoValue);
        this.txvFuelNormsValue = (TextView) findViewById(R.id.fuelNormsValue);
        this.txvOwnershipValue = (TextView) findViewById(R.id.ownershipValue);
        this.txvVehicleColorValue = (TextView) findViewById(R.id.vehicleColorValue);
        this.txvSeatCapacityValue = (TextView) findViewById(R.id.seatCapacityValue);
        this.txvRegistrationNoValue = (TextView) findViewById(R.id.registrationNoValue);
        this.txvVehicleAgeValue = (TextView) findViewById(R.id.vehicleAgeValue);
        this.txvVehicleModelName = (TextView) findViewById(R.id.txvVehicleModelName);
        this.txvVehicleBrandName = (TextView) findViewById(R.id.txvVehicleBrandName);
        this.txvRoadTaxPaidUptoValue = (TextView) findViewById(R.id.roadTaxPaidUptoValue);
        this.txvOwnerPopularityValue = (TextView) findViewById(R.id.ownerPopularityValue);
        this.txvFinancierValue = (TextView) findViewById(R.id.financierValue);
        this.txvPuccUptoValue = (TextView) findViewById(R.id.puccUptoValue);
        this.txvUnloadWeightValue = (TextView) findViewById(R.id.unloadWeightValue);
        this.txvBodyTypeDescValue = (TextView) findViewById(R.id.bodyTypeDescValue);
        this.action_bar_title = (TextView) findViewById(R.id.action_bar_title);
        this.txvManufactureMonthYearValue = (TextView) findViewById(R.id.manufactureMonthYearValue);
        this.txvRCStatusValue = (TextView) findViewById(R.id.rcStatusValue);
        this.financierValueHiddenContainer = (LinearLayout) findViewById(R.id.financierValueHiddenContainer);
        this.insuranceCompanyValueHiddenContainer = (LinearLayout) findViewById(R.id.insuranceCompanyValueHiddenContainer);
        this.txvFinancierValueHidden = (TextView) findViewById(R.id.financierValueHidden);
        this.txvInsuranceCompanyValueHidden = (TextView) findViewById(R.id.insuranceCompanyValueHidden);
        this.btnCheckHiddenFinancierValue = (CardView) findViewById(R.id.btnCheckHiddenFinancierValue);
        this.btnCheckHiddenInsuranceCompanyValue = (CardView) findViewById(R.id.btnCheckHiddenInsuranceCompanyValue);
        this.ivVehicleImage = (ImageView) findViewById(R.id.ivVehicleImage);
        this.back = (ImageView) findViewById(R.id.back);
        action_bar_title.setText(!Utils.isNullOrEmpty(this.registrationNo) ? this.registrationNo : "");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        managePageElements();
    }

    private void managePageElements() {
        if (Utils.isNullOrEmpty(this.dataFetchStatus)) {
            startSearchVehicleDetailsLoaderActivity();
        } else if (this.dataFetchStatus.equalsIgnoreCase("no_internet")) {
            showOrHideElements(false, false, getString(R.string.app_internet_msg));
            this.errorImage.setImageResource(R.drawable.wifi2);
            this.txvTitle.setText(getString(R.string.txt_connection_error_title));
            this.txvSubTitle.setText(getString(R.string.no_network_message));
            this.btnAction.setText(getString(R.string.btn_retry));
            this.btnAction.setVisibility(0);
            this.btnAction.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                   VehicleDetailsActivity.this.lambda$managePageElements$0$VehicleDetailsActivity(view);
                }
            });
        } else if (this.dataFetchStatus.equalsIgnoreCase("no_data_available")) {
            showOrHideElements(true, false, this.dataFetchStatusMessage);
            this.errorImage.setImageResource(R.drawable.empty_folder);
            this.txvTitle.setText(getString(R.string.oops));
            this.txvSubTitle.setText(getString(R.string.no_result_found_info));
            this.btnAction.setText(getString(R.string.btn_go_back_search_again));
            this.btnAction.setVisibility(0);
            this.btnAction.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    VehicleDetailsActivity.this.lambda$managePageElements$1$VehicleDetailsActivity(view);
                }
            });
        } else if (this.dataFetchStatus.equalsIgnoreCase("error")) {
            showOrHideElements(true, false, this.dataFetchStatusMessage);
            if (Utils.isNullOrEmpty(this.dataFetchStatusMessage)) {
                this.txvTitle.setText(getString(R.string.oops));
                this.txvSubTitle.setText(getString(R.string.no_info));
            } else {
                this.txvTitle.setText(getString(R.string.oops));
                this.txvSubTitle.setText(this.dataFetchStatusMessage);
            }
            this.errorImage.setImageResource(R.drawable.bug);
            this.btnAction.setText(getString(R.string.btn_try_again));
            this.btnAction.setVisibility(0);
            this.btnAction.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    VehicleDetailsActivity.this.lambda$managePageElements$2$VehicleDetailsActivity(view);
                }
            });
        } else {
            VehicleDetailsResponse vehicleDetailsResponse = this.response;
            if (vehicleDetailsResponse == null || vehicleDetailsResponse.getDetails() == null) {
                showOrHideElements(true, false, this.dataFetchStatusMessage);
                this.errorImage.setImageResource(R.drawable.surprised);
                this.txvTitle.setText(getString(R.string.oops));
                this.txvSubTitle.setText(getString(R.string.no_result_found_info));
                this.btnAction.setText(getString(R.string.btn_go_back_search_again));
                this.btnAction.setVisibility(0);
                this.btnAction.setOnClickListener(new View.OnClickListener() {
                    public final void onClick(View view) {
                        VehicleDetailsActivity.this.lambda$managePageElements$3$VehicleDetailsActivity(view);
                    }
                });
                return;
            }
            handleResponse(this.response.getDetails());
            showOrHideElements(true, true, "");
        }
    }

    public void lambda$managePageElements$0$VehicleDetailsActivity(View view) {
        startSearchVehicleDetailsLoaderActivity();
    }

    public void lambda$managePageElements$1$VehicleDetailsActivity(View view) {
        onBackPressed();
    }

    public void lambda$managePageElements$2$VehicleDetailsActivity(View view) {
        startSearchVehicleDetailsLoaderActivity();
    }

    public void lambda$managePageElements$3$VehicleDetailsActivity(View view) {
        onBackPressed();
    }

    private void startSearchVehicleDetailsLoaderActivity() {
        this.dataFetchStatus = "";
        if (!Utils.isNetworkConnected(this)) {
            showOrHideElements(false, false, getString(R.string.app_internet_msg));
            return;
        }
        Intent intent = new Intent(this, SearchVehicleDetailsLoaderActivity.class);
        intent.putExtra("REGISTRATION_NO", this.registrationNo);
        intent.putExtra("ACTION", this.actionName);
        intent.putExtra("TYPE", this.type);
        startActivity(intent);
        finish();
    }

    private void handleResponse(VehicleDetails vehicleDetails) {
        if (vehicleDetails == null) {
            showOrHideElements(true, false, getString(R.string.error_message));
            return;
        }
//        setupCoverUi(vehicleDetails);
        this.txvOwnerNameValue.setText(vehicleDetails.getOwnerName());
        this.txvMakerModelValue.setText(vehicleDetails.getMakerModel());
        this.txvRegDateValue.setText(vehicleDetails.getRegistrationDate());
        this.txvRegistrationNoValue.setText(vehicleDetails.getRegistrationNo());
        this.txvFuelTypeValue.setText(vehicleDetails.getFuelType());
        this.txvVehicleClassValue.setText(vehicleDetails.getVehicleClass());
        this.txvRegAuthorityValue.setText(vehicleDetails.getRegistrationAuthority());
        this.txvEngineNoValue.setText(vehicleDetails.getEngineNo());
        this.txvChassisNoValue.setText(vehicleDetails.getChassisNo());
        String vehicleAge = Utils.getVehicleAge(vehicleDetails.getRegistrationDate());
        if (Utils.isNullOrEmpty(vehicleAge)) {
            this.cvVehicleAge.setVisibility(8);
        } else {
            this.cvVehicleAge.setVisibility(0);
            this.txvVehicleAgeValue.setText(vehicleAge);
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getInsuranceUpto())) {
            this.cvInsuranceUpto.setVisibility(8);
        } else {
            this.cvInsuranceUpto.setVisibility(0);
            this.txvInsuranceUptoValue.setText(vehicleDetails.getInsuranceUpto());
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getInsuranceCompany())) {
            this.cvInsuranceCompany.setVisibility(8);
        } else {
            this.cvInsuranceCompany.setVisibility(0);
            this.txvInsuranceCompanyValue.setText(vehicleDetails.getInsuranceCompany());
            this.txvInsuranceCompanyValueHidden.setText(Utils.hideString(vehicleDetails.getInsuranceCompany()));
            if (!PreferencesHelper.isClickToSeeAvailable()) {
                this.insuranceCompanyValueHiddenContainer.setVisibility(8);
                this.txvInsuranceCompanyValue.setVisibility(0);
            } else {
                this.insuranceCompanyValueHiddenContainer.setVisibility(0);
                this.txvInsuranceCompanyValue.setVisibility(8);

            }
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getFitnessUpto())) {
            this.cvFitnessUpto.setVisibility(8);
        } else {
            this.cvFitnessUpto.setVisibility(0);
            this.txvFitnessUptoValue.setText(vehicleDetails.getFitnessUpto());
        }
        this.txvFuelNormsValue.setText(vehicleDetails.getFuelNorms());
        if (Utils.isNullOrEmpty(vehicleDetails.getOwnership())) {
            this.cvOwnership.setVisibility(8);
        } else {
            this.cvOwnership.setVisibility(0);
            if (Utils.isNullOrEmpty(vehicleDetails.getOwnershipDesc())) {
                this.txvOwnershipValue.setText(vehicleDetails.getOwnership());
            } else {
                this.txvOwnershipValue.setText(vehicleDetails.getOwnershipDesc());
            }
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getFinancierName())) {
            this.cvFinancier.setVisibility(8);
        } else {
            this.cvFinancier.setVisibility(0);
            this.txvFinancierValue.setText(vehicleDetails.getFinancierName());
            this.txvFinancierValueHidden.setText(Utils.hideString(vehicleDetails.getInsuranceCompany()));
            if (!PreferencesHelper.isClickToSeeAvailable() ) {
                this.financierValueHiddenContainer.setVisibility(8);
                this.txvFinancierValue.setVisibility(0);
            } else {
                this.financierValueHiddenContainer.setVisibility(0);
                this.txvFinancierValue.setVisibility(8);
            }
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getVehicleColor())) {
            this.cvVehicleColor.setVisibility(8);
        } else {
            this.cvVehicleColor.setVisibility(0);
            this.txvVehicleColorValue.setText(vehicleDetails.getVehicleColor());
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getSeatCapacity())) {
            this.cvSeatCapacity.setVisibility(8);
        } else {
            this.cvSeatCapacity.setVisibility(0);
            this.txvSeatCapacityValue.setText(vehicleDetails.getSeatCapacity());
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getRoadTaxPaidUpto())) {
            this.cvRoadTaxPaidUpto.setVisibility(8);
        } else {
            this.cvRoadTaxPaidUpto.setVisibility(0);
            this.txvRoadTaxPaidUptoValue.setText(vehicleDetails.getRoadTaxPaidUpto());
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getPucUpto())) {
            this.cvPuccUpto.setVisibility(8);
        } else {
            this.cvPuccUpto.setVisibility(0);
            this.txvPuccUptoValue.setText(vehicleDetails.getPucUpto());
        }
        if (vehicleDetails.getSearchCount() <= 1) {
            this.cvOwnerPopularity.setVisibility(8);
        } else {
            this.cvOwnerPopularity.setVisibility(0);
            this.txvOwnerPopularityValue.setText(getString(R.string.format_views, new Object[]{Integer.valueOf(vehicleDetails.getSearchCount())}));
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getUnloadWeight())) {
            this.cvUnloadWeight.setVisibility(8);
        } else {
            this.cvUnloadWeight.setVisibility(0);
            this.txvUnloadWeightValue.setText(vehicleDetails.getUnloadWeight());
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getBodyTypeDesc())) {
            this.cvBodyTypeDesc.setVisibility(8);
        } else {
            this.cvBodyTypeDesc.setVisibility(0);
            this.txvBodyTypeDescValue.setText(vehicleDetails.getBodyTypeDesc());
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getManufactureMonthYear())) {
            this.cvManufactureMonthYear.setVisibility(8);
        } else {
            this.cvManufactureMonthYear.setVisibility(0);
            this.txvManufactureMonthYearValue.setText(vehicleDetails.getManufactureMonthYear());
        }
        if (Utils.isNullOrEmptyOrNA(vehicleDetails.getRcStatus())) {
            this.cvRCStatus.setVisibility(8);
        } else {
            this.cvRCStatus.setVisibility(0);
            this.txvRCStatusValue.setText(vehicleDetails.getRcStatus());
        }
        if (vehicleDetails.getVehicleInfo() == null || Utils.isNullOrEmpty(vehicleDetails.getVehicleType())) {
            this.cvVehicleInfo.setVisibility(8);
        } else {
            this.cvVehicleInfo.setVisibility(0);
            int i = vehicleDetails.getVehicleType().equalsIgnoreCase("car_models") ? R.drawable.ic_default_car : R.drawable.ic_default_bike;
            try {
                Picasso.with(this).load(vehicleDetails.getVehicleInfo().getImageUrl()).error(i).placeholder(i).into(this.ivVehicleImage);
            } catch (Exception unused) {
                Picasso.with(this).load(i).error(i).placeholder(i).into(this.ivVehicleImage);
            }
            this.txvVehicleModelName.setText(vehicleDetails.getVehicleInfo().getModelName());
            this.txvVehicleBrandName.setText(getString(R.string.format_brand_name, new Object[]{vehicleDetails.getVehicleInfo().getBrandName()}));
            this.btnVehicleDetails.setOnClickListener(new View.OnClickListener() {

                public final void onClick(View view) {
//                    VehicleDetailsActivity.this.lambda$handleResponse$6$VehicleDetailsActivity(vehicleDetails, view);
                }
            });
        }

        findViewById(R.id.ll_share).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                VehicleDetailsActivity.this.lambda$handleResponse$10$VehicleDetailsActivity(view);
            }
        });
        showOrHideElements(true, true, "");
    }
    public void lambda$handleResponse$10$VehicleDetailsActivity(View view) {
        shareToWhatsApp();
    }


    public void lambda$showRewardedVideo$12$VehicleDetailsActivity(String str) {
        if (str.equalsIgnoreCase("INSURANCE")) {
            updateInsuranceValue();
        } else {
            updateFinanceValue();
        }
    }

    /* access modifiers changed from: private */
    public void updateInsuranceValue() {
        this.insuranceCompanyValueHiddenContainer.setVisibility(8);
        this.txvInsuranceCompanyValue.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void updateFinanceValue() {
        this.financierValueHiddenContainer.setVisibility(8);
        this.txvFinancierValue.setVisibility(0);
    }

//    private void setupCoverUi(VehicleDetails vehicleDetails) {
//        ImageView imageView = (ImageView) findViewById(R.id.ivVehicleCoverImage);
//        if (vehicleDetails.getVehicleInfo() == null) {
//            imageView.setImageResource(vehicleDetails.identifyVehicleType());
//        } else {
//            Picasso.with(this).load(vehicleDetails.getVehicleInfo().getImageUrl()).placeholder(vehicleDetails.identifyVehicleType()).error(vehicleDetails.identifyVehicleType()).into(imageView);
//        }
//    }

    private void showOrHideElements(boolean z, boolean z2, String str) {
        int i = 8;
        this.errorContainer.setVisibility((!z || !z2) ? View.VISIBLE : View.GONE);
        LinearLayout linearLayout = this.contentLayout;
        if (z && z2) {
            i = 0;
        }
        linearLayout.setVisibility(i);

    }

    public void shareTo3rdPartyApps() {
        VehicleDetailsResponse vehicleDetailsResponse = this.response;
        if (vehicleDetailsResponse == null || vehicleDetailsResponse.getDetails() == null) {
            ToastHelper.showToast(this, getString(R.string.share_error), true);
            return;
        }
        VehicleDetails details = this.response.getDetails();
        String format = String.format(getString(R.string.share_vehicle_detail), new Object[]{details.getRegistrationNo(), details.getOwnerName(), details.getRegistrationAuthority(), details.getRegistrationDate(), details.getMakerModel(), details.getVehicleClass(), details.getFuelType(), details.getChassisNo(), details.getEngineNo()});
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", format);
        startActivity(Intent.createChooser(intent, "Share with"));
    }

    private void shareToWhatsApp() {
        VehicleDetailsResponse vehicleDetailsResponse = this.response;
        if (vehicleDetailsResponse == null || vehicleDetailsResponse.getDetails() == null) {
            ToastHelper.showToast(this, getString(R.string.share_error), true);
            return;
        }
        VehicleDetails details = this.response.getDetails();
        String format = String.format(getString(R.string.share_vehicle_detail), new Object[]{details.getRegistrationNo(), details.getOwnerName(), details.getRegistrationAuthority(), details.getRegistrationDate(), details.getMakerModel(), details.getVehicleClass(), details.getFuelType(), details.getChassisNo(), details.getEngineNo()});
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
//        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.TEXT", format);
        try {
            startActivity(intent);
        } catch (Exception e) {
//            FirebaseCrashlytics.getInstance().recordException(e);
            ToastHelper.showToast(this, "WhatsApp not installed on your device.", true);
        }
    }

    public void onBackPressed() {
        finish();
    }
}
