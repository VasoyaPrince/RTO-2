package anon.rtoinfo.rtovehical.handlers.request;

import com.android.volley.Response;
import anon.rtoinfo.rtovehical.utils.Utils;
//import com.tradetu.trendingapps.vehicleregistrationdetails.utils.Utils;

import org.json.JSONObject;

public class JsonResponseListener implements Response.Listener<JSONObject> {
    private RequestLoader requestLoader;

    public JsonResponseListener(RequestLoader requestLoader2) {
        this.requestLoader = requestLoader2;
    }

    public void onResponse(JSONObject jSONObject) {
        if (this.requestLoader.isProgressDialogShowing && Utils.isActivityFinished(this.requestLoader.context) && this.requestLoader.progressDialog != null && this.requestLoader.progressDialog.isShowing()) {
            this.requestLoader.mInstance.cancelProgressDialog(this.requestLoader.progressDialog);
        }
        this.requestLoader.jsonResponseHandler.onResponse(jSONObject);
    }
}
