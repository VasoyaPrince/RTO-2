package anon.rtoinfo.rtovehical.handlers.request;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import anon.rtoinfo.rtovehical.R;
import anon.rtoinfo.rtovehical.utils.Utils;


public class RequestErrorListener implements Response.ErrorListener {
    private RequestLoader requestLoader;

    public RequestErrorListener(RequestLoader requestLoader2) {
        this.requestLoader = requestLoader2;
    }

    public void onErrorResponse(VolleyError volleyError) {
        if (this.requestLoader.isProgressDialogShowing && Utils.isActivityFinished(this.requestLoader.context) && this.requestLoader.progressDialog != null && this.requestLoader.progressDialog.isShowing()) {
            this.requestLoader.mInstance.cancelProgressDialog(this.requestLoader.progressDialog);
        }
        NetworkResponse networkResponse = volleyError.networkResponse;
        if (networkResponse != null) {
            int i = networkResponse.statusCode;
            try {
                Log.e(this.requestLoader.getClass().getSimpleName(), "onErrorResponse: ");
            } catch (Exception e) {
                e.printStackTrace();
            }
            String simpleName = this.requestLoader.getClass().getSimpleName();
            Log.d(simpleName, "onErrorResponse: " + volleyError);
            if (this.requestLoader.jsonResponseHandler != null) {
                this.requestLoader.jsonResponseHandler.onError(this.requestLoader.context.getString(R.string.no_info));
            } else if (this.requestLoader.responseHandler != null) {
                this.requestLoader.responseHandler.onError(this.requestLoader.context.getString(R.string.no_info));
            }
        } else if (this.requestLoader.jsonResponseHandler != null) {
            this.requestLoader.jsonResponseHandler.onError(this.requestLoader.context.getString(R.string.no_info));
        } else if (this.requestLoader.responseHandler != null) {
            this.requestLoader.responseHandler.onError(this.requestLoader.context.getString(R.string.no_info));
        }
    }
}
