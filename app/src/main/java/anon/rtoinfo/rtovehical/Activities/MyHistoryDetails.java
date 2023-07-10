package anon.rtoinfo.rtovehical.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;

import anon.rtoinfo.rtovehical.R;

public class MyHistoryDetails extends AppCompatActivity {
    ImageView back_btn, iv_creationzoom;
    File path;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_historyzoom);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(getResources().getColor(R.color.white));

        FrameLayout adMobView = (FrameLayout) findViewById(R.id.adMobView);
//        showBanner(adMobView);

        this.iv_creationzoom = (ImageView) findViewById(R.id.iv_creationzoom);
        this.back_btn = (ImageView) findViewById(R.id.imageViewBack);
        this.back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        path = (File) getIntent().getExtras().get("path");
        Log.e("print", "onClickstr: " + path);

        Glide.with(this).load(this.path).into(this.iv_creationzoom);


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}