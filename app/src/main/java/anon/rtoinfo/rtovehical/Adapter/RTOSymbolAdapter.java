package anon.rtoinfo.rtovehical.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import anon.rtoinfo.rtovehical.R;

public class RTOSymbolAdapter extends RecyclerView.Adapter<RTOSymbolAdapter.data_Holder> {
    private int[] imageList;
    private String[] nameList;

    public RTOSymbolAdapter(String[] strArr, int[] iArr) {
        this.nameList = strArr;
        this.imageList = iArr;
    }

     @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public data_Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new data_Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rto_symbol_list_row, viewGroup, false));
    }

    public void onBindViewHolder(data_Holder data_holder, int i) {
        data_holder.text_list.setText(this.nameList[i]);
        data_holder.img_list.setImageResource(this.imageList[i]);
    }

    class data_Holder extends RecyclerView.ViewHolder {
        ImageView img_list;
        TextView text_list;

        data_Holder(View view) {
            super(view);
            this.text_list = (TextView) view.findViewById(R.id.text_list);
            this.img_list = (ImageView) view.findViewById(R.id.img_list);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.nameList.length;
    }
}
