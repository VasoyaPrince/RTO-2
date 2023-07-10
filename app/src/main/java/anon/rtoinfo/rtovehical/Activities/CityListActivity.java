package anon.rtoinfo.rtovehical.Activities;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import anon.rtoinfo.rtovehical.Adapter.CityListAdapter;
import anon.rtoinfo.rtovehical.R;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import anon.rtoinfo.rtovehical.utils.Utils;

import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class CityListActivity extends AppCompatActivity {
    private ArrayList<String> cityList = new ArrayList<>();
    CityListAdapter citylistadapter;
    ImageView back_btn;
    ImageView serch;
    ImageView iv_clear;
    EditText search_city;
    private ArrayList<String> urlList = new ArrayList<>();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_citylist);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(getResources().getColor(R.color.white));

        FrameLayout adMobView = (FrameLayout) findViewById(R.id.adMobView);
//        showBanner(adMobView);

        jsonToArray();
        setupList();
        this.back_btn = (ImageView) findViewById(R.id.back_btn);
        this.serch = (ImageView) findViewById(R.id.serch);
        this.iv_clear = (ImageView) findViewById(R.id.iv_clear);
        this.back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.search_city = (EditText) findViewById(R.id.search_city);
        this.search_city.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (Utils.isNullOrEmpty(charSequence.toString()) || charSequence.toString().length() == 0) {
                    serch.setVisibility(View.VISIBLE);
                    iv_clear.setVisibility(View.GONE);
                    return;
                }
                serch.setVisibility(View.GONE);
                iv_clear.setVisibility(View.VISIBLE);
                citylistadapter.filter(charSequence.toString());
            }
        });
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_city.setText("");
            }
        });
    }

    private void setupList() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.city_list_recycler);
        VerticalRecyclerViewFastScroller verticalRecyclerViewFastScroller = (VerticalRecyclerViewFastScroller) findViewById(R.id.fast_scroller_city_list);
        recyclerView.setHasFixedSize(true);
        verticalRecyclerViewFastScroller.setRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(verticalRecyclerViewFastScroller.getOnScrollListener());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.citylistadapter = new CityListAdapter(this, this.cityList, this.urlList);
        recyclerView.setAdapter(this.citylistadapter);
    }

    private void jsonToArray() {
        try {
            InputStream open = getAssets().open("cities_data.json");
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            String str = null;
            if (Build.VERSION.SDK_INT >= 19) {
                str = new String(bArr, StandardCharsets.UTF_8);
            }
            try {
                JSONArray jSONArray = new JSONArray(str);
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    this.cityList.add(jSONObject.getString("city"));
                    this.urlList.add(jSONObject.getString("url"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}