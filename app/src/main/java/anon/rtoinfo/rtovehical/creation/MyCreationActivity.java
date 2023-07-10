package anon.rtoinfo.rtovehical.creation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import anon.rtoinfo.rtovehical.Activities.MyHistoryDetails;
import anon.rtoinfo.rtovehical.BuildConfig;
import anon.rtoinfo.rtovehical.R;

public class MyCreationActivity extends AppCompatActivity implements MycreationAdapter.OnrvgalleyItemClick {
    ImageView iv_back;
    RecyclerView rv_mycreation;
    public static ArrayList<String> IMAGEALLARY = new ArrayList<>();
    MycreationAdapter mycreationAdapter;
    private Uri urishare;
    private TextView txt_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_creation);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(getResources().getColor(R.color.white));

        FrameLayout adMobView = (FrameLayout) findViewById(R.id.adMobView);
//        showFBBanner(adMobView);
        FrameLayout adMobView1 = (FrameLayout) findViewById(R.id.adMobView1);
//        showBanner(adMobView1);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        IMAGEALLARY.clear();
        listAllImages(new File(pathtoSave() + "/"));

        rv_mycreation = (RecyclerView) findViewById(R.id.rv_mycreation);
        txt_nodata = (TextView) findViewById(R.id.txt_nodata);
        rv_mycreation.setHasFixedSize(true);

        if (IMAGEALLARY.size() > 0) {
            txt_nodata.setVisibility(View.GONE);
            rv_mycreation.setVisibility(View.VISIBLE);
            rv_mycreation.setLayoutManager(new GridLayoutManager(MyCreationActivity.this, 2));
            mycreationAdapter = new MycreationAdapter(MyCreationActivity.this, MyCreationActivity.this, IMAGEALLARY);
            rv_mycreation.setAdapter(mycreationAdapter);
        }
        txt_nodata.setVisibility(View.VISIBLE);
        rv_mycreation.setVisibility(View.GONE);
    }

    private String pathtoSave() {
        try {
            return Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name);
        } catch (Exception e) {
            return Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name);
        }
    }

    private void listAllImages(File filepath) {
        File[] files = filepath.listFiles();
        if (files != null) {
            for (int j = files.length - 1; j >= 0; j--) {
                String ss = files[j].toString();
                File check = new File(ss);
                Log.d("" + check.length(), "" + check.length());
                if (check.length() <= 1 << 10) {
                    Log.e("Invalid Image", "Delete Image");
                } else if (check.toString().contains(".jpg") || check.toString().contains(".png") || check.toString().contains(".jpeg")) {
                    IMAGEALLARY.add(ss);
                }
                System.out.println(ss);
            }
            Collections.sort(IMAGEALLARY);
            Collections.reverse(IMAGEALLARY);
            return;
        }
        System.out.println("Empty Folder");
    }

    @Override
    public void OnGalleyImageItemClick(int position) {
        File fromFile = new File(IMAGEALLARY.get(position));
        Intent intent = new Intent(MyCreationActivity.this, MyHistoryDetails.class);
        intent.putExtra("path", fromFile);
        Log.e("print", "onClick: " + fromFile);
        startActivity(intent);
    }

    @Override
    public void OnGalleyShareItemClick(int position) {
        Intent sharingIntent = new Intent("android.intent.action.SEND");
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + " Create By : " + "https://play.google.com/store/apps/details?id=" + getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            urishare = FileProvider.getUriForFile(MyCreationActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(IMAGEALLARY.get(position)));
        } else {
            urishare = Uri.fromFile(new File(IMAGEALLARY.get(position)));
        }

        sharingIntent.putExtra("android.intent.extra.STREAM", urishare);
        startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));
    }

    @Override
    public void OnGalleyDeleteItemClick(final int position) {
        new AlertDialog.Builder(this).setMessage("Do you want to delete this vehicle detail?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            /* class anon.rtoinfo.rtovehical.Activities.MyVehicleInfoActivity.Passport_Photo_Adpater.AnonymousClass2.AnonymousClass2 */

            public void onClick(DialogInterface dialogInterface, int i) {
                File fD = new File(IMAGEALLARY.get(position));
                if (fD.exists()) {
                    fD.delete();
                }
                IMAGEALLARY.remove(position);
                mycreationAdapter.notifyDataSetChanged();
                if (IMAGEALLARY.size() == 0) {
                    Toast.makeText(MyCreationActivity.this, "No Image Found..", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            /* class anon.rtoinfo.rtovehical.Activities.MyVehicleInfoActivity.Passport_Photo_Adpater.AnonymousClass2.AnonymousClass1 */

            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}