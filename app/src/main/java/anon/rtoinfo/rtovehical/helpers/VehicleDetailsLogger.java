package anon.rtoinfo.rtovehical.helpers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import anon.rtoinfo.rtovehical.datamodels.VehicleDetails;
import anon.rtoinfo.rtovehical.handlers.TaskHandler;
import anon.rtoinfo.rtovehical.utils.GlobalReferenceEngine;
import anon.rtoinfo.rtovehical.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;


public class VehicleDetailsLogger {
    public static void logVehicleDetails(final Activity activity, final VehicleDetails vehicleDetails) {
        if (GlobalReferenceEngine.isLogServerData && Utils.isNetworkConnected(activity) && vehicleDetails != null && !vehicleDetails.isEmptyResponse()) {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put("registrationNo", vehicleDetails.getRegistrationNo());
                hashMap.put("details", new Gson().toJson((Object) vehicleDetails));
                TaskHandler.newInstance().pushVehicleDetails(activity, hashMap, new TaskHandler.ResponseHandler<JSONObject>() {
                    public void onError(String str) {
                        Bundle bundle = new Bundle();
                        bundle.putString(GlobalTracker.EVENT_LOG_VEHICLE_DETAILS, "Error in logging vehicle details: " + vehicleDetails.getRegistrationNo());
                        GlobalTracker.from((Context) activity).sendViewItemEvent(bundle);
                    }

                    public void onResponse(JSONObject jSONObject) {
                        Bundle bundle = new Bundle();
                        bundle.putString(GlobalTracker.EVENT_LOG_VEHICLE_DETAILS, "Success in logging vehicle details: " + vehicleDetails.getRegistrationNo());
                        GlobalTracker.from((Context) activity).sendViewItemEvent(bundle);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
