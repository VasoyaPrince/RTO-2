package anon.rtoinfo.rtovehical.handlers.response;

//import com.google.firebase.crashlytics.FirebaseCrashlytics;
import anon.rtoinfo.rtovehical.handlers.TaskHandler;

public class ExternalVehicleDetailsResponseHandler implements TaskHandler.ResponseHandler<String> {
    private TaskHandler.ResponseHandler<String> responseHandler;

    public ExternalVehicleDetailsResponseHandler(TaskHandler.ResponseHandler<String> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    public void onResponse(String str) {
        try {
            this.responseHandler.onResponse(str);
        } catch (Exception e) {
//            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }
}
