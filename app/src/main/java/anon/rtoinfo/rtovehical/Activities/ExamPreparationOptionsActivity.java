package anon.rtoinfo.rtovehical.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import anon.rtoinfo.rtovehical.R;
import anon.rtoinfo.rtovehical.RTOExamDatabase.RTO_Variable;

import java.util.Collections;

public class ExamPreparationOptionsActivity extends AppCompatActivity {
    public static Boolean sign_or_not = false;
    LinearLayout driving_questions;
    SharedPreferences prefs;
    RadioGroup radioLangauge;
    RadioButton radioenglish;
    RadioButton radiogujarati;
    RadioButton radiohindi;
    LinearLayout signsymbol_questions;
    TextView startexam;
    private ImageView back_btn;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_exam_preparation_options);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(getResources().getColor(R.color.white));

//        initAdmobFullAd(this);
//        loadAdmobAd();
//
//        FrameLayout adMobView = (FrameLayout) findViewById(R.id.adMobView);
//        showFBBanner(adMobView);
//        FrameLayout adMobView1 = (FrameLayout) findViewById(R.id.adMobView1);
//        showBanner(adMobView1);

        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.radioLangauge = (RadioGroup) findViewById(R.id.radioLangauge);
        this.radioenglish = (RadioButton) findViewById(R.id.radioenglish);
        this.radiohindi = (RadioButton) findViewById(R.id.radiohindi);
        this.radiogujarati = (RadioButton) findViewById(R.id.radiogujarati);
        this.startexam = (TextView) findViewById(R.id.startexam);
        this.prefs = getSharedPreferences("settings", 0);
        this.driving_questions = (LinearLayout) findViewById(R.id.driving_questions);
        this.driving_questions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ExamPreparationOptionsActivity.sign_or_not = false;
                openexampreparation();
            }
        });
        this.signsymbol_questions = (LinearLayout) findViewById(R.id.signsymbol_questions);
        this.signsymbol_questions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ExamPreparationOptionsActivity.sign_or_not = true;
                openexampreparation();
            }
        });
        this.startexam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int checkedRadioButtonId = radioLangauge.getCheckedRadioButtonId();
                if (checkedRadioButtonId == radioenglish.getId()) {
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putInt("langId", 1);
                    edit.apply();
                    Intent intent = new Intent(ExamPreparationOptionsActivity.this, RTOExamActivity.class);
                    RTO_Variable.getInstance().counter = 30;
                    Collections.shuffle(RTO_Variable.getInstance().nlist);
                    RTO_Variable.getInstance().right_score = 0;
                    RTO_Variable.getInstance().wrong_score = 0;
                    RTO_Variable.getInstance().qid = 0;
                    startActivity(intent);
                } else if (checkedRadioButtonId == radiohindi.getId()) {
                    SharedPreferences.Editor edit2 = prefs.edit();
                    edit2.putInt("langId", 2);
                    edit2.apply();
                    Intent intent2 = new Intent(ExamPreparationOptionsActivity.this, RTOExamActivity.class);
                    RTO_Variable.getInstance().counter = 30;
                    Collections.shuffle(RTO_Variable.getInstance().nlist);
                    RTO_Variable.getInstance().right_score = 0;
                    RTO_Variable.getInstance().wrong_score = 0;
                    RTO_Variable.getInstance().qid = 0;
                    startActivity(intent2);
                } else if (checkedRadioButtonId == radiogujarati.getId()) {
                    SharedPreferences.Editor edit3 = prefs.edit();
                    edit3.putInt("langId", 3);
                    edit3.apply();
                    Intent intent3 = new Intent(ExamPreparationOptionsActivity.this, RTOExamActivity.class);
                    RTO_Variable.getInstance().counter = 30;
                    Collections.shuffle(RTO_Variable.getInstance().nlist);
                    RTO_Variable.getInstance().right_score = 0;
                    RTO_Variable.getInstance().wrong_score = 0;
                    RTO_Variable.getInstance().qid = 0;
                    startActivity(intent3);
                } else {
                    Toast.makeText(ExamPreparationOptionsActivity.this, "Select Language", 0).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void openexampreparation() {
        int checkedRadioButtonId = this.radioLangauge.getCheckedRadioButtonId();
        if (checkedRadioButtonId == this.radioenglish.getId()) {
            SharedPreferences.Editor edit = this.prefs.edit();
            edit.putInt("langId", 1);
            edit.apply();
            startActivity(new Intent(this, RTOExamPreparationActivity.class));
        } else if (checkedRadioButtonId == this.radiohindi.getId()) {
            SharedPreferences.Editor edit2 = this.prefs.edit();
            edit2.putInt("langId", 2);
            edit2.apply();
            startActivity(new Intent(this, RTOExamPreparationActivity.class));
        } else if (checkedRadioButtonId == this.radiogujarati.getId()) {
            SharedPreferences.Editor edit3 = this.prefs.edit();
            edit3.putInt("langId", 3);
            edit3.apply();
            startActivity(new Intent(this, RTOExamPreparationActivity.class));
        } else {
            Toast.makeText(this, "Select Language", 0).show();
        }
//        if (Splash_Activity.adModel.getIsAdmobEnable() == 1 && isAdmobLoaded()) {
//            showAdmobInterstitial();
//        }
    }
}