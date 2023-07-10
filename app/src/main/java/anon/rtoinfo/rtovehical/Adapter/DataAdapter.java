package anon.rtoinfo.rtovehical.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import anon.rtoinfo.rtovehical.R;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder1> {
    public static ArrayList<String> finaldata;
    private Activity activity;
    List<String> data;
    private LayoutInflater inflater;

    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        public LinearLayout child_linear;
        public final TextView txtLabel;

        public MyViewHolder1(View view) {
            super(view);
            this.txtLabel = (TextView) view.findViewById(R.id.txtLabel);
            this.child_linear = (LinearLayout) view.findViewById(R.id.child_linear);
        }
    }

    public DataAdapter(Activity activity2, List<String> list) {
        this.activity = activity2;
        this.inflater = LayoutInflater.from(activity2);
        this.data = list;
        finaldata = new ArrayList<>();
        finaldata.add(this.data.get(0).replace(":", " :"));
        finaldata.add(this.data.get(1).replace(":", " :"));
        finaldata.add(this.data.get(2).replace(":", " :"));
        finaldata.add(this.data.get(3).replace(":", " :"));
        finaldata.add(this.data.get(14).replace(":", " :"));
        finaldata.add(this.data.get(15).replace(":", " :"));
        finaldata.add(this.data.get(4).replace(":", " :"));
        finaldata.add(this.data.get(5).replace(":", " :"));
        finaldata.add(this.data.get(12).replace(":", " :"));
        finaldata.add(this.data.get(13).replace(":", " :"));
        finaldata.add(this.data.get(10).replace(":", " :"));
        finaldata.add(this.data.get(11).replace(":", " :"));
        finaldata.add(this.data.get(22).replace(":", " :"));
        finaldata.add(this.data.get(23).replace(":", " :"));
        finaldata.add(this.data.get(8).replace(":", " :"));
        finaldata.add(this.data.get(9).replace(":", " :"));
        finaldata.add(this.data.get(6).replace(":", " :"));
        finaldata.add(this.data.get(7).replace(":", " :"));
        finaldata.add(this.data.get(16).replace(":", " :"));
        finaldata.add(this.data.get(17).replace(":", " :"));
        finaldata.add(this.data.get(18).replace(":", " :"));
        finaldata.add(this.data.get(19).replace(":", " :"));
        finaldata.add(this.data.get(20).replace(":", " :"));
        finaldata.add(this.data.get(21).replace(":", " :"));
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder1(this.inflater.inflate(R.layout.item_data, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder1 myViewHolder1, int i) {
        if (i == 0) {
            myViewHolder1.txtLabel.setText(this.data.get(0).replace(":", " :"));
        }
        if (i == 1) {
            myViewHolder1.txtLabel.setText(this.data.get(1).replace(":", " :"));
        }
        if (i == 2) {
            myViewHolder1.txtLabel.setText(this.data.get(2).replace(":", " :"));
        }
        if (i == 3) {
            myViewHolder1.txtLabel.setText(this.data.get(3).replace(":", " :"));
        }
        if (i == 4) {
            myViewHolder1.txtLabel.setText(this.data.get(14).replace(":", " :"));
        }
        if (i == 5) {
            myViewHolder1.txtLabel.setText(this.data.get(15).replace(":", " :"));
        }
        if (i == 6) {
            myViewHolder1.txtLabel.setText(this.data.get(4).replace(":", " :"));
        }
        if (i == 7) {
            myViewHolder1.txtLabel.setText(this.data.get(5).replace(":", " :"));
        }
        if (i == 8) {
            myViewHolder1.txtLabel.setText(this.data.get(12).replace(":", " :"));
        }
        if (i == 9) {
            myViewHolder1.txtLabel.setText(this.data.get(13).replace(":", " :"));
        }
        if (i == 10) {
            myViewHolder1.txtLabel.setText(this.data.get(10).replace(":", " :"));
        }
        if (i == 11) {
            myViewHolder1.txtLabel.setText(this.data.get(11).replace(":", " :"));
        }
        if (i == 12) {
            myViewHolder1.txtLabel.setText(this.data.get(22).replace(":", " :"));
        }
        if (i == 13) {
            myViewHolder1.txtLabel.setText(this.data.get(23).replace(":", " :"));
        }
        if (i == 14) {
            myViewHolder1.txtLabel.setText(this.data.get(8).replace(":", " :"));
        }
        if (i == 15) {
            myViewHolder1.txtLabel.setText(this.data.get(9).replace(":", " :"));
        }
        if (i == 16) {
            myViewHolder1.txtLabel.setText(this.data.get(6).replace(":", " :"));
        }
        if (i == 17) {
            myViewHolder1.txtLabel.setText(this.data.get(7).replace(":", " :"));
        }
        if (i == 18) {
            myViewHolder1.txtLabel.setText(this.data.get(16).replace(":", " :"));
        }
        if (i == 19) {
            myViewHolder1.txtLabel.setText(this.data.get(17).replace(":", " :"));
        }
        if (i == 20) {
            myViewHolder1.txtLabel.setText(this.data.get(18).replace(":", " :"));
        }
        if (i == 21) {
            myViewHolder1.txtLabel.setText(this.data.get(19).replace(":", " :"));
        }
        if (i == 22) {
            myViewHolder1.txtLabel.setText(this.data.get(20).replace(":", " :"));
        }
        if (i == 23) {
            myViewHolder1.txtLabel.setText(this.data.get(21).replace(":", " :"));
        }
        if (i % 2 == 0) {
            myViewHolder1.txtLabel.setTextColor(this.activity.getResources().getColor(R.color.black));
            return;
        }
        myViewHolder1.txtLabel.setTextColor(this.activity.getResources().getColor(R.color.gray));
    }

    @Override
    public int getItemCount() {
        return finaldata.size();
    }
}
