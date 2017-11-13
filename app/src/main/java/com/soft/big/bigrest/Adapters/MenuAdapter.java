package com.soft.big.bigrest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soft.big.bigrest.Model.DetailsOrder;
import com.soft.big.bigrest.R;

import java.util.List;



/**
 * Created by Tidjini on 10/11/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private Context mContext;
    private List<DetailsOrder> mDetailsOrder;

    public MenuAdapter(Context context, List<DetailsOrder> detailsOrder) {
        this.mContext = context;
        this.mDetailsOrder = detailsOrder;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.menu_row, parent, false);
        return new MenuViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {

        holder.bind(mDetailsOrder.get(position).getPrice()+" DA",
                mDetailsOrder.get(position).getPlatName(),
                mDetailsOrder.get(position).getPlatDescription(),
                "Total: "+mDetailsOrder.get(position).getTotal(),
                "Price: "+mDetailsOrder.get(position).getTotalHt(),
                mDetailsOrder.get(position).getImageId());
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

    public void refreshItemAdapter(int position, DetailsOrder detailsOrder){
        //this.mDetailsOrder.(position);
        this.mDetailsOrder.set(position, detailsOrder);
        this.notifyDataSetChanged();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mPriceTextView, mNameTextView, mDescriptionTextView, mTotalTextView, mTotalPriceTextView;
        ImageView mPlatImageView;
        public MenuViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mPriceTextView = itemView.findViewById(R.id.tv_price_menu);
            mNameTextView = itemView.findViewById(R.id.tv_plat_name_menu);
            mDescriptionTextView = itemView.findViewById(R.id.tv_plat_desc_menu);
            mTotalTextView = itemView.findViewById(R.id.tv_total_menu);
            mTotalPriceTextView =  itemView.findViewById(R.id.tv_total_price_menu);
            mPlatImageView =  itemView.findViewById(R.id.iv_plat_menu);
        }
        public void bind(String price, String name, String description, String total, String totalPrice, int idImage){
            mPriceTextView.setText(price);
            mNameTextView.setText(name);
            mDescriptionTextView.setText(description);
            mTotalTextView.setText(total);
            mTotalPriceTextView.setText(totalPrice);
            mPlatImageView.setImageResource(idImage);

        }


        public void onClick(View view) {
            DetailsOrder detailsOrder = mDetailsOrder.get(getPosition());
            int total = detailsOrder.getTotal() + 1;
            detailsOrder.setTotal(total);
            refreshItemAdapter(getPosition(), detailsOrder);


        }


    }
}
