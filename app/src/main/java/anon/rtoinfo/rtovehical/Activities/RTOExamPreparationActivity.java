package anon.rtoinfo.rtovehical.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import anon.rtoinfo.rtovehical.Examdata.RTOExamData;
import anon.rtoinfo.rtovehical.R;
import anon.rtoinfo.rtovehical.RTOExamDatabase.RTO_Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RTOExamPreparationActivity extends AppCompatActivity {
    ArrayList<String> answer_list = new ArrayList<>();
    ImageView back_btn;
    ImageView symbole_logo;
    ArrayList<String> img_list = new ArrayList<>();
    RTO_Question myapp_currentQ;
    List<RTO_Question> myapp_quesList;
    ArrayList<String> option_a_list = new ArrayList<>();
    ArrayList<String> option_b_list = new ArrayList<>();
    ArrayList<String> option_c_list = new ArrayList<>();
    ArrayList<String> questionlist = new ArrayList<>();
    RecyclerView questionlists_view;
    RTO_Exam_Adapter rto_exam_adapter;
    TextView tv_title;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_rtoexam_preparation);

        FrameLayout adMobView = (FrameLayout) findViewById(R.id.adMobView);
//        showFBBanner(adMobView);
        FrameLayout adMobView1 = (FrameLayout) findViewById(R.id.adMobView1);
//        showBanner(adMobView1);

        this.back_btn = (ImageView) findViewById(R.id.back_btn);
        this.symbole_logo = (ImageView) findViewById(R.id.symbole_logo);
        this.back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RTOExamPreparationActivity.this.onBackPressed();
            }
        });
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        if (!ExamPreparationOptionsActivity.sign_or_not.booleanValue()) {
            this.tv_title.setText("Driving Questions");
            symbole_logo.setImageResource(R.drawable.driving);
        } else {
            this.tv_title.setText("Symbol Questions");
            symbole_logo.setImageResource(R.drawable.symbole);
        }
        int i = 0;
        this.myapp_quesList = new RTOExamData(this).getAllQuestions(Integer.valueOf(getSharedPreferences("settings", 0).getInt("langId", 1)));
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (!ExamPreparationOptionsActivity.sign_or_not.booleanValue()) {
            arrayList.addAll(this.myapp_quesList.subList(0, 126));
            Collections.shuffle(arrayList);
            while (i < arrayList.size()) {
                this.myapp_currentQ = (RTO_Question) arrayList.get(i);
                this.questionlist.add(this.myapp_currentQ.getQUESTION());
                this.option_a_list.add(this.myapp_currentQ.getOPTA());
                this.option_b_list.add(this.myapp_currentQ.getOPTB());
                this.option_c_list.add(this.myapp_currentQ.getOPTC());
                this.answer_list.add(this.myapp_currentQ.getANSWER().toString());
                if (!this.myapp_currentQ.getIMAGEPATH().isEmpty()) {
                    this.img_list.add(this.myapp_currentQ.getIMAGEPATH());
                } else {
                    this.img_list.add("a");
                }
                i++;
            }
        } else {
            List<RTO_Question> list = this.myapp_quesList;
            arrayList2.addAll(list.subList(126, list.size()));
            Collections.shuffle(arrayList2);
            while (i < arrayList2.size()) {
                this.myapp_currentQ = (RTO_Question) arrayList2.get(i);
                this.questionlist.add(this.myapp_currentQ.getQUESTION());
                this.option_a_list.add(this.myapp_currentQ.getOPTA());
                this.option_b_list.add(this.myapp_currentQ.getOPTB());
                this.option_c_list.add(this.myapp_currentQ.getOPTC());
                this.answer_list.add(this.myapp_currentQ.getANSWER().toString());
                if (!this.myapp_currentQ.getIMAGEPATH().isEmpty()) {
                    this.img_list.add(this.myapp_currentQ.getIMAGEPATH());
                } else {
                    this.img_list.add("a");
                }
                i++;
            }
        }
        this.questionlists_view = (RecyclerView) findViewById(R.id.recycler_view);
        this.questionlists_view.setHasFixedSize(true);
        this.questionlists_view.setLayoutManager(new LinearLayoutManager(this));
        this.rto_exam_adapter = new RTO_Exam_Adapter(this, this.questionlist, this.option_a_list, this.option_b_list, this.option_c_list, this.answer_list);
        this.questionlists_view.setAdapter(this.rto_exam_adapter);
    }

    public class RTO_Exam_Adapter extends RecyclerView.Adapter<RTO_Exam_Adapter.data_Holder> {
        ArrayList<String> manswer_list;
        Context mcontext;
        ArrayList<String> moption_a_list;
        ArrayList<String> moption_b_list;
        ArrayList<String> moption_c_list;
        ArrayList<String> mquestionlist;

        RTO_Exam_Adapter(Context context, ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5) {
            this.mcontext = context;
            this.mquestionlist = arrayList;
            this.moption_a_list = arrayList2;
            this.moption_b_list = arrayList3;
            this.moption_c_list = arrayList4;
            this.manswer_list = arrayList5;
        }

        @Override
        public data_Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new data_Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rto_exam_data, viewGroup, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(data_Holder data_holder, int i) {
            data_holder.question_txt.setText(this.mquestionlist.get(i));
            if (Integer.parseInt(this.manswer_list.get(i)) == 1) {
                data_holder.op3_txt.setText(this.moption_a_list.get(i));
            } else if (Integer.parseInt(this.manswer_list.get(i)) == 2) {
                data_holder.op3_txt.setText(this.moption_b_list.get(i));
            } else if (this.moption_c_list.get(i).contains("બંને") || this.moption_c_list.get(i).contains("both") || this.moption_c_list.get(i).contains("दोनों")) {
                TextView textView = data_holder.op3_txt;
                textView.setText("(A) " + this.moption_a_list.get(i) + " , (B) " + this.moption_b_list.get(i));
            } else {
                data_holder.op3_txt.setText(this.moption_c_list.get(i));
            }
            if (!img_list.get(i).equals("a")) {
                data_holder.img_rto.setImageResource(getResources().getIdentifier(img_list.get(i), "drawable",getPackageName()));
                data_holder.img_rto.setVisibility(0);
                TextView textView2 = data_holder.question_txt_no;
                textView2.setText("Question. " + (i + 1));
                return;
            }
            data_holder.img_rto.setVisibility(8);
            TextView textView3 = data_holder.question_txt_no;
            textView3.setText("Question. " + (i + 1));
        }

        class data_Holder extends RecyclerView.ViewHolder {
            ImageView img_rto;
            TextView op3_txt;
            TextView question_txt;
            TextView question_txt_no;

            data_Holder(View view) {
                super(view);
                this.question_txt = (TextView) view.findViewById(R.id.question_txt);
                this.op3_txt = (TextView) view.findViewById(R.id.op3_txt);
                this.img_rto = (ImageView) view.findViewById(R.id.img_rto);
                this.question_txt_no = (TextView) view.findViewById(R.id.question_txt_no);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mquestionlist.size();
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        ExamPreparationOptionsActivity.sign_or_not = false;
        finish();
    }
}
