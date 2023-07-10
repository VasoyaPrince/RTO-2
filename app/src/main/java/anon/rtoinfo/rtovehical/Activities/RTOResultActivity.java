package anon.rtoinfo.rtovehical.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import anon.rtoinfo.rtovehical.R;
import anon.rtoinfo.rtovehical.RTOExamDatabase.RTO_Variable;

import java.util.Collections;

public class RTOResultActivity extends AppCompatActivity {
    ImageView btn_back;
    TextView percentage;
    TextView retry_exam;
    TextView rightanswer;
    TextView wrongquestion;
    private ImageView ivbanner;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_rto_result);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(getResources().getColor(R.color.white));

        this.btn_back = (ImageView) findViewById(R.id.btn_back);
        this.btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTOResultActivity.this.onBackPressed();
            }
        });
        this.rightanswer = (TextView) findViewById(R.id.textView2);
        this.wrongquestion = (TextView) findViewById(R.id.textView3);
        this.percentage = (TextView) findViewById(R.id.textView4);


        TextView textView = this.rightanswer;
        textView.setText(RTO_Variable.getInstance().right_score + "/30");
        TextView textView2 = this.wrongquestion;
        textView2.setText(RTO_Variable.getInstance().wrong_score + "/30");
        TextView textView3 = this.percentage;
        textView3.setText(((RTO_Variable.getInstance().right_score * 100) / 30) + "%");
        this.retry_exam = (TextView) findViewById(R.id.retry_exam);
        this.retry_exam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTO_Variable.getInstance().nlist.clear();
                for (int i = 0; i < 208; i++) {
                    RTO_Variable.getInstance().nlist.add(Integer.valueOf(i));
                }
                Intent intent = new Intent(RTOResultActivity.this, RTOExamActivity.class);
                RTO_Variable.getInstance().counter = 30;
                Collections.shuffle(RTO_Variable.getInstance().nlist);
                RTO_Variable.getInstance().right_score = 0;
                RTO_Variable.getInstance().wrong_score = 0;
                RTO_Variable.getInstance().qid = 0;
                RTOResultActivity.this.startActivity(intent);
                RTOResultActivity.this.finish();
            }
        });
    }

    public void onBackPressed() {
        finish();
    }
}
