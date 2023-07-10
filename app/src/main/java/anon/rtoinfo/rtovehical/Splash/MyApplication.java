package anon.rtoinfo.rtovehical.Splash;

import android.app.Application;


import com.google.firebase.analytics.FirebaseAnalytics;
import anon.rtoinfo.rtovehical.helpers.GlobalTracker;

public class MyApplication extends Application implements GlobalTracker.Provider{
    protected volatile GlobalTracker mTracker;

    public void onCreate() {
        super.onCreate();


    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public GlobalTracker getTracker() {
        GlobalTracker globalTracker = this.mTracker;
        if (globalTracker == null) {
            synchronized (this) {
                globalTracker = this.mTracker;
                if (globalTracker == null) {
                    GlobalTracker globalTracker2 = new GlobalTracker(FirebaseAnalytics.getInstance(this));
                    this.mTracker = globalTracker2;
                    globalTracker = globalTracker2;
                }
            }
        }
        return globalTracker;
    }
}
