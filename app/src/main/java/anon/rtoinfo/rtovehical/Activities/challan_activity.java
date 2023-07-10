package anon.rtoinfo.rtovehical.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import anon.rtoinfo.rtovehical.R;

public class challan_activity extends AppCompatActivity {

    public WebView webPrivacyPolicy;
    public ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_challan);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(challan_activity.this, R.color.white));

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        webPrivacyPolicy = (WebView) findViewById(R.id.wvPrivacyPolicy);
        WebSettings webSettings = webPrivacyPolicy.getSettings();
        webSettings.setJavaScriptEnabled(true);// enable javascript
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webPrivacyPolicy.setInitialScale(1);
        webPrivacyPolicy.getSettings().setLoadWithOverviewMode(true);
        webPrivacyPolicy.getSettings().setUseWideViewPort(true);
        webPrivacyPolicy.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webPrivacyPolicy.setScrollbarFadingEnabled(true);
        webPrivacyPolicy.getSettings().setBuiltInZoomControls(true);
        webPrivacyPolicy.getSettings().setDisplayZoomControls(false);

        webPrivacyPolicy.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(challan_activity.this, description, Toast.LENGTH_SHORT).show();
            }
        });

        webPrivacyPolicy.loadUrl("https://echallan.parivahan.gov.in/index/accused-challan");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
