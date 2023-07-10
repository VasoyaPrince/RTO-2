package anon.rtoinfo.rtovehical.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesHelper {
    public static boolean isClickToSeeAvailable() {
        return getSharedPreference().getBoolean("KEY_CLICK_TO_SEE", false);
    }

    public static String getKeyDeviceId() {
        return getSharedPreference().getString("KEY_DEVICE_ID", "");
    }

    public static void setKeyDeviceId(String str) {
        SharedPreferences.Editor edit = getSharedPreference().edit();
        edit.putString("KEY_DEVICE_ID", str);
        edit.apply();
    }

    private static SharedPreferences getSharedPreference() {
        return PreferenceManager.getDefaultSharedPreferences(GlobalContext.getInstance().getContext());
    }
}
