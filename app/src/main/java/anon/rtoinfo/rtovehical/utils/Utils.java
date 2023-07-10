package anon.rtoinfo.rtovehical.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import anon.rtoinfo.rtovehical.R;
import anon.rtoinfo.rtovehical.helpers.ToastHelper;

import java.io.File;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import saschpe.android.customtabs.CustomTabsHelper;

public class Utils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNullOrEmptyOrNA(String str) {
        return str == null || str.isEmpty() || str.toLowerCase().equalsIgnoreCase("na") || str.toLowerCase().equalsIgnoreCase("n/a") || str.toLowerCase().equalsIgnoreCase("not available") || str.toLowerCase().equalsIgnoreCase("null") || str.toLowerCase().equalsIgnoreCase("0");
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        try {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String formatString(String str) {
        if (isNullOrEmpty(str)) {
            return "";
        }
        return str.replaceAll("[^A-Za-z0-9]", "");
    }

    private static File getFile(File file, String str) {
        File file2 = new File(file, "images");
        if (!file2.exists()) {
            boolean mkdirs = file2.mkdirs();
            String simpleName = Utils.class.getSimpleName();
            Log.i(simpleName, "Folder created: " + mkdirs);
        }
        return new File(file2, str);
    }


    public static Date formatDateByPattern(String str, String str2) {
        if (str2 == null || str2.contentEquals("")) {
            return new Date();
        }
        try {
            return new SimpleDateFormat(str, Locale.US).parse(str2);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getVehicleAge(String str) {
        Date formatDateByPattern;
        String str2;
        String str3;
        if (isNullOrEmpty(str) || (formatDateByPattern = formatDateByPattern("dd-MMM-yyyy", str)) == null) {
            return "";
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(formatDateByPattern);
        Calendar instance2 = Calendar.getInstance();
        int i = instance2.get(1) - instance.get(1);
        int i2 = instance2.get(2) + 1;
        int i3 = instance.get(2) + 1;
        int i4 = i2 - i3;
        if (i4 < 0) {
            i--;
            i4 = (12 - i3) + i2;
            if (instance2.get(5) < instance.get(5)) {
                i4--;
            }
        } else if (i4 == 0 && instance2.get(5) < instance.get(5)) {
            i--;
            i4 = 11;
        }
        int i5 = 0;
        if (instance2.get(5) > instance.get(5)) {
            i5 = instance2.get(5) - instance.get(5);
        } else if (instance2.get(5) < instance.get(5)) {
            int i6 = instance2.get(5);
            instance2.add(2, -1);
            i5 = (instance2.getActualMaximum(5) - instance.get(5)) + i6;
        } else if (i4 == 12) {
            i++;
            i4 = 0;
        }
        if (i <= 1) {
            str2 = "".concat(String.valueOf(i)).concat(" Year ");
        } else {
            str2 = "".concat(String.valueOf(i)).concat(" Years ");
        }
        if (i4 <= 1) {
            str3 = str2.concat(String.valueOf(i4)).concat(" Month ");
        } else {
            str3 = str2.concat(String.valueOf(i4)).concat(" Months ");
        }
        if (i5 <= 1) {
            return str3.concat(String.valueOf(i5)).concat(" Day");
        }
        return str3.concat(String.valueOf(i5)).concat(" Days");
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public static String getPackageName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public static String getAndroidId(Context context) {
        String keyDeviceId = PreferencesHelper.getKeyDeviceId();
        if (!isNullOrEmpty(keyDeviceId)) {
            return keyDeviceId;
        }
        try {
            keyDeviceId = Settings.Secure.getString(context.getContentResolver(), "android_id");
            PreferencesHelper.setKeyDeviceId(keyDeviceId);
            return keyDeviceId;
        } catch (Exception e) {
            e.printStackTrace();
            return keyDeviceId;
        }
    }

    public static String getTimeInMilli() {
        return String.valueOf(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime().getTime());
    }

    public static String getRandomNumber() {
        return String.valueOf(new Random().nextInt(1000));
    }

    public static int getDeviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDeviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String encryptStr(Context context, String str, String str2) {
        byte[] bytes = ("android_vehicle-details|encTradetu-" + str + "|" + getAppVersionCode(context) + "|" + str2).getBytes();
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-512");
            instance.reset();
            instance.update(bytes);
            for (byte b : instance.digest()) {
                String hexString = Integer.toHexString(b & -1);
                if (hexString.length() == 1) {
                    sb.append("0");
                }
                sb.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static boolean isActivityFinished(Context context) {
        if (!(context instanceof Activity)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 17) {
            return !((Activity) context).isFinishing();
        }
        Activity activity = (Activity) context;
        if (activity.isFinishing() || activity.isDestroyed()) {
            return false;
        }
        return true;
    }

    public static String[] splitRegistrationNo(String str) {
        String[] strArr = new String[2];
        try {
            strArr[0] = str.split("\\d*$")[0];
            strArr[1] = str.replace(strArr[0], "");
            if (isNullOrEmpty(strArr[1])) {
                strArr[1] = "0";
            }
        } catch (Exception unused) {
            strArr[0] = str;
            strArr[1] = "0";
        }
        return strArr;
    }

    public static void openWebPage(Context context, String str) {
        Uri parse = Uri.parse(str);
        if (!str.startsWith("http://") && !str.startsWith("https://") && !str.startsWith("market://")) {
            parse = Uri.parse("http://" + str);
        }
        try {
            Intent intent = new Intent("android.intent.action.VIEW", parse);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        } catch (Exception unused) {
            ToastHelper.showToast(context, context.getString(R.string.no_browser_error), false);
        }
    }

    public static void openBrowserTabWithCustomFallback(Activity activity, String str) {
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
            builder.setSecondaryToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
            builder.setShowTitle(true);
            builder.enableUrlBarHiding();
            CustomTabsHelper.openCustomTab(activity, builder.build(), Uri.parse(str), new CustomTabsHelper.CustomTabFallback() {
                public final void openUri(Context activity, Uri str) {
                    Utils.openWebPage(activity, String.valueOf(str));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String extractWarningMessage(String str, String str2) {
        if (isNullOrEmpty(str)) {
            return "";
        }
        try {
            String substring = str.substring(str.indexOf("showMessageInDialog"));
            if (!isNullOrEmpty(substring)) {
                if (substring.contains(",")) {
                    String[] split = substring.split("\",");
                    if (split.length < 3) {
                        return "";
                    }
                    String[] split2 = split[2].split("\"");
                    if (split2.length < 2) {
                        return "";
                    }
                    return split2[1].trim();
                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while fetching " + str2 + " details, please try again!";
        }
    }

    public static String formatUrl(String str) {
        if (str.contains("flipkart")) {
            if (!str.contains("?")) {
                return str.concat("?affid=").concat(GlobalReferenceEngine.flipkartAffiliateId).concat("&affExtParam1=rto_externallink");
            }
            if (!str.contains("affid")) {
                return str.concat("&affid=").concat(GlobalReferenceEngine.flipkartAffiliateId).concat("&affExtParam1=rto_externallink");
            }
            try {
                return GlobalReferenceEngine.cueLinksBaseUrl.concat(URLEncoder.encode(str, "UTF-8").replace("%2F", "/")).concat("&subid=rto_externallink");
            } catch (Exception e) {
                e.printStackTrace();
                return str;
            }
        } else if (str.contains("2gud")) {
            if (!str.contains("?")) {
                return str.concat("?affid=").concat(GlobalReferenceEngine.twoGudAffiliateId).concat("&affExtParam1=rto_externallink");
            }
            if (!str.contains("affid")) {
                return str.concat("&affid=").concat(GlobalReferenceEngine.twoGudAffiliateId).concat("&affExtParam1=rto_externallink");
            }
            try {
                return GlobalReferenceEngine.cueLinksBaseUrl.concat(URLEncoder.encode(str, "UTF-8").replace("%2F", "/")).concat("&subid=rto_externallink");
            } catch (Exception e2) {
                e2.printStackTrace();
                return str;
            }
        } else if (!str.contains("amazon.in")) {
            try {
                return GlobalReferenceEngine.cueLinksBaseUrl.concat(URLEncoder.encode(str, "UTF-8").replace("%2F", "/")).concat("&subid=rto_externallink");
            } catch (Exception e3) {
                e3.printStackTrace();
                return str;
            }
        } else if (!str.contains("?")) {
            return str.concat("?tag=").concat(GlobalReferenceEngine.amazonAffiliateId).concat("&ascsubtag=rto_externallink");
        } else {
            if (!str.contains("tag")) {
                return str.concat("&tag=").concat(GlobalReferenceEngine.amazonAffiliateId).concat("&ascsubtag=rto_externallink");
            }
            try {
                return GlobalReferenceEngine.cueLinksBaseUrl.concat(URLEncoder.encode(str, "UTF-8").replace("%2F", "/")).concat("&subid=rto_externallink");
            } catch (Exception e4) {
                e4.printStackTrace();
                return str;
            }
        }
    }

    public static String getOwnershipString(String str) {
        if (isNullOrEmpty(str) || str.equalsIgnoreCase("1")) {
            return "FIRST OWNER";
        }
        if (str.equalsIgnoreCase("2")) {
            return "SECOND OWNER";
        }
        if (str.equalsIgnoreCase("3")) {
            return "THIRD OWNER";
        }
        if (str.equalsIgnoreCase("4")) {
            return "FOURTH OWNER";
        }
        if (str.equalsIgnoreCase("5")) {
            return "FIFTH OWNER";
        }
        if (str.equalsIgnoreCase("6")) {
            return "SIXTH OWNER";
        }
        if (str.equalsIgnoreCase("7")) {
            return "SEVENTH OWNER";
        }
        if (str.equalsIgnoreCase("8")) {
            return "EIGHTH OWNER";
        }
        if (str.equalsIgnoreCase("9")) {
            return "NINTH OWNER";
        }
        if (str.equalsIgnoreCase("10")) {
            return "TENTH OWNER";
        }
        if (str.equalsIgnoreCase("11")) {
            return "ELEVENTH OWNER";
        }
        if (str.equalsIgnoreCase("12")) {
            return "TWELFTH OWNER";
        }
        if (str.equalsIgnoreCase("13")) {
            return "THIRTEENTH OWNER";
        }
        if (str.equalsIgnoreCase("14")) {
            return "FOUTEENTH OWNER";
        }
        if (str.equalsIgnoreCase("15")) {
            return "FIFTEENTH OWNER";
        }
        return "FIRST OWNER";
    }

    public static String hideString(String str) {
        if (isNullOrEmpty(str)) {
            return "";
        }
        return str.replaceAll("\\w", "X");
    }
}
