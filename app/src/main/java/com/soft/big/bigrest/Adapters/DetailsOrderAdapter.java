package com.soft.big.bigrest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soft.big.bigrest.Behaviors.Utils;
import com.soft.big.bigrest.Model.DetailsOrder;
import com.soft.big.bigrest.Model.Plat;
import com.soft.big.bigrest.R;

import java.util.List;

/**
 * Created by Tidjini on 16/11/2017.
 */

public class DetailsOrderAdapter extends RecyclerView.Adapter<DetailsOrderAdapter.DetailOrderViewHolder>{
    private Context mContext;
    private List<DetailsOrder> mDetailsOrder;

    //Click handler
    private final DetailsOrderAdapter.DetailOrderClickHandler mClickHandler;
    public interface DetailOrderClickHandler{
        /**
         * when Detail order is selected from Details Grid get ID and manage it in TableActivity
         * display dialog
         * @param idDetailOrder
         */
        void onDetailOrderSelected(int idDetailOrder);
    }

    public DetailsOrderAdapter(Context context, List<DetailsOrder> detailsOrder, DetailsOrderAdapter.DetailOrderClickHandler clickHandler) {
        this.mContext = context;
        this.mDetailsOrder = detailsOrder;
        this.mClickHandler = clickHandler;
    }

    class DetailOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mArticleNameTextView, mArticlePriceTextView, mTotalTextView, mTotalHtTextView;
        public DetailOrderViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mArticleNameTextView = itemView.findViewById(R.id.tv_article_name_order_row);
            mArticlePriceTextView = itemView.findViewById(R.id.tv_price_order_row);
            mTotalTextView = itemView.findViewById(R.id.tv_number_order_row);
            mTotalHtTextView =  itemView.findViewById(R.id.tv_total_order_row);
        }
        public void bind(String name, String price, String number, String totalHt){
            mArticleNameTextView.setText(name);
            mArticlePriceTextView.setText(price);
            mTotalTextView.setText(number);
            mTotalHtTextView.setText(totalHt);

        }
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            //TODO Check if the Entity is already created or not
            int idDetailsOrder = mDetailsOrder.get(adapterPosition).getNbrLigne();
            mClickHandler.onDetailOrderSelected(idDetailsOrder);

        }
    }

    @Override
    public DetailsOrderAdapter.DetailOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.order_row, parent, false);
        return new DetailsOrderAdapter.DetailOrderViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(DetailsOrderAdapter.DetailOrderViewHolder holder, int position) {
        //TODO format price
        String platPrice = Utils.Formater.getBigDecimalFormat(mDetailsOrder.get(position).getPrixProd(), 2) + " DA";
        String platQte = Utils.Formater.getBigDecimalFormat(mDetailsOrder.get(position).getQttProd(), 0);
        String platTotalPrice = Utils.Formater.getBigDecimalFormat(mDetailsOrder.get(position).getMtnetArt(), 2) + " DA";

        holder.bind(mDetailsOrder.get(position).getLibeProd(),
                platPrice,
                platQte,
                platTotalPrice);
    }

    @Override
    public int getItemCount() {
        return mDetailsOrder.size();
    }

    public void refreshAdapter(List<DetailsOrder> list){
        this.mDetailsOrder.clear();
        this.mDetailsOrder.addAll(list);
        this.notifyDataSetChanged();
    }

    public void refreshItemAdapter(int position, DetailsOrder detailOrder){
        //this.mDetailsOrder.(position);
        this.mDetailsOrder.set(position, detailOrder);
        this.notifyDataSetChanged();
    }

}
