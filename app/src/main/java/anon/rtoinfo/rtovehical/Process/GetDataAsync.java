package anon.rtoinfo.rtovehical.Process;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import anon.rtoinfo.rtovehical.Activities.DetailActivity;
import anon.rtoinfo.rtovehical.Activities.MainActivity;
import anon.rtoinfo.rtovehical.Splash.Splash_Activity;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetDataAsync extends AsyncTask<String, Void, String> {
    private static int count = -1;
    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private ProgressDialog dialog;
    private final String reg1;
    private final String reg2;
    private final String reg3;
    private final String reg4;
    private String result = "";

    public GetDataAsync(Activity activity2, String str, String str2, String str3, String str4) {
        this.activity = activity2;
        this.reg1 = str;
        this.reg2 = str2;
        this.reg3 = str3;
        this.reg4 = str4;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
        this.dialog = new ProgressDialog(this.activity);
        this.dialog.setMessage("Getting Data Wait...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    /* access modifiers changed from: protected */
    public String doInBackground(String... strArr) {
        try {
            MainActivity.dataModel = getData(this.reg1 + this.reg2 + this.reg3 + this.reg4);
            this.result = "success";
        } catch (Exception e) {
            this.result = "";
            e.printStackTrace();
        }
        return this.result;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(String str) {
        super.onPostExecute(str);
        if (str.trim().length() <= 0) {
            this.dialog.dismiss();
            setNotify(this.activity, "Server Down Find information Using Send Sms.");
        } else if (MainActivity.dataModel != null && MainActivity.dataModel.size() > 1) {
            this.dialog.dismiss();
            Activity activity2 = this.activity;
            activity2.startActivity(new Intent(activity2, DetailActivity.class));
//            if (Splash_Activity.adModel.getIsfbEnable() == 1 && ((MainActivity)activity2).isFBLoaded()) {
//                ((MainActivity)activity2).showFBInterstitial();
//            }
        } else if (MainActivity.dataModel == null || MainActivity.dataModel.size() != 1) {
            this.dialog.dismiss();
            setNotify(this.activity, "Server down Please Find Information using send sms");
        } else {
            this.dialog.dismiss();
            Toast.makeText(this.activity, "Search Again", 0).show();
        }
    }

    public void setNotify(Activity activity2, String str) {
        new AlertDialog.Builder(activity2).setTitle("Alert").setMessage(str).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            /* class anon.rtoinfo.rtovehical.Process.GetDataAsync.AnonymousClass1 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    public static ArrayList getData(String str) {
        String str2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Matcher matcher = Pattern.compile("(\\w{2})(\\d{2})(\\D*?)(\\d{1,4})").matcher(str);
        if (matcher.matches()) {
            str2 = matcher.replaceFirst("$1$2$3-$4");
        } else {
            str2 = "";
        }
        String[] split = str2.split("-");
        String str3 = split[0];
        String str4 = split[1];
        try {
            Connection.Response execute = Jsoup.connect("https://parivahan.gov.in/rcdlstatus/?pur_cd=102").validateTLSCertificates(false).followRedirects(true).ignoreHttpErrors(true).method(Connection.Method.GET).execute();
            if (execute.statusCode() <= 500) {
                Map<String, String> cookies = execute.cookies();
                Document parse = Jsoup.parse(execute.body());
                Element first = parse.getElementsByAttributeValue("name", "javax.faces.ViewState").first();
                if (first == null) {
                    first = parse.getElementById("j_id1:javax.faces.ViewState:0");
                }
                String attr = first.attr("value");
                String trim = ((Element) Jsoup.parse(execute.body()).getElementsByAttributeValueStarting("id", "form_rcdl:j_idt").select("button").get(0)).attr("id").trim();
                String body = Jsoup.connect("https://parivahan.gov.in/rcdlstatus/vahan/rcDlHome.xhtml").validateTLSCertificates(false).followRedirects(true).method(Connection.Method.POST).cookies(cookies).referrer("https://parivahan.gov.in/rcdlstatus/?pur_cd=102").header("Content-Type", "application/x-www-form-urlencoded").header("Host", "parivahan.gov.in").header("Accept", "application/xml, text/xml, */*; q=0.01").header("Accept-Language", "en-US,en;q=0.5").header("Accept-Encoding", "gzip, deflate, br").header("X-Requested-With", "XMLHttpRequest").header("Faces-Request", "partial/ajax").header("Origin", "https://parivahan.gov.in").userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36").data("javax.faces.partial.ajax", "true").data("javax.faces.source", trim).data("javax.faces.partial.execute", "@all").data("javax.faces.partial.render", "form_rcdl:pnl_show form_rcdl:pg_show form_rcdl:rcdl_pnl").data(trim, trim).data("form_rcdl", "form_rcdl").data("form_rcdl:tf_reg_no1", str3).data("form_rcdl:tf_reg_no2", str4).data("javax.faces.ViewState", attr).execute().body();
                StringBuilder sb = new StringBuilder();
                sb.append("str2 ");
                sb.append(body);
                if (body.contains("Registration No. does not exist!!!")) {
                    arrayList2.add(0, "No Record(s) Found");
                } else {
                    Document parse2 = Jsoup.parse("<!DOCTYPE html><html><body>" + body.substring(body.indexOf("<table"), body.lastIndexOf("</table>")) + "</body></html>");
                    int indexOf = body.indexOf("<div class=\"font-bold top-space bottom-space text-capitalize\">") + 62;
                    body.substring(indexOf, body.indexOf("</div>", indexOf)).replaceAll("Registering Authority:", "").trim();
                    Element first2 = parse2.select("table").first();
                    if (first2 != null) {
                        Elements select = first2.select("tr");
                        for (int i = 0; i < select.size(); i++) {
                            Elements select2 = ((Element) select.get(i)).select("td");
                            for (int i2 = 0; i2 < select2.size(); i2++) {
                                String text = ((Element) select2.get(i2)).text();
                                Log.e("text", text);
                                if (text.trim().equals("Owner Name:") || count == i2) {
                                    if (count == -1) {
                                        count = i2 + 1;
                                    } else {
                                        count = -1;
                                    }
                                    arrayList2.add(text);
                                } else {
                                    arrayList.add(text);
                                }
                            }
                        }
                        arrayList2.addAll(arrayList);
                    } else {
                        arrayList2.add(0, "No Record(s) Found");
                    }
                }
            } else {
                arrayList2.add(0, "500 Server Error");
            }
        } catch (Exception unused) {
            arrayList2.add(0, "Server Error");
        }
        return arrayList2;
    }
}
