package com.googol.imagecompress.utills;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;
import com.googol.imagecompress.R;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class AppConstant {
    public static boolean checkVersion() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public static int[] getOptionArray(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    public static String getDisplaySize(long j) {
        if (j <= 0) {
            return "0";
        }
        String[] strArr = {"B", "kB", "MB", "GB", "TB"};
        double d = (double) j;
        int log10 = (int) (Math.log10(d) / Math.log10(1024.0d));
        String str = (log10 == 0 || log10 == 1) ? "###0" : "###0.#";
        StringBuilder sb = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat(str);
        double pow = Math.pow(1024.0d, (double) log10);
        Double.isNaN(d);
        Double.isNaN(d);
        sb.append(decimalFormat.format(d / pow));
        sb.append(" ");
        sb.append(strArr[log10]);
        return sb.toString();
    }

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, 0).show();
    }

    public static Bitmap bitmapHelper(String str, BitmapFactory.Options options) {
        Bitmap bitmap;
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(str);
            bitmap = BitmapFactory.decodeStream(fileInputStream2, null, options);
            fileInputStream = fileInputStream2;
        } catch (Exception unused) {
            bitmap = null;
        }
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getVersion(Context context) {
        String str;
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            str = "";
        }
        return String.valueOf("Version " + str);
    }

    public static void shareApp(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", context.getString(R.string.app_name) + "\n\n https://play.google.com/store/apps/details?id=" + context.getPackageName());
            context.startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toastShort(Context context, String str) {
        Toast.makeText(context, str, 0).show();
    }

    public static void emailUsFeedback(Context context, String str) {
        try {
            String str2 = Build.MODEL;
            String str3 = Build.MANUFACTURER;
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("mailto:googolenterprise11@gmail.com"));
            intent.putExtra("android.intent.extra.SUBJECT", Constants.APP_EMAIL_SUBJECT);
            intent.putExtra("android.intent.extra.TEXT", "Kindly give us your precious feedback. \n If you have any query then let us know.\nTo : " + context.getString(R.string.app_name) + " \n\nDevice Manufacturer : " + str3 + "\nDevice Model : " + str2 + "\nAndroid Version : " + Build.VERSION.RELEASE);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openUrl(Context context, String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.addFlags(1208483840);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Constants.PRIVACY_POLICY_URL)));
        }
    }

    public static void uriparse(Context context, String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.addFlags(1208483840);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
