package anon.rtoinfo.rtovehical.helpers;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;

import anon.rtoinfo.rtovehical.R;
import anon.rtoinfo.rtovehical.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class AppMigrateNotifier {
    private String appPackageName;
    private String cta;
    private boolean enabled;
    private String message;
    private String title;

    public AppMigrateNotifier setJson(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                this.title = jSONObject.getString("title");
                this.message = jSONObject.getString("message");
                this.cta = jSONObject.getString("cta");
                this.appPackageName = jSONObject.getString("appPackageName");
                this.enabled = jSONObject.getBoolean("enabled");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void showDialog(Context context) {
        if (isEnabled() && !Utils.isNullOrEmpty(this.appPackageName)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setPositiveButton(this.cta, new DialogInterface.OnClickListener() {

                public final void onClick(DialogInterface dialogInterface, int i) {
                    AppMigrateNotifier.this.lambda$showDialog$0$AppMigrateNotifier(context, dialogInterface, i);
                }
            });
            builder.setTitle(this.title);
            builder.setMessage(this.message);
            builder.show();
        }
    }

    public void lambda$showDialog$0$AppMigrateNotifier(Context context, DialogInterface dialogInterface, int i) {
        String str;
        if (this.appPackageName.startsWith("http://") || this.appPackageName.startsWith("https://") || this.appPackageName.startsWith("market://")) {
            str = this.appPackageName;
        } else {
            str = "https://play.google.com/store/apps/details?id=" + this.appPackageName;
        }
        Utils.openWebPage(context, str);
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Copied", str));
        ToastHelper.showToast(context, context.getString(R.string.copied_item_txt), true);
    }
}
