package anon.rtoinfo.rtovehical.handlers.request;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import anon.rtoinfo.rtovehical.handlers.TaskHandler;
import anon.rtoinfo.rtovehical.helpers.GlobalTracker;
import anon.rtoinfo.rtovehical.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RequestLoader {
    public Context context;
    public boolean isProgressDialogShowing;
    public TaskHandler.JsonResponseHandler jsonResponseHandler;
    public TaskHandler mInstance;
    Map<String, Object> params;
    public ProgressDialog progressDialog;
    int requestMethod;
    public String requestUrl;
    public TaskHandler.ResponseHandler<String> responseHandler;
    String tag;

    public RequestLoader(TaskHandler taskHandler, int i, Map<String, Object> map, String str, boolean z, Context context2, ProgressDialog progressDialog2, TaskHandler.JsonResponseHandler jsonResponseHandler2, String str2) {
        this.mInstance = taskHandler;
        this.requestMethod = i;
        this.params = map;
        this.requestUrl = str;
        this.isProgressDialogShowing = z;
        this.context = context2;
        this.progressDialog = progressDialog2;
        this.jsonResponseHandler = jsonResponseHandler2;
        this.tag = str2;
    }

    public RequestLoader(TaskHandler taskHandler, int i, Map<String, Object> map, String str, boolean z, Context context2, ProgressDialog progressDialog2, TaskHandler.ResponseHandler<String> responseHandler2, String str2) {
        this.mInstance = taskHandler;
        this.requestMethod = i;
        this.params = map;
        this.requestUrl = str;
        this.isProgressDialogShowing = z;
        this.context = context2;
        this.progressDialog = progressDialog2;
        this.responseHandler = responseHandler2;
        this.tag = str2;
    }

    public void request() {
        JSONObject jSONObject;
        String str;
        StringBuilder sb = new StringBuilder();
        if (this.requestMethod == 0) {
            for (Map.Entry next : this.params.entrySet()) {
                sb.append("&");
                sb.append((String) next.getKey());
                sb.append("=");
                sb.append(this.mInstance.encodeString((String) next.getValue()));
            }
            jSONObject = null;
        } else {
            jSONObject = new JSONObject(this.params);
        }
        JSONObject jSONObject2 = jSONObject;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.requestUrl);
        if (sb.length() == 0) {
            str = "";
        } else {
            str = "?" + sb;
        }
        sb2.append(str);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(this.requestMethod, sb2.toString(), jSONObject2, new JsonResponseListener(this), new RequestErrorListener(this));
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(250000, 1, 1.0f));
        CustomRequestQueue.getInstance(this.context).addToRequestQueue(jsonObjectRequest, this.tag);
    }

    public void requestExternal() {
        StringRequest r0 = new StringRequest(this.requestMethod, this.requestUrl, new Response.Listener() {
            public final void onResponse(Object obj) {
                RequestLoader.this.lambda$requestExternal$0$RequestLoader((String) obj);
            }
        }, new RequestErrorListener(this)) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                for (Map.Entry next : RequestLoader.this.params.entrySet()) {
                    if (next.getValue() instanceof String) {
                        hashMap.put(next.getKey(), (String) next.getValue());
                    }
                }
                return hashMap;
            }

            public Map<String, String> getHeaders() {
                HashMap hashMap = new HashMap();
                hashMap.put("Content-Type", "application/x-www-form-urlencoded");
                return hashMap;
            }
        };
        r0.setRetryPolicy(new DefaultRetryPolicy(250000, 1, 1.0f));
        CustomRequestQueue.getInstance(this.context).addToRequestQueue(r0, this.tag);
    }

    public /* synthetic */ void lambda$requestExternal$0$RequestLoader(String str) {
        ProgressDialog progressDialog2;
        if (this.isProgressDialogShowing && Utils.isActivityFinished(this.context) && (progressDialog2 = this.progressDialog) != null && progressDialog2.isShowing()) {
            this.mInstance.cancelProgressDialog(this.progressDialog);
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putString("E_VEHICLE_DETAILS_PARAMS", this.params.toString());
            bundle.putString("E_VEHICLE_DETAILS_RESPONSE", str);
            bundle.putString("content_type", "E_VEHICLE_DETAILS");
            GlobalTracker.from(this.context).sendCustomEvent("E_VEHICLE_DETAILS_EVENT", bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.responseHandler.onResponse(str);
    }
}
