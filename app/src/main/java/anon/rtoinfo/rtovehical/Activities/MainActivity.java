package anon.rtoinfo.rtovehical.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import anon.rtoinfo.rtovehical.Process.GpsTracker;
import anon.rtoinfo.rtovehical.R;
import anon.rtoinfo.rtovehical.RTOExamDatabase.RTO_Variable;
import anon.rtoinfo.rtovehical.creation.MyCreationActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static ArrayList<String> dataModel;
    public static Boolean isexam = false;
    private List cityList = new ArrayList();
    String cityNameExtra;
    public String cityUrl;
    String cityUrlExtra;
    TextView diesel_price;
    LinearLayout Search_vehicle;
    ProgressBar diesel_progress;
    GpsTracker gpsTracker;
    LinearLayout near_car_showroom;
    LinearLayout near_petrol_pump;
    TextView petrol_price;
    ProgressBar petrol_progress;
    LinearLayout rto_info;
    LinearLayout rto_information;
    LinearLayout rto_symbols;
    LinearLayout rto_exam;
    LinearLayout rto_callan;
    ImageView text_change_city;
    TextView text_city;
    private List urlList = new ArrayList();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setSoftInputMode(3);
        getWindow().setSoftInputMode(48);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(getResources().getColor(R.color.white));
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());


        isexam = false;
        dataModel = new ArrayList<>();
        rto_info = (LinearLayout) findViewById(R.id.img_rtoinfo);
        near_petrol_pump = (LinearLayout) findViewById(R.id.img_near_petrolpump);
        near_car_showroom = (LinearLayout) findViewById(R.id.img_near_car_showroom);
        rto_callan = (LinearLayout) findViewById(R.id.rto_callan);
        text_city = (TextView) findViewById(R.id.text_city);
        diesel_price = (TextView) findViewById(R.id.diesel_price);
        petrol_price = (TextView) findViewById(R.id.petrol_price);
        Search_vehicle = (LinearLayout) findViewById(R.id.Search_vehicle);
        petrol_progress = (ProgressBar) findViewById(R.id.petrol_progress);
        diesel_progress = (ProgressBar) findViewById(R.id.diesel_progress);

        Search_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchVehicleActivity.class).putExtra("TYPE", "RC"));
            }
        });
        rto_callan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), challan_activity.class));
            }
        });
        try {
            if (Build.VERSION.SDK_INT < 23) {
                getLocation();
                jsonToArray();
                loadComponents();
            } else if (checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, 101);
            } else {
                getLocation();
                jsonToArray();
                loadComponents();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.rto_exam = (LinearLayout) findViewById(R.id.rto_exam);
        rto_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FuelActivity.class));
            }
        });
        this.rto_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), RTOinformationActivity.class));
            }
        });
        this.near_petrol_pump.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("geo:0,0?q=petrol-pump"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
        this.near_car_showroom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("geo:0,0?q=car-showroom"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
        this.text_change_city = (ImageView) findViewById(R.id.text_change_city);
        this.text_change_city.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CityListActivity.class));
            }
        });
        this.rto_symbols = (LinearLayout) findViewById(R.id.rto_symbols);
        this.rto_symbols.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RTOSymbolsActivity.class));
            }
        });
        this.rto_information = (LinearLayout) findViewById(R.id.rto_information);
        this.rto_information.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExamPreparationOptionsActivity.class));
            }
        });
        RTO_Variable.getInstance().nlist.clear();
        for (int i = 0; i < 208; i++) {
            RTO_Variable.getInstance().nlist.add(Integer.valueOf(i));
        }
    }

    public void loadComponents() {
        if (isNetworkAvailable()) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                this.cityNameExtra = extras.getString("city_name");
                this.cityUrlExtra = extras.getString("fuel_url");
                this.cityUrl = "http://www.mypetrolprice.com" + this.cityUrlExtra;
                new getpetrolpricetask().execute(this.cityUrl);
                if (!TextUtils.isEmpty(this.cityNameExtra)) {
                    this.text_city.setText(this.cityNameExtra);
                    return;
                }
                return;
            }
            for (int i = 0; i < this.cityList.size(); i++) {
                if (this.text_city.getText().toString().equals(this.cityList.get(i))) {
                    this.cityUrlExtra = this.urlList.get(i).toString();
                    this.cityUrl = "http://www.mypetrolprice.com" + this.cityUrlExtra;
                    if (!TextUtils.isEmpty(this.cityNameExtra)) {
                        this.text_city.setText(this.cityNameExtra);
                    }
                    new getpetrolpricetask().execute(this.cityUrl);
                }
            }
            return;
        }
        new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please turn on the internet otherwise you can't use this app").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.getItemId();
        return true;
    }

    private class getpetrolpricetask extends AsyncTask<String, Void, String[]> {
        public void onPreExecute() {
        }

        private getpetrolpricetask() {
        }

        public String[] doInBackground(String... strArr) {
            String[] strArr2 = {"N/A", "N/A"};
            String replaceAll = strArr[0].replaceAll("CURRENTFUEL", "Petrol");
            String replaceAll2 = strArr[0].replaceAll("CURRENTFUEL", "Diesel");
            try {
                strArr2[0] = "₹" + Jsoup.connect(replaceAll).get().select("#BC_lblCurrent").text().replaceAll("[^0-9.]", "");
                strArr2[1] = "₹" + Jsoup.connect(replaceAll2).get().select("#BC_lblCurrent").text().replaceAll("[^0-9.]", "");
            } catch (IOException e) {
                e.printStackTrace();
                strArr2[0] = "N/A";
                strArr2[1] = "N/A";
            }
            return strArr2;
        }

        @SuppressLint("WrongConstant")
        public void onPostExecute(String[] strArr) {
            petrol_price.setText(strArr[0]);
            diesel_price.setText(strArr[1]);
            if (petrol_price.getText().equals("N/A")) {
                new getpetrolpricetask().execute(cityUrl);
                petrol_price.setVisibility(8);
                diesel_price.setVisibility(8);
                diesel_progress.setVisibility(0);
                petrol_progress.setVisibility(0);
                return;
            }
            petrol_price.setVisibility(0);
            diesel_price.setVisibility(0);
            diesel_progress.setVisibility(8);
            petrol_progress.setVisibility(8);
            petrol_price.setText(strArr[0]);
            diesel_price.setText(strArr[1]);
        }
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void getLocation() throws IOException {
        this.gpsTracker = new GpsTracker(this);
        if (this.gpsTracker.canGetLocation()) {
            List<Address> fromLocation = new Geocoder(this, Locale.getDefault()).getFromLocation(this.gpsTracker.getLatitude(), this.gpsTracker.getLongitude(), 1);
            if (fromLocation.size() > 0) {
                this.text_city.setText(fromLocation.get(0).getLocality());
                return;
            }
            this.text_city.setText("Delhi");
            this.cityUrl = "http://www.mypetrolprice.com/2/CURRENTFUEL-price-in-Delhi";
            new getpetrolpricetask().execute(this.cityUrl);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS Issue..!!!");
        builder.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 90);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr != null && iArr.length > 0) {
            switch (i) {
                case 101:
                    if (iArr[0] == 0) {
                        try {
                            getLocation();
                            jsonToArray();
                            loadComponents();
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                    } else {
                        Toast.makeText(this, "Please allow permission", 0).show();
                        return;
                    }
                case 102:
                    if (iArr[1] == 0) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp1.jpg")));
                        startActivityForResult(intent, 10);
                        return;
                    }
                    Toast.makeText(this, "Please allow permission", 0).show();
                    return;
                case 104:
                    if (iArr[1] == 0) {
                        startActivity(new Intent(this, MyCreationActivity.class));
//                        if (Splash_Activity.adModel.getIsAdmobEnable() == 1 && isAdmobLoaded()) {
//                            showAdmobInterstitial();
//                        }
                        return;
                    } else {
                        Toast.makeText(this, "Please allow permission", 0).show();
                        return;
                    }
                default:
                    return;
            }
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 90) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    try {
                        getLocation();
                        jsonToArray();
                        loadComponents();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);
        }

    }

    public void jsonToArray() {
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
                    cityList.add(jSONObject.getString("city"));
                    urlList.add(jSONObject.getString("url"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}