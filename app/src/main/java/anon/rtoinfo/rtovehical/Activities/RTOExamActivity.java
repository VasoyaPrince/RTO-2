package anon.rtoinfo.rtovehical.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import anon.rtoinfo.rtovehical.utils.ProgressWheel;

import anon.rtoinfo.rtovehical.Examdata.RTOExamData;
import anon.rtoinfo.rtovehical.R;
import anon.rtoinfo.rtovehical.RTOExamDatabase.RTO_Question;
import anon.rtoinfo.rtovehical.RTOExamDatabase.RTO_Variable;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RTOExamActivity extends AppCompatActivity {
    public static Boolean nextclicked = false;
    public static Boolean option1selected;
    public static Boolean option2selected;
    public static Boolean option3selected = false;
    TextView Qno;
    TextView atext;
    TextView btext;
    ImageView btn_back;
    TextView butFinish;
    TextView butNext;
    ProgressWheel countdown_timer;
    TextView ctext;
    RTO_Question currentQ;
    Dialog dialogTransparent;
    final Handler handler = new Handler();
    ImageView img;
    LinearLayout img_bg;
    public Handler mHandler = new Handler();
    public MediaPlayer mp;
    public MediaPlayer mp1;
    public MediaPlayer mp2;
    TextView op1;
    TextView op2;
    TextView op3;
    LinearLayout opp1;
    LinearLayout opp2;
    LinearLayout opp3;
    List<RTO_Question> quesList;
    public Runnable runnable = new Runnable() {
        public void run() {
            if (value_timer >= 0) {
                tv_timer.setText(String.valueOf(value_timer));

                countdown_timer.setText(String.valueOf(value_timer));
                countdown_timer.setProgress(value_timer * 12);
                mHandler.postDelayed(this, 1000);
                value_timer--;
                return;
            }
            checkAnswer();
            RTOExamActivity.option3selected = true;
        }

        private void checkAnswer() {
            RTO_Variable.getInstance().wrong_score++;
            if (currentQ.getANSWER().intValue() == 1) {
                opp1.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                op1.setTextColor(getResources().getColor(R.color.colorAccent));
                atext.setTextColor(getResources().getColor(R.color.colorAccent));
                true_icon1.setVisibility(0);
            } else if (currentQ.getANSWER().intValue() == 2) {
                opp2.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                op2.setTextColor(getResources().getColor(R.color.colorAccent));
                btext.setTextColor(getResources().getColor(R.color.colorAccent));
                true_icon2.setVisibility(0);
            } else {
                opp3.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                op3.setTextColor(getResources().getColor(R.color.colorAccent));
                ctext.setTextColor(getResources().getColor(R.color.colorAccent));
                true_icon3.setVisibility(0);
            }
        }
    };
    Timer timer;
    TimerTask timerTask;
    int timertick = 30;
    ImageView true_icon1;
    ImageView true_icon2;
    ImageView true_icon3;
    TextView txtQuestion, tv_timer;
    int value_timer = 30;
    Vibrator vibrator;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_rtoexam);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(getResources().getColor(R.color.white));

        FrameLayout adMobView = (FrameLayout) findViewById(R.id.adMobView);
