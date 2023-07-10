package anon.rtoinfo.rtovehical.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import anon.rtoinfo.rtovehical.R;

public class RTOSymbolsActivity extends AppCompatActivity {
    ImageView back_btn;
    LinearLayout cautionary_option;
    LinearLayout drivingrules_option;
    LinearLayout informatory_option;
    LinearLayout mandatory_option;
    LinearLayout roadsingnals_option;
    LinearLayout trafficpolice_option;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_rtosymbols);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(getResources().getColor(R.color.white));

        this.back_btn = (ImageView) findViewById(R.id.back_btn);
        this.back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTOSymbolsActivity.this.onBackPressed();
            }
        });
        this.cautionary_option = (LinearLayout) findViewById(R.id.cautionary_option);
        this.cautionary_option.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTOSymbolsActivity.this.openinfoactivity("Cautionary" , R.drawable.ic_symbolcau);
            }
        });
        this.informatory_option = (LinearLayout) findViewById(R.id.informatory_option);
        this.informatory_option.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTOSymbolsActivity.this.openinfoactivity("Informatory", R.drawable.ic_parkinginfo );
            }
        });
        this.mandatory_option = (LinearLayout) findViewById(R.id.mandatory_option);
        this.mandatory_option.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTOSymbolsActivity.this.openinfoactivity("Mandatory" , R.drawable.ic_signman);
            }
        });
        this.roadsingnals_option = (LinearLayout) findViewById(R.id.roadsingnals_option);
        this.roadsingnals_option.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTOSymbolsActivity.this.openinfoactivity("Road & Signals" ,R.drawable.ic_trafficroadsign);
            }
        });
        this.drivingrules_option = (LinearLayout) findViewById(R.id.drivingrules_option);
        this.drivingrules_option.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTOSymbolsActivity.this.openinfoactivity("Driving Rules", R.drawable.ic_drivingrules);
            }
        });
        this.trafficpolice_option = (LinearLayout) findViewById(R.id.trafficpolice_option);
        this.trafficpolice_option.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTOSymbolsActivity.this.openinfoactivity("Traffic Police Signals", R.drawable.ic_trafficc);
            }
        });
    }

    public void openinfoactivity(String str, int logo) {
        Intent intent = new Intent(this, RTOSymbolsDetailActivity.class);
        intent.putExtra("passvalue", str);
        intent.putExtra("passlogo", logo);
        startActivity(intent);
//        showadd();
    }
}