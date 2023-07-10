package anon.rtoinfo.rtovehical.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements IRecyclerViewClickListener, IRecyclerViewLongClickListener {
    private List<SearchVehicleHistory> historyList;
    private RecentSearchHistoryAdapter adapter;
    ImageView iv_back;
    private String type;
    private boolean isHistoryItemClicked = false;
    TextView text_nofound;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        this.type = getIntent().getStringExtra("TYPE");
        this.historyList = new ArrayList();
        RecentSearchHistoryAdapter recentSearchHistoryAdapter = new RecentSearchHistoryAdapter(this, this.type, this, this);
        this.adapter = recentSearchHistoryAdapter;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchHistories);
        text_nofound = (TextView) findViewById(R.id.text_nofound);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.adapter);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        } else {
            showOrHideHistoryElements(false);
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
//                this.etFirst.setText(searchVehicleHistory.getRegistrationNo());
            }
        }
    }

    public void onItemLongClick(int i) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        TextView txt_cancel = (TextView) dialog.findViewById(R.id.txt_cancel);
        TextView txt_ok = (TextView) dialog.findViewById(R.id.txt_ok);
        txt_ok.setOnClickListener(new View.OnClickListener() {
            int position = i;

            @Override
            public void onClick(View v) {
                HistoryActivity.this.lambda$onItemLongClick$4$SearchVehicleActivity(position, dialog, i);
                dialog.dismiss();
            }
        });
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
                recyclerView.setVisibility(View.GONE);
                text_nofound.setVisibility(View.VISIBLE);
            }
            showOrHideHistoryElements(z);
        }
    }

    public void btnSearchVehicleDetailsClickListener(String str) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
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

    public void onResume() {
        super.onResume();
        if (!this.isHistoryItemClicked) {
            loadSearchHistories();
        }
    }

    public void showOrHideHistoryElements(boolean z) {
        if (z) {
            this.recyclerView.setVisibility(View.VISIBLE);
            text_nofound.setVisibility(View.GONE);
        } else {
            this.recyclerView.setVisibility(View.GONE);
            text_nofound.setVisibility(View.VISIBLE);
        }
    }
}
