package anon.rtoinfo.rtovehical.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import anon.rtoinfo.rtovehical.Adapter.AgeCalculation;
import anon.rtoinfo.rtovehical.Adapter.DataAdapter;
import anon.rtoinfo.rtovehical.R;

import java.io.File;
import java.io.FileOutputStream;

public class DetailActivity extends AppCompatActivity {
    DataAdapter dataAdapter;
    private File mGalleryFolder;
    Uri mImageUri;
    RecyclerView rcList;
    private int screen_height;
    private int screen_width;
    String send1;
    ImageView share_data, back_btn;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_detail);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(getResources().getColor(R.color.white));

        this.rcList = (RecyclerView) findViewById(R.id.rcList);
        int i = 1;
        this.rcList.setLayoutManager(new GridLayoutManager(this, 1));
        MainActivity.dataModel.remove("Road Tax Paid Upto:");
        MainActivity.dataModel.remove(", ");
        MainActivity.dataModel.remove(MainActivity.dataModel.size() - 1);
        this.mGalleryFolder = createFolders();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screen_height = displayMetrics.heightPixels;
        this.screen_width = displayMetrics.widthPixels;
        String[] split = MainActivity.dataModel.get(5).split("-");
        if (!split[1].equals("Jan")) {
            if (split[1].equals("Feb")) {
                i = 2;
            } else if (split[1].equals("Mar")) {
                i = 3;
            } else if (split[1].equals("Apr")) {
                i = 4;
            } else if (split[1].equals("May")) {
                i = 5;
            } else if (split[1].equals("Jun")) {
                i = 6;
            } else if (split[1].equals("Jul")) {
                i = 7;
            } else if (split[1].equals("Aug")) {
                i = 8;
            } else if (split[1].equals("Sep")) {
                i = 9;
            } else if (split[1].equals("Oct")) {
                i = 10;
            } else if (split[1].equals("Nov")) {
                i = 11;
            } else if (split[1].equals("Dec")) {
                i = 12;
            } else {
                i = 1;
            }
        }
        AgeCalculation ageCalculation = new AgeCalculation();
        ageCalculation.getCurrentDate();
        ageCalculation.setDateOfBirth(Integer.parseInt(split[2]), i, Integer.parseInt(split[0]));
        MainActivity.dataModel.add("Vehicle Age :");
        MainActivity.dataModel.add(ageCalculation.calcualteYear() + " Year " + ageCalculation.calcualteMonth() + " Month " + ageCalculation.calcualteDay() + " Day");
        this.dataAdapter = new DataAdapter(this, MainActivity.dataModel);
        this.rcList.setAdapter(this.dataAdapter);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                DetailActivity.this.saveImage();
            }
        }, 1000);
        this.back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.share_data = (ImageView) findViewById(R.id.share_data);
        this.share_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", DataAdapter.finaldata.get(3) + " Vehicle Information");
                intent.putExtra("android.intent.extra.STREAM", DetailActivity.this.mImageUri);
                intent.setType("image/*");
                DetailActivity.this.startActivity(intent);
            }
        });
    }

    private File createFolders() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory == null) {
            return Environment.getExternalStorageDirectory();
        }
        File file = new File(externalStorageDirectory, getResources().getString(R.string.app_name));
        return (file.exists() || file.mkdirs()) ? file : Environment.getExternalStorageDirectory();
    }

    public boolean saveImage() {
        File file;
        File file2 = this.mGalleryFolder;
        if (file2 == null || !file2.exists()) {
            file = null;
        } else {
            file = new File(this.mGalleryFolder, "Vehicleinformation_" + DataAdapter.finaldata.get(3) + "_" + System.currentTimeMillis() + ".jpg");
        }
        try {
            Bitmap recyclerViewScreenshot = getRecyclerViewScreenshot(this.rcList);
            this.mImageUri = Uri.parse("file://" + file.getPath());
            this.send1 = file.getPath();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            recyclerViewScreenshot.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, new String[]{"image/jpeg"}, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressLint("WrongConstant")
    public Bitmap getRecyclerViewScreenshot(RecyclerView recyclerView) {
        Bitmap bitmap;
        int itemCount = this.dataAdapter.getItemCount();
        DataAdapter.MyViewHolder1 myViewHolder1 = (DataAdapter.MyViewHolder1) this.dataAdapter.createViewHolder(recyclerView, 0);
        this.dataAdapter.onBindViewHolder(myViewHolder1, 0);
        myViewHolder1.child_linear.measure(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
        myViewHolder1.child_linear.layout(0, 0, myViewHolder1.child_linear.getMeasuredWidth(), myViewHolder1.child_linear.getMeasuredHeight());
        if (this.screen_width > 720) {
            bitmap = Bitmap.createBitmap(1080, (myViewHolder1.child_linear.getMeasuredHeight() * itemCount) - 350, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(720, (myViewHolder1.child_linear.getMeasuredHeight() * itemCount) - 250, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(-1);
        Paint paint = new Paint();
        int i = 0;
        for (int i2 = 0; i2 < itemCount; i2++) {
            this.dataAdapter.onBindViewHolder(myViewHolder1, i2);
            myViewHolder1.txtLabel.setDrawingCacheEnabled(true);
            myViewHolder1.txtLabel.buildDrawingCache();
            myViewHolder1.txtLabel.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            myViewHolder1.txtLabel.layout(0, 0, myViewHolder1.txtLabel.getMeasuredWidth() * 3, myViewHolder1.txtLabel.getMeasuredHeight() * 2);
            myViewHolder1.txtLabel.buildDrawingCache(true);
            canvas.drawBitmap(pad(myViewHolder1.txtLabel.getDrawingCache(), 30, 0), 0.0f, (float) i, paint);
            i += myViewHolder1.txtLabel.getMeasuredHeight();
            myViewHolder1.txtLabel.setDrawingCacheEnabled(false);
            myViewHolder1.txtLabel.destroyDrawingCache();
        }
        return bitmap;
    }

    public Bitmap pad(Bitmap bitmap, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth() + i, bitmap.getHeight() + i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawARGB(255, 255, 255, 255);
        canvas.drawBitmap(bitmap, (float) i, (float) i2, (Paint) null);
        return createBitmap;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}