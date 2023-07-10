package anon.rtoinfo.rtovehical.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import anon.rtoinfo.rtovehical.R;
import java.util.ArrayList;
import java.util.Iterator;

public class Searchadapter extends BaseAdapter {
    public static ArrayList<String> language_filterlist;
    private LayoutInflater language_Inflater;
    private ArrayList<String> language_mainlist;

    public long getItemId(int i) {
        return (long) i;
    }

    public Searchadapter(Context context, ArrayList<String> arrayList) {
        this.language_Inflater = LayoutInflater.from(context);
        this.language_mainlist = arrayList;
        language_filterlist = new ArrayList<>();
        for (int i = 0; i < this.language_mainlist.size(); i++) {
            language_filterlist.add(this.language_mainlist.get(i));
        }
    }

    public int getCount() {
        return language_filterlist.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = this.language_Inflater.inflate(R.layout.rtoitem_data, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.historyData = (TextView) view.findViewById(R.id.text1);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.historyData.setText(language_filterlist.get(i));
        return view;
    }

    static class ViewHolder {
        TextView historyData;

        ViewHolder() {
        }
    }

    public void filter(String str) {
        language_filterlist.clear();
        if (str.isEmpty()) {
            language_filterlist.addAll(this.language_mainlist);
        } else {
            String lowerCase = str.toLowerCase();
            Iterator<String> it = this.language_mainlist.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (next.toLowerCase().contains(lowerCase)) {
                    language_filterlist.add(next);
                }
            }
        }
        notifyDataSetChanged();
    }
}
