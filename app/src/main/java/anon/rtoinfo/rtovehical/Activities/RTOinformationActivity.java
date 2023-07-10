package anon.rtoinfo.rtovehical.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import anon.rtoinfo.rtovehical.Adapter.Searchadapter;
import anon.rtoinfo.rtovehical.Database.DBHelper;
import anon.rtoinfo.rtovehical.Database.DatabaseAccess;
import anon.rtoinfo.rtovehical.R;
import anon.rtoinfo.rtovehical.utils.Utils;

import java.util.ArrayList;

public class RTOinformationActivity extends AppCompatActivity {
    EditText inputSearch;
    ListView list_rto;
    DBHelper mydb;
    ImageView serch;
    ImageView iv_clear;
    Searchadapter searchadapter;
    private ImageView back_btn;

    private TextWatcher filterTextWatcher = new TextWatcher() {
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
            RTOinformationActivity.this.searchadapter.filter(charSequence.toString());
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_rtoinformation);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(getResources().getColor(R.color.white));
        this.serch = (ImageView) findViewById(R.id.serch);
        this.iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputSearch.setText("");
            }
        });
        FrameLayout adMobView = (FrameLayout) findViewById(R.id.adMobView);
//        showBanner(adMobView);

        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.list_rto = (ListView) findViewById(R.id.list_rto);
        this.inputSearch = (EditText) findViewById(R.id.inputSearch);
        DatabaseAccess instance = DatabaseAccess.getInstance(this);
        instance.open();
        ArrayList<String> quotes = instance.getQuotes();
        instance.close();
        this.mydb = new DBHelper(this);
        this.mydb.getAllCotacts();
        this.searchadapter = new Searchadapter(this, quotes);
        this.list_rto.setAdapter((ListAdapter) this.searchadapter);
        this.list_rto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Cursor data = RTOinformationActivity.this.mydb.getData(Searchadapter.language_filterlist.get(i));
                data.moveToFirst();
                String string = data.getString(2);
                if (!data.isClosed()) {
                    data.close();
                }
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("geo:0,0?q=" + string + ""));
                intent.setPackage("com.google.android.apps.maps");
                RTOinformationActivity.this.startActivity(intent);
            }
        });
        this.inputSearch.addTextChangedListener(this.filterTextWatcher);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}