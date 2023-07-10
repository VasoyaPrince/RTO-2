package anon.rtoinfo.rtovehical.handlers.response;

//import com.google.firebase.crashlytics.FirebaseCrashlytics;

import anon.rtoinfo.rtovehical.handlers.TaskHandler;

import org.json.JSONObject;

public class VehicleDetailsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<JSONObject> responseHandler;

    public VehicleDetailsResponseHandler(TaskHandler.ResponseHandler<JSONObject> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse(jSONObject);
        } catch (Exception e) {
//            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }
}
