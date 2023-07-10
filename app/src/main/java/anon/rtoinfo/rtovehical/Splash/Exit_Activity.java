package anon.rtoinfo.rtovehical.Splash;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import anon.rtoinfo.rtovehical.R;

public class Exit_Activity extends AppCompatActivity {

    private ImageView btn_yes, btn_no,iv_rate;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_exit);

//        FrameLayout native_ad = (FrameLayout) findViewById(R.id.native_ad);
//        showGOOGLEAdvance1(native_ad);
//
//        LinearLayout ll_qlbanner = (LinearLayout) findViewById(R.id.ll_qlbanner);
//        ll_qlbanner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openChromeCustomTabUrl();
//            }
//        });

        btn_yes = (ImageView) findViewById(R.id.btn_yes);
        btn_no = (ImageView) findViewById(R.id.btn_no);
        iv_rate = (ImageView) findViewById(R.id.iv_rate);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
        iv_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri1 = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri1);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(Exit_Activity.this, "You don't have Google Play installed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}