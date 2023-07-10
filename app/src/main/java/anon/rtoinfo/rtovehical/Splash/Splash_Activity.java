package anon.rtoinfo.rtovehical.Splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import anon.rtoinfo.rtovehical.R;

public class Splash_Activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            public void run() {
                nextIntent();

            }
        }, 2000);
//        }
    }


    @Override
    public void onBackPressed() {

    }




    public void nextIntent() {
        Intent i = new Intent(Splash_Activity.this, Spalsh_StartActivity.class);
        i.putExtra("fromSplash", true);
        startActivity(i);
        finish();
    }

}