//        showBanner(adMobView);
//
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        initFBFullAd();
//        loadFBAd();

        option1selected = false;
        option2selected = false;
        option3selected = false;
        this.mp = MediaPlayer.create(this, (int) R.raw.win);
        this.mp1 = MediaPlayer.create(this, (int) R.raw.timer);
        this.mp2 = MediaPlayer.create(this, (int) R.raw.wrong);
        this.btn_back = (ImageView) findViewById(R.id.btn_back);
        this.btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.true_icon1 = (ImageView) findViewById(R.id.true_icon1);
        this.true_icon2 = (ImageView) findViewById(R.id.true_icon2);
        this.true_icon3 = (ImageView) findViewById(R.id.true_icon3);
        this.quesList = new RTOExamData(this).getAllQuestions(Integer.valueOf(getSharedPreferences("settings", 0).getInt("langId", 1)));
        this.currentQ = this.quesList.get(RTO_Variable.getInstance().nlist.get(RTO_Variable.getInstance().qid).intValue());
        this.tv_timer = (TextView) findViewById(R.id.tv_timer);
        this.txtQuestion = (TextView) findViewById(R.id.textView1);
        this.Qno = (TextView) findViewById(R.id.qno);
        this.op1 = (TextView) findViewById(R.id.op1);
        this.op2 = (TextView) findViewById(R.id.op2);
        this.op3 = (TextView) findViewById(R.id.op3);
        this.atext = (TextView) findViewById(R.id.atext);
        this.btext = (TextView) findViewById(R.id.btext);
        this.ctext = (TextView) findViewById(R.id.ctext);
        this.img = (ImageView) findViewById(R.id.img);
        this.img_bg = (LinearLayout) findViewById(R.id.img_bg);
        this.opp1 = (LinearLayout) findViewById(R.id.opp1);
        this.opp2 = (LinearLayout) findViewById(R.id.opp2);
        this.opp3 = (LinearLayout) findViewById(R.id.opp3);
        this.butNext = (TextView) findViewById(R.id.button1);
        this.butFinish = (TextView) findViewById(R.id.button2);
        this.countdown_timer = (ProgressWheel) findViewById(R.id.countdown_timer);
        setQuestionView();
        this.vibrator = (Vibrator) getSystemService("vibrator");
        startTimer();
        this.mHandler.post(this.runnable);
        this.butFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (RTO_Variable.getInstance().adc % 2 == 0) {
                    showResult();
                    stoptimertask();
                }
            }
        });
        this.butNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTOExamActivity.nextclicked = true;
                if (RTOExamActivity.option1selected.booleanValue() || RTOExamActivity.option2selected.booleanValue() || RTOExamActivity.option3selected.booleanValue()) {
                    stoptimertask();
                    if (RTO_Variable.getInstance().qid < RTO_Variable.getInstance().counter) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                mp.stop();
                                mp.release();
                                mp2.stop();
                                mp2.release();
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        }, 100);
                        return;
                    }
                    Intent intent = new Intent(RTOExamActivity.this, RTOResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("right_score", RTO_Variable.getInstance().right_score);
                    bundle.putInt("wrong_score", RTO_Variable.getInstance().wrong_score);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
//                    if (Splash_Activity.adModel.getIsfbEnable() == 1 && isFBLoaded()) {
//                        showFBInterstitial();
//                    }
                    return;
                }
                Toast makeText = Toast.makeText(getApplicationContext(), "Answer the question", 0);
                makeText.setGravity(17, 0, 0);
                makeText.show();
            }
        });
        this.opp1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!RTOExamActivity.option1selected.booleanValue() && !RTOExamActivity.option2selected.booleanValue() && !RTOExamActivity.option3selected.booleanValue()) {
                    mHandler.removeCallbacks(runnable);
                    if (currentQ.getANSWER().intValue() == 1) {
                        RTO_Variable.getInstance().right_score++;
                        mp.start();
                        true_icon1.setVisibility(0);
                        opp1.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                        op1.setTextColor(getResources().getColor(R.color.colorAccent));
                        atext.setTextColor(getResources().getColor(R.color.colorAccent));
                    } else {
                        RTO_Variable.getInstance().wrong_score++;
                        opp1.setBackground(getResources().getDrawable(R.drawable.bg_option_wrong));
                        op1.setTextColor(getResources().getColor(R.color.red));
                        atext.setTextColor(getResources().getColor(R.color.red));
                        mp2.start();
                        vibrator.vibrate(500);
                        if (currentQ.getANSWER().intValue() == 2) {
                            true_icon2.setVisibility(0);
                            opp2.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                            op2.setTextColor(getResources().getColor(R.color.colorAccent));
                            btext.setTextColor(getResources().getColor(R.color.colorAccent));
                        } else {
                            true_icon3.setVisibility(0);
                            opp3.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                            op3.setTextColor(getResources().getColor(R.color.colorAccent));
                            ctext.setTextColor(getResources().getColor(R.color.colorAccent));
                        }
                    }
                    stoptimertask();
                    RTOExamActivity.option1selected = true;
                }
            }
        });
        this.opp2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!RTOExamActivity.option1selected.booleanValue() && !RTOExamActivity.option2selected.booleanValue() && !RTOExamActivity.option3selected.booleanValue()) {
                    mHandler.removeCallbacks(runnable);
                    if (currentQ.getANSWER().intValue() == 2) {
                        RTO_Variable.getInstance().right_score++;
                        mp.start();
                        true_icon2.setVisibility(0);
                        opp2.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                        op2.setTextColor(getResources().getColor(R.color.colorAccent));
                        btext.setTextColor(getResources().getColor(R.color.colorAccent));
                    } else {
                        RTO_Variable.getInstance().wrong_score++;
                        opp2.setBackground(getResources().getDrawable(R.drawable.bg_option_wrong));
                        op2.setTextColor(getResources().getColor(R.color.red));
                        btext.setTextColor(getResources().getColor(R.color.red));
                        mp2.start();
                        vibrator.vibrate(500);
                        if (currentQ.getANSWER().intValue() == 1) {
                            true_icon1.setVisibility(0);
                            opp1.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                            op1.setTextColor(getResources().getColor(R.color.colorAccent));
                            atext.setTextColor(getResources().getColor(R.color.colorAccent));
                        } else {
                            true_icon3.setVisibility(0);
                            opp3.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                            op3.setTextColor(getResources().getColor(R.color.colorAccent));
                            ctext.setTextColor(getResources().getColor(R.color.colorAccent));
                        }
                    }
                    stoptimertask();
                    RTOExamActivity.option2selected = true;
                }
            }
        });
        this.opp3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            public void onClick(View view) {
                if (!RTOExamActivity.option1selected.booleanValue() && !RTOExamActivity.option2selected.booleanValue() && !RTOExamActivity.option3selected.booleanValue()) {
                    mHandler.removeCallbacks(runnable);
                    if (currentQ.getANSWER().intValue() == 3) {
                        RTO_Variable.getInstance().right_score++;
                        mp.start();
                        true_icon3.setVisibility(0);
                        opp3.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                        op3.setTextColor(getResources().getColor(R.color.colorAccent));
                        ctext.setTextColor(getResources().getColor(R.color.colorAccent));
                    } else {
                        RTO_Variable.getInstance().wrong_score++;
                        opp3.setBackground(getResources().getDrawable(R.drawable.bg_option_wrong));
                        op3.setTextColor(getResources().getColor(R.color.red));
                        ctext.setTextColor(getResources().getColor(R.color.red));
                        mp2.start();
                        vibrator.vibrate(500);
                        if (currentQ.getANSWER().intValue() == 1) {
                            true_icon1.setVisibility(0);
                            opp1.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                            op1.setTextColor(getResources().getColor(R.color.colorAccent));
                            atext.setTextColor(getResources().getColor(R.color.colorAccent));
                        } else {
                            true_icon2.setVisibility(0);
                            opp2.setBackground(getResources().getDrawable(R.drawable.bg_option_right));
                            op2.setTextColor(getResources().getColor(R.color.colorAccent));
                            btext.setTextColor(getResources().getColor(R.color.colorAccent));
                        }
                    }
                    stoptimertask();
                    RTOExamActivity.option3selected = true;
                }
            }
        });
    }

    public void startTimer() {
        this.timer = new Timer();
        initializeTimerTask();
        this.timer.schedule(this.timerTask, 1000, 1000);
    }

    public void stoptimertask() {
        Timer timer2 = this.timer;
        if (timer2 != null) {
            timer2.cancel();
            MediaPlayer mediaPlayer = this.mp1;
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                this.mp1.stop();
                this.mp1.reset();
                this.mp1.release();
                this.mp1 = null;
            }
            this.timer = null;
        }
    }

    public void initializeTimerTask() {
        this.timerTask = new TimerTask() {
            /* class anon.rtoinfo.rtovehical.Activities.RTOExamActivity.AnonymousClass8 */

            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if (timertick != 0) {
                            if (mp1 != null) {
                                mp1.start();
                            }
                            RTOExamActivity rTOExamActivity = RTOExamActivity.this;
                            rTOExamActivity.timertick--;
                        } else if (mp1 != null) {
                            mp1.stop();
                            mp1.reset();
                            mp1.release();
                            MediaPlayer unused = mp1 = null;
                        }
                    }
                });
            }
        };
    }

    private void setQuestionView() {
        if (!this.currentQ.getIMAGEPATH().isEmpty()) {
            this.img.setImageResource(getResources().getIdentifier(this.currentQ.getIMAGEPATH(), "drawable", getPackageName()));
            this.img.setVisibility(View.VISIBLE);
            this.img_bg.setVisibility(View.VISIBLE);
        }
        if (RTO_Variable.getInstance().qid == RTO_Variable.getInstance().counter - 1) {
            this.butNext.setVisibility(View.GONE);
            this.butFinish.setVisibility(View.VISIBLE);
        } else {
            this.butFinish.setVisibility(View.GONE);
        }
        if (this.currentQ.getQUESTION().contains("?")) {
            this.txtQuestion.setText(this.currentQ.getQUESTION());
        } else {
            TextView textView = this.txtQuestion;
            textView.setText(this.currentQ.getQUESTION() + "?");
        }
        this.op1.setText(this.currentQ.getOPTA());
        this.op2.setText(this.currentQ.getOPTB());
        this.op3.setText(this.currentQ.getOPTC());
        TextView textView2 = this.Qno;
        RTO_Variable instance = RTO_Variable.getInstance();
        instance.qid = instance.qid + 1;
        textView2.setText(("Que " + String.valueOf(instance.qid ) + ": "));
        Log.e( "setQuestionView: ", String.valueOf(instance.qid +1));
    }

    public void showResult() {
        Intent intent = new Intent(this, RTOResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("right_score", RTO_Variable.getInstance().right_score);
        bundle.putInt("wrong_score", RTO_Variable.getInstance().wrong_score);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mp1 != null) {
            RTOExamActivity rTOExamActivity = RTOExamActivity.this;
            rTOExamActivity.mp1 = MediaPlayer.create(rTOExamActivity, (int) R.raw.timer);
            mp1.stop();
            mp1.reset();
            mp1.release();
            mp1 = null;
        }
        mHandler.removeCallbacks(runnable);
        stoptimertask();
        super.onBackPressed();
    }
}