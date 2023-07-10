package anon.rtoinfo.rtovehical.handlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;

import anon.rtoinfo.rtovehical.handlers.request.RequestLoader;
import anon.rtoinfo.rtovehical.handlers.response.ExternalVehicleDetailsResponseHandler;
import anon.rtoinfo.rtovehical.handlers.response.LogVehicleDetailsResponseHandler;
import anon.rtoinfo.rtovehical.handlers.response.VehicleDetailsResponseHandler;
import anon.rtoinfo.rtovehical.helpers.EncryptionHandler;
import anon.rtoinfo.rtovehical.utils.GlobalContext;
import anon.rtoinfo.rtovehical.utils.GlobalReferenceEngine;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import anon.rtoinfo.rtovehical.R;
import anon.rtoinfo.rtovehical.utils.Utils;

public class TaskHandler {
    private static TaskHandler mInstance;

    public interface JsonResponseHandler {
        void onError(String str);

        void onResponse(JSONObject jSONObject);
    }

    public interface ResponseHandler<T> {
        void onError(String str);

        void onResponse(T t);
    }

    public static TaskHandler newInstance() {
        if (mInstance == null) {
            mInstance = new TaskHandler();
        }
        return mInstance;
    }

    public void fetchVehicleDetails(Context context, String str, boolean z, boolean z2, boolean z3, ResponseHandler<JSONObject> responseHandler) {
        HashMap hashMap = new HashMap();
        hashMap.put("registrationNo", str);
        hashMap.put("key_skip_db", Boolean.valueOf(z));
        hashMap.put("extra", Boolean.valueOf(z2));
        requestUrl(context, 1, GlobalReferenceEngine.prependAPIBaseUrl(GlobalReferenceEngine.SEARCH_VEHICLE_DETAILS_NODE), "tag_vehicle_details", hashMap, z3 ? context.getString(R.string.loading) : null, new VehicleDetailsResponseHandler(responseHandler));
    }

    public void fetchVehicleDetails(Context context, String str, String str2, boolean z, ResponseHandler<JSONObject> responseHandler) {
        HashMap hashMap = new HashMap();
        hashMap.put("registrationNo", str);
        hashMap.put("data", str2);
        requestUrl(context, 1, GlobalReferenceEngine.prependAPIBaseUrl("compileVehicleDetails"), "tag_compile_vehicle_details", hashMap, z ? context.getString(R.string.loading) : null, new VehicleDetailsResponseHandler(responseHandler));
    }

    public void fetchVehicleDetails(Context context, String str, String[] strArr, boolean z, ResponseHandler<String> responseHandler) {
        HashMap hashMap = new HashMap();
        for (String split : strArr) {
            String[] split2 = split.split("=");
            if (split2.length > 1) {
                if (split2[1].equalsIgnoreCase("REG_NO")) {
                    hashMap.put(split2[0], split2[1].replaceFirst("REG_NO", EncryptionHandler.encrypt(str)));
                } else if (split2[1].equalsIgnoreCase("OS")) {
                    hashMap.put(split2[0], split2[1].replaceFirst("OS", EncryptionHandler.encrypt(Build.VERSION.RELEASE)));
                } else if (split2[1].equalsIgnoreCase("MO")) {
                    hashMap.put(split2[0], split2[1].replaceFirst("MO", EncryptionHandler.encrypt(Build.MODEL)));
                } else if (split2[1].equalsIgnoreCase("DI")) {
                    hashMap.put(split2[0], split2[1].replaceFirst("DI", EncryptionHandler.encrypt(UUID.randomUUID().toString())));
                } else {
                    hashMap.put(split2[0], EncryptionHandler.encrypt(split2[1]));
                }
            }
        }
        requestExternalUrl(context, 1, GlobalReferenceEngine.dataAccessUrl, "tag_external_vehicle_details", hashMap, z ? context.getString(R.string.loading) : null, new ExternalVehicleDetailsResponseHandler(responseHandler));
    }

    public void pushVehicleDetails(Context context, Map<String, Object> map, ResponseHandler<JSONObject> responseHandler) {
        requestUrl(context, 1, GlobalReferenceEngine.prependAPIBaseUrl(GlobalReferenceEngine.PUSH_VEHICLE_DETAILS_NODE), "tag_push_vehicle_details", map, "", new LogVehicleDetailsResponseHandler(responseHandler));
    }

    private void requestUrl(Context context, int i, String str, String str2, Map<String, Object> map, String str3, JsonResponseHandler jsonResponseHandler) {
        ProgressDialog progressDialog;
        boolean z;
        Context context2 = context;
        if (Utils.isNullOrEmpty(str3) || !Utils.isActivityFinished(context)) {
            progressDialog = null;
            z = false;
        } else {
            ProgressDialog progressDialog2 = new ProgressDialog(context);
            progressDialog2.setMessage(str3);
            progressDialog2.setCancelable(false);
            progressDialog2.setCanceledOnTouchOutside(false);
            progressDialog2.show();
            progressDialog = progressDialog2;
            z = true;
        }
        if (context2 == null) {
            context2 = GlobalContext.getInstance().getContext();
        }
        new RequestLoader(this, i, map, str, z, context2, progressDialog, jsonResponseHandler, str2).request();
    }

    private void requestExternalUrl(Context context, int i, String str, String str2, Map<String, Object> map, String str3, ResponseHandler<String> responseHandler) {
        ProgressDialog progressDialog;
        boolean z;
        Context context2 = context;
        if (Utils.isNullOrEmpty(str3) || !Utils.isActivityFinished(context)) {
            progressDialog = null;
            z = false;
        } else {
            ProgressDialog progressDialog2 = new ProgressDialog(context);
            progressDialog2.setMessage(str3);
            progressDialog2.setCancelable(false);
            progressDialog2.setCanceledOnTouchOutside(false);
            progressDialog2.show();
            progressDialog = progressDialog2;
            z = true;
        }
        if (context2 == null) {
            context2 = GlobalContext.getInstance().getContext();
        }
        new RequestLoader(this, i, map, str, z, context2, progressDialog, responseHandler, str2).requestExternal();
    }

    public String encodeString(String str) {
        if (str == null) {
            return "";
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public void cancelProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            try {
                if (progressDialog.isShowing()) {
                    progressDialog.cancel();
                    progressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
