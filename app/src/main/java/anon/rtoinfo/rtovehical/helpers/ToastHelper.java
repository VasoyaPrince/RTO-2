package anon.rtoinfo.rtovehical.helpers;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
    public static void showToast(Context context, String str, boolean z) {
        Toast.makeText(context, str, z ^ true ? 1 : 0).show();
    }
}
