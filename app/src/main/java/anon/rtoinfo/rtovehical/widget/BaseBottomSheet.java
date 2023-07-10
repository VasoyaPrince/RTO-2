package anon.rtoinfo.rtovehical.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import anon.rtoinfo.rtovehical.R;

public class BaseBottomSheet extends BottomSheetDialogFragment {
    private Context context;
    private String message;
    private String title;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.title = getArguments().getString("TITLE");
        this.message = getArguments().getString("MESSAGE");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.bottom_sheet_acknowledgement, viewGroup, false);
        TextView textView = (TextView) inflate.findViewById(R.id.cta);
        TextView textView2 = (TextView) inflate.findViewById(R.id.secondaryBtn);
        ((TextView) inflate.findViewById(R.id.title)).setText(this.title);
        ((TextView) inflate.findViewById(R.id.message)).setText(this.message);
        textView2.setText(R.string.txt_not_now);
        textView.setText(R.string.txt_open_settings);
        textView2.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                BaseBottomSheet.this.lambda$onCreateView$0$BaseBottomSheet(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                BaseBottomSheet.this.lambda$onCreateView$1$BaseBottomSheet(view);
            }
        });
        return inflate;
    }

    public /* synthetic */ void lambda$onCreateView$0$BaseBottomSheet(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$onCreateView$1$BaseBottomSheet(View view) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", this.context.getPackageName(), (String) null));
        this.context.startActivity(intent);
        dismiss();
    }

    public void onAttach(Context context2) {
        super.onAttach(context2);
        this.context = context2;
    }
}
