package anon.rtoinfo.rtovehical.utils;

import android.content.Context;


import anon.rtoinfo.rtovehical.helpers.AppMigrateNotifier;

import org.json.JSONObject;

//import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class GlobalReferenceEngine {
    private static String API_BASE_URL = "https://www.tradetu.com/bus_api/public/api/v1/vaahan/";
    public static String INIT_DATA_NODE = "init_data";
    public static String PUSH_VEHICLE_DETAILS_NODE = "pushVehicleDetails";
    public static String SEARCH_VEHICLE_DETAILS_NODE = "searchVehicleDetails";
    private static String SITE_URL = "https://www.tradetu.com/rto/api/v1.0/";
    static String amazonAffiliateId = "dtashion-rto-apps-21";
    public static boolean catchExternalSiteLink = true;
    static String cueLinksBaseUrl = "https://linksredirect.com/?cid=28540&subid=sharedLink&source=linkkit&url=";
    public static String dataAccessKey = "";
    public static String dataAccessParams = "";
    public static String dataAccessPoint = "LOCAL";
    public static String dataAccessRouter = "";
    public static String dataAccessUrl = "";
    static String flipkartAffiliateId = "contacttr5";
    public static boolean isLogServerData = false;
    private static JSONObject jsonObject = null;
    public static String localSourceField1 = "";
    public static String localSourceField2 = "";
    public static String localSourceFinalUrl = "";
    public static String localSourceHostUrl = "";
    public static String localSourceInitUrl = "";
    private static JSONObject migrateAppConfig = null;
    public static boolean serverUnderMaintenance = false;
    static String twoGudAffiliateId = "contacttr";

    public interface Callback {
        void onConfigLoaded();
    }

    public static String prependSiteUrl(String str) {
        if (!Utils.isNullOrEmpty(SITE_URL) && !SITE_URL.endsWith("/")) {
            SITE_URL += "/";
        }
        return SITE_URL + str;
    }


    public static String prependAPIBaseUrl(String str) {
        if (!Utils.isNullOrEmpty(API_BASE_URL) && !API_BASE_URL.endsWith("/")) {
            API_BASE_URL += "/";
        }
        return API_BASE_URL + str;
    }

    public static void showAppMigrateDialog(Context context) {
        new AppMigrateNotifier().setJson(migrateAppConfig).showDialog(context);
    }

}
