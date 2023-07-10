package anon.rtoinfo.rtovehical.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import anon.rtoinfo.rtovehical.Activities.FuelActivity;
import anon.rtoinfo.rtovehical.R;

import java.util.ArrayList;
import java.util.Iterator;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.data_Holder> {
    public static ArrayList<String> city_filterlist;
    public static ArrayList<String> url_filterlist;
    private ArrayList<String> cityList;
    public Context context;
    private ArrayList<String> urlList;

    public CityListAdapter(Context context2, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        this.context = context2;
        this.cityList = arrayList;
        this.urlList = arrayList2;
        city_filterlist = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            city_filterlist.add(arrayList.get(i));
        }
        url_filterlist = new ArrayList<>();
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            url_filterlist.add(arrayList2.get(i2));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public data_Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new data_Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.city_list_row, viewGroup, false));
    }

    public void onBindViewHolder(data_Holder data_holder, int i) {
        TextView textView = data_holder.mTextView;
        textView.setText(city_filterlist.get(i));
        textView.setOnClickListener(new cityonclick(i));
    }

    class cityonclick implements View.OnClickListener {
        final int position;

        cityonclick(int i) {
            this.position = i;
        }

        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            Intent intent = new Intent(CityListAdapter.this.context, FuelActivity.class);
            intent.putExtra("city_name", CityListAdapter.city_filterlist.get(this.position));
            intent.putExtra("fuel_url", CityListAdapter.url_filterlist.get(this.position));
            intent.addFlags(335544320);
            CityListAdapter.this.context.startActivity(intent);
        }
    }

    public static class data_Holder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public data_Holder(View view) {
            super(view);
            this.mTextView = (TextView) view.findViewById(R.id.petrol_price_tv_home);
        }
    }

    public void filter(String str) {
        city_filterlist.clear();
        url_filterlist.clear();
        if (str.isEmpty()) {
            city_filterlist.addAll(this.cityList);
            url_filterlist.addAll(this.urlList);
        } else {
            String lowerCase = str.toLowerCase();
            Iterator<String> it = this.cityList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (next.toLowerCase().contains(lowerCase)) {
                    city_filterlist.add(next);
                }
            }
            Iterator<String> it2 = this.urlList.iterator();
            while (it2.hasNext()) {
                String next2 = it2.next();
                if (next2.toLowerCase().contains(lowerCase)) {
                    url_filterlist.add(next2);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return city_filterlist.size();
    }
}
