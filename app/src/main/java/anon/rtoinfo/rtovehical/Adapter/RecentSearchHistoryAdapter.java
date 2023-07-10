package anon.rtoinfo.rtovehical.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import anon.rtoinfo.rtovehical.R;
import anon.rtoinfo.rtovehical.datamodels.SearchVehicleHistory;
import anon.rtoinfo.rtovehical.utils.Utils;
//import com.tradetu.trendingapps.vehicleregistrationdetails.datamodels.SearchVehicleHistory;
//import com.tradetu.trendingapps.vehicleregistrationdetails.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class RecentSearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BANNER_AD_VIEW_TYPE = 2;
    /* access modifiers changed from: private */
    public static final String TAG = "RecentSearchHistoryAdapter";
    private static final int VEHICLE_HISTORY_ITEM_VIEW_TYPE = 1;
    Context context;
    private List<SearchVehicleHistory> historyList = new ArrayList();
    IRecyclerViewClickListener mListener;
    IRecyclerViewLongClickListener mLongClickListener;
    String type;

    public RecentSearchHistoryAdapter(Context context2, String str, IRecyclerViewClickListener iRecyclerViewClickListener, IRecyclerViewLongClickListener iRecyclerViewLongClickListener) {
        this.context = context2;
        this.type = str;
        this.mListener = iRecyclerViewClickListener;
        this.mLongClickListener = iRecyclerViewLongClickListener;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        if (i == 1) {
            return new SearchVehicleHistoryItemHolder(LayoutInflater.from(this.context).inflate(R.layout.row_search_vehicle_history_item, viewGroup, false), this.mListener, this.mLongClickListener);
//        }
//        return new AdViewItemHolder(LayoutInflater.from(this.context).inflate(R.layout.row_banner_ad_view_item, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof SearchVehicleHistoryItemHolder) {
            SearchVehicleHistoryItemHolder searchVehicleHistoryItemHolder = (SearchVehicleHistoryItemHolder) viewHolder;
            SearchVehicleHistory searchVehicleHistory = this.historyList.get(viewHolder.getAdapterPosition());
            searchVehicleHistoryItemHolder.txvRegNo.setText(searchVehicleHistory.getRegistrationNo());
            if (Utils.isNullOrEmpty(searchVehicleHistory.getName())) {
                searchVehicleHistoryItemHolder.txvName.setVisibility(8);
            } else {
                searchVehicleHistoryItemHolder.txvName.setText(searchVehicleHistory.getName());
                searchVehicleHistoryItemHolder.txvName.setVisibility(0);
            }
            searchVehicleHistoryItemHolder.itemView.setTag(Integer.valueOf(viewHolder.getAdapterPosition()));
        }
//        else if (viewHolder instanceof AdViewItemHolder) {
//            final AdViewItemHolder adViewItemHolder = (AdViewItemHolder) viewHolder;
////            final AdView adView = new AdView(this.context);
////            adView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
////            adView.setVisibility(8);
//            String str = this.type;
//            if (str == null || str.equalsIgnoreCase("RC")) {
//                adView.setAdUnitId(Constants.ADMOB_SEARCH_VEHICLE_SCREEN_IN_BETWEEN_BANNER_ID);
//            } else if (this.type.equalsIgnoreCase("INSURANCE")) {
//                adView.setAdUnitId(Constants.ADMOB_SEARCH_VEHICLE_INSURANCE_SCREEN_IN_BETWEEN_BANNER_ID);
//            } else if (this.type.equalsIgnoreCase("FINANCE")) {
//                adView.setAdUnitId(Constants.ADMOB_SEARCH_VEHICLE_FINANCE_SCREEN_IN_BETWEEN_BANNER_ID);
//            } else {
//                adView.setAdUnitId(Constants.ADMOB_SEARCH_VEHICLE_SCREEN_IN_BETWEEN_BANNER_ID);
//            }
//            adView.setAdSize(AdSize.SMART_BANNER);
//            adView.loadAd(new AdRequest.Builder().build());
//            adView.setAdListener(new AdListener() {
//                public void onAdFailedToLoad(LoadAdError loadAdError) {
//                    if (loadAdError != null) {
//                        String access$000 = RecentSearchHistoryAdapter.TAG;
//                        Log.i(access$000, "onAdFailedToLoad errorCode: " + loadAdError.getCode());
//                    }
//                }
//
//                public void onAdLoaded() {
//                    adView.setVisibility(0);
//                    adViewItemHolder.adLayout.setVisibility(0);
//                    adViewItemHolder.layoutAdView.setVisibility(0);
//                }
//            });
//            adViewItemHolder.layoutAdView.addView(adView);
//        }
    }

    public int getItemCount() {
        List<SearchVehicleHistory> list = this.historyList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public int getItemViewType(int i) {
        return (this.historyList.get(i) == null || this.historyList.get(i).getRegistrationNo() != null) ? 1 : 2;
    }

    public void updateListData(List<SearchVehicleHistory> list) {
        if (this.historyList == null) {
            this.historyList = new ArrayList();
        }
        this.historyList = list;
        pushAds();
        notifyDataSetChanged();
    }

    private void pushAds() {
        List<SearchVehicleHistory> list = this.historyList;
        if (list != null && list.size() > 0) {
            int i = 4;
            if (this.historyList.size() > 29) {
                i = 8;
            }
            int i2 = 1;
            int size = (this.historyList.size() / i) + 1;
            int size2 = this.historyList.size();
            while (i2 < size + size2 && this.historyList.size() > i2) {
                if (i2 % i == 0) {
                    this.historyList.add(i2, new SearchVehicleHistory());
                }
                i2++;
            }
        }
    }

    static class SearchVehicleHistoryItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        IRecyclerViewClickListener mListener;
        IRecyclerViewLongClickListener mLongClickListener;
        TextView txvName;
        TextView txvRegNo;

        SearchVehicleHistoryItemHolder(View view, IRecyclerViewClickListener iRecyclerViewClickListener, IRecyclerViewLongClickListener iRecyclerViewLongClickListener) {
            super(view);
            this.txvRegNo = (TextView) view.findViewById(R.id.txvRegNo);
            this.txvName = (TextView) view.findViewById(R.id.txvName);
            this.mListener = iRecyclerViewClickListener;
            this.mLongClickListener = iRecyclerViewLongClickListener;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void onClick(View view) {
            this.mListener.onItemSelected(getAdapterPosition());
        }

        public boolean onLongClick(View view) {
            this.mLongClickListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }

    static class AdViewItemHolder extends RecyclerView.ViewHolder {
        LinearLayout adLayout;
        CardView layoutAdView;

        AdViewItemHolder(View view) {
            super(view);
//            this.adLayout = (LinearLayout) view.findViewById(R.id.adLayout);
//            this.layoutAdView = (CardView) view.findViewById(R.id.ad_card_view);
        }
    }
}
