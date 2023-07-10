package anon.rtoinfo.rtovehical.helpers;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;


public class GlobalTracker {
    public static final String BUTTON = "BUTTON";
    public static final String EVENT_LOG_VEHICLE_DETAILS = "EVENT_LOG_VEHICLE_DETAILS";
    public static final String EVENT_VEHICLE_NO = "VEHICLE_NO";
    private static final String TAG = "GlobalTracker";

    public interface Provider {
        GlobalTracker getTracker();
    }

    public GlobalTracker(FirebaseAnalytics firebaseAnalytics) {

        Log.d(TAG, "Initialized analytics facade.");
    }

    public static GlobalTracker from(Context context) {
        return ((Provider) context.getApplicationContext()).getTracker();
    }

    public static GlobalTracker from(Fragment fragment) {
        return from((Context) fragment.getActivity());
    }

    public void sendSelectContentEvent(Bundle bundle) {
        Log.i(TAG, FirebaseAnalytics.Event.SELECT_CONTENT);

    }

    public void sendViewItemEvent(Bundle bundle) {

    }

    public void sendViewItemListEvent(Bundle bundle) {

    }

    public void sendCustomEvent(String str, Bundle bundle) {

    }

    public void getAnalytics() {
        return ;
    }
}
