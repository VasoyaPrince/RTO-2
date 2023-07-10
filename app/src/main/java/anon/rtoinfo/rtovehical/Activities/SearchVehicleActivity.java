package anon.rtoinfo.rtovehical.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.PointerIconCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import anon.rtoinfo.rtovehical.Adapter.IRecyclerViewClickListener;
import anon.rtoinfo.rtovehical.Adapter.IRecyclerViewLongClickListener;
import anon.rtoinfo.rtovehical.Adapter.RecentSearchHistoryAdapter;
import anon.rtoinfo.rtovehical.Database.SearchVehicleHistoryTableAdapter;
import anon.rtoinfo.rtovehical.Database.VehicleDetailsTableAdapter;
import anon.rtoinfo.rtovehical.R;


import anon.rtoinfo.rtovehical.datamodels.SearchVehicleHistory;
import anon.rtoinfo.rtovehical.helpers.GlobalTracker;
import anon.rtoinfo.rtovehical.helpers.ToastHelper;
import anon.rtoinfo.rtovehical.utils.Utils;
import anon.rtoinfo.rtovehical.widget.BaseBottomSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class SearchVehicleActivity extends AppCompatActivity implements IRecyclerViewClickListener, IRecyclerViewLongClickListener {
    private static final int SPEECH_RECOGNITION_CODE = 1001;
    private RecentSearchHistoryAdapter adapter;
    private TextView btnSearchDetails;
    private CardView cvRecentSearches;
    private EditText etFirst;
    private List<SearchVehicleHistory> historyList;
    public ImageView imageClear;
    public ImageView imageVoice;
    public ImageView history;
    public ImageView iv_back;
    private boolean isHistoryItemClicked = false;
    private String type;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search_vehicle);
        this.type = getIntent().getStringExtra("TYPE");
        String str = this.type;

        EditText editText = (EditText) findViewById(R.id.first_part);
        this.etFirst = editText;
        editText.setOnKeyListener(new View.OnKeyListener() {
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return SearchVehicleActivity.this.lambda$onCreate$0$SearchVehicleActivity(view, i, keyEvent);
            }
        });
        applyEditTextFilters();
        this.imageClear = (ImageView) findViewById(R.id.iv_clear);
        this.imageVoice = (ImageView) findViewById(R.id.iv_voice);
        this.history = (ImageView) findViewById(R.id.history);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.btnSearchDetails = (TextView) findViewById(R.id.btnSearchDetails);
        this.cvRecentSearches = (CardView) findViewById(R.id.cvRecentSearches);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchHistories);
        this.historyList = new ArrayList();
        RecentSearchHistoryAdapter recentSearchHistoryAdapter = new RecentSearchHistoryAdapter(this, this.type, this, this);
        this.adapter = recentSearchHistoryAdapter;
        recentSearchHistoryAdapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.adapter);
        recyclerView.setNestedScrollingEnabled(false);
        this.etFirst.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (Utils.isNullOrEmpty(charSequence.toString()) || charSequence.toString().length() == 0) {
                    SearchVehicleActivity.this.imageClear.setVisibility(8);
                    return;
                }
                SearchVehicleActivity.this.imageClear.setVisibility(0);
            }
        });
        this.imageClear.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                SearchVehicleActivity.this.lambda$onCreate$1$SearchVehicleActivity(view);
            }
        });
        this.imageVoice.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                SearchVehicleActivity.this.lambda$onCreate$2$SearchVehicleActivity(view);
            }
        });
        this.btnSearchDetails.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                SearchVehicleActivity.this.lambda$onCreate$3$SearchVehicleActivity(view);
            }
        });
        this.history.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class).putExtra("TYPE", type));
            }
        });
        this.iv_back.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public boolean lambda$onCreate$0$SearchVehicleActivity(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) {
            return false;
        }
        if (i != 66) {
            if (i == 67 && this.etFirst.getText().length() == 0) {
                this.etFirst.requestFocus();
            }
            return false;
        } else if (isValidRegistrationNo()) {
            ToastHelper.showToast(this, "Please enter vehicle registration number", false);
            return true;
        } else {
            btnSearchVehicleDetailsClickListener(this.etFirst.getText().toString());
            return true;
        }
    }

    public void lambda$onCreate$1$SearchVehicleActivity(View view) {
        this.etFirst.setText("");
    }

    public void lambda$onCreate$2$SearchVehicleActivity(View view) {
        checkPermission();
    }

    public void lambda$onCreate$3$SearchVehicleActivity(View view) {
        if (isValidRegistrationNo()) {
            ToastHelper.showToast(this, "Please enter vehicle registration number", false);
        } else {
            btnSearchVehicleDetailsClickListener(this.etFirst.getText().toString());
        }
    }

    private void applyEditTextFilters() {
        this.etFirst.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(11)});
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") == 0) {
            listen();
        } else if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.RECORD_AUDIO"}, PointerIconCompat.TYPE_HAND);
        } else {
            listen();
        }
    }

    private void listen() {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("calling_package", getPackageName());
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.PROMPT", getString(R.string.placeholder_speech_prompt_add_vehicle_no));
        intent.putExtra("android.speech.extra.LANGUAGE", Locale.getDefault());
        try {
            startActivityForResult(intent, 1001);
        } catch (Exception e) {
            ToastHelper.showToast(this, getString(R.string.txt_device_no_speech_recognition), true);
        }
        this.etFirst.setText("");
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 1002) {
            return;
        }
        if (iArr.length <= 0 || iArr[0] != 0) {
            Bundle bundle = new Bundle();
            bundle.putString("TITLE", "Permission Denied");
            bundle.putString("MESSAGE", "Kindly allow audio permission from app settings to enable voice input feature.");
            BaseBottomSheet baseBottomSheet = new BaseBottomSheet();
            baseBottomSheet.setArguments(bundle);
            baseBottomSheet.show(getSupportFragmentManager(), "acknowledgement_bottom_sheet");
            return;
        }
        checkPermission();
    }

    private boolean isValidRegistrationNo() {
        return Utils.isNullOrEmpty(this.etFirst.getText().toString());
    }

    public void btnSearchVehicleDetailsClickListener(String str) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(this.btnSearchDetails.getWindowToken(), 0);
        }
        if (!Utils.isNetworkConnected(this)) {
            ToastHelper.showToast(this, getString(R.string.app_internet_msg), false);
            return;
        }
        String formatString = Utils.formatString(str);
        if (Utils.isNullOrEmpty(formatString) || formatString.length() < 5) {
            ToastHelper.showToast(this, "Please enter the correct vehicle no!", true);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(GlobalTracker.EVENT_VEHICLE_NO, formatString);
        bundle.putString("content_type", GlobalTracker.BUTTON);
        GlobalTracker.from((Context) this).sendSelectContentEvent(bundle);
        Intent intent = new Intent(this, SearchVehicleDetailsLoaderActivity.class);
        intent.putExtra("REGISTRATION_NO", formatString);
        intent.putExtra("ACTION", "SAVE");
        intent.putExtra("TYPE", this.type);
        startActivity(intent);
    }

    public void showOrHideHistoryElements(boolean z) {
        this.cvRecentSearches.setVisibility(z ? 0 : 8);
    }

    private void loadSearchHistories() {
        this.historyList = new SearchVehicleHistoryTableAdapter(this).getSearchVehicleHistoryList(true, 20);
        Bundle bundle = new Bundle();
        bundle.putString("item_name", "Vehicle search histories loaded");
        List<SearchVehicleHistory> list = this.historyList;
        if (list == null) {
            bundle.putString("item_list", "0");
        } else {
            bundle.putString("item_list", String.valueOf(list.size()));
        }
        bundle.putString("content_type", GlobalTracker.BUTTON);
        GlobalTracker.from((Context) this).sendViewItemListEvent(bundle);
        List<SearchVehicleHistory> list2 = this.historyList;
        if (list2 != null && list2.size() > 0) {
            showOrHideHistoryElements(true);
            this.adapter.updateListData(this.historyList);
        }
    }

    public void onItemSelected(int i) {
        SearchVehicleHistory searchVehicleHistory;
        List<SearchVehicleHistory> list = this.historyList;
        if (list != null && list.size() > 0 && i >= 0 && i < this.historyList.size() && (searchVehicleHistory = this.historyList.get(i)) != null) {
            this.isHistoryItemClicked = true;
            String str = this.type;
            if (str == null || (!str.equalsIgnoreCase("INSURANCE") && !this.type.equalsIgnoreCase("FINANCE"))) {
                btnSearchVehicleDetailsClickListener(searchVehicleHistory.getRegistrationNo());
            } else {
                this.etFirst.setText(searchVehicleHistory.getRegistrationNo());
            }
        }
    }

    public void onItemLongClick(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.txt_confirm_delete_search_history);
        builder.setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {

            int position = i;

            public final void onClick(DialogInterface dialogInterface, int i) {
                SearchVehicleActivity.this.lambda$onItemLongClick$4$SearchVehicleActivity(position, dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.txt_no, (DialogInterface.OnClickListener) null).show();
    }

    public void lambda$onItemLongClick$4$SearchVehicleActivity(int i, DialogInterface dialogInterface, int i2) {
        SearchVehicleHistory searchVehicleHistory;
        List<SearchVehicleHistory> list = this.historyList;
        if (list != null && list.size() > 0 && i < this.historyList.size() && (searchVehicleHistory = this.historyList.get(i)) != null) {
            boolean z = true;
            try {
                new SearchVehicleHistoryTableAdapter(this).deleteHistoryById(String.valueOf(searchVehicleHistory.getId()), true);
                new VehicleDetailsTableAdapter(this).deleteHistoryByArgs(searchVehicleHistory.getRegistrationNo());
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.historyList.remove(i);
            this.adapter.notifyDataSetChanged();
            if (this.historyList.size() <= 0) {
                z = false;
            }
            showOrHideHistoryElements(z);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        ArrayList<String> stringArrayListExtra;
        String str;
        if (i == 1001 && i2 == -1 && intent != null && (stringArrayListExtra = intent.getStringArrayListExtra("android.speech.extra.RESULTS")) != null && !stringArrayListExtra.isEmpty()) {
            Iterator<String> it = stringArrayListExtra.iterator();
            while (true) {
                if (!it.hasNext()) {
                    str = "";
                    break;
                }
                str = Utils.formatString(it.next());
                if (!Utils.isNullOrEmpty(str)) {
                    break;
                }
            }
            if (Utils.isNullOrEmpty(str)) {
                ToastHelper.showToast(this, "Invalid registration no.", true);
            } else {
                this.etFirst.setText(str);
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        return;
    }

    public void onResume() {
        super.onResume();
        if (!this.isHistoryItemClicked) {
            loadSearchHistories();
        }
    }
}
