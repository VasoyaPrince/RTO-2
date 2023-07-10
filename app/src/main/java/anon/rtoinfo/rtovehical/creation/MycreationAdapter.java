package anon.rtoinfo.rtovehical.creation;

import android.content.Context;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import anon.rtoinfo.rtovehical.R;

public class MycreationAdapter extends RecyclerView.Adapter<MycreationAdapter.ViewHolder> {

    OnrvgalleyItemClick objOnrvItemClick;
    Context context;
    ArrayList<String> IMAGEALLARY;
    SparseBooleanArray mSparseBooleanArray;
    ArrayList<String> imagegallary = new ArrayList<String>();
    private static long MiB = 1024 * 1024;
    private static long KiB = 1024;
    private static DecimalFormat format = new DecimalFormat("#.##");

    public MycreationAdapter(Context context, OnrvgalleyItemClick objOnrvItemClick, ArrayList<String> IMAGEALLARY) {
        this.context = context;
        this.objOnrvItemClick = objOnrvItemClick;
        this.IMAGEALLARY = IMAGEALLARY;
        mSparseBooleanArray = new SparseBooleanArray(imagegallary.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        int i2 = context.getResources().getDisplayMetrics().widthPixels;
        holder.iv_creationimage.setImageURI(Uri.parse(this.IMAGEALLARY.get(position)));

        holder.iv_creationimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objOnrvItemClick.OnGalleyImageItemClick(position);
            }
        });
        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objOnrvItemClick.OnGalleyShareItemClick(position);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objOnrvItemClick.OnGalleyDeleteItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.IMAGEALLARY.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_creationimage;
        TextView imgShare, imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_creationimage = (ImageView) itemView.findViewById(R.id.ivImageThumb);
            imgShare = (TextView) itemView.findViewById(R.id.share_btn);
            imgDelete = (TextView) itemView.findViewById(R.id.delete_btn);
        }
    }

    public interface OnrvgalleyItemClick {
        void OnGalleyImageItemClick(int position);

        void OnGalleyShareItemClick(int position);

        void OnGalleyDeleteItemClick(int position);
    }
}