package anon.rtoinfo.rtovehical.utils;

import android.content.Context;

public class GlobalContext {
    private static GlobalContext mInstance;
    private Context context;

    private GlobalContext(Context context2) {
        if (this.context == null) {
            this.context = context2;
        }
    }

    public static void initialize(Context context2) {
        if (mInstance == null) {
            mInstance = new GlobalContext(context2);
        }
    }

    public static GlobalContext getInstance() {
        return mInstance;
    }

    public Context getContext() {
        return this.context;
    }
}
