package com.soft.big.bigrest.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
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
 * Created by Tidjini on 10/11/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private Context mContext;
    private List<Plat> mPlats;

    //Click handler
    private final MenuAdapter.MenuClickHandler mClickHandler;
    public interface MenuClickHandler{
        /**
         * when plat is selected from menu get ID and manage it in TableActivity
         * @param idPlat
         */
        void onPlatSelected(String idPlat);
    }

    public MenuAdapter(Context context, List<Plat> plats, MenuClickHandler mClickHandler) {
        this.mContext = context;
        this.mPlats = plats;
        this.mClickHandler = mClickHandler;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.menu_row, parent, false);
        return new MenuViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {

        String platPrice = Utils.Formater.getBigDecimalFormat(mPlats.get(position).getPrixProdVente(), 2)+" DA";

        holder.bind(
                platPrice,
                mPlats.get(position).getDésignProf(),
                "", //remarque or description
                mPlats.get(position).getImageProd());
    }

    @Override
    public int getItemCount() {
        return mPlats.size();
    }

    public void refreshAdapter(List<Plat> list){
        this.mPlats.clear();
        this.mPlats.addAll(list);
        this.notifyDataSetChanged();
    }

    public void refreshItemAdapter(int position, Plat plat){
        //this.mDetailsOrder.(position);
        this.mPlats.set(position, plat);
        this.notifyDataSetChanged();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mPriceTextView, mNameTextView, mDescriptionTextView;
        ImageView mPlatImageView;
        public MenuViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mPriceTextView = itemView.findViewById(R.id.tv_price_menu);
            mNameTextView = itemView.findViewById(R.id.tv_plat_name_menu);
            mDescriptionTextView = itemView.findViewById(R.id.tv_plat_desc_menu);
            mPlatImageView =  itemView.findViewById(R.id.iv_plat_menu);
        }
        public void bind(String price, String name, String description, Bitmap image){
            mPriceTextView.setText(price);
            mNameTextView.setText(name);
            mDescriptionTextView.setText(description);
            //set a default plat image
            if(image != null)
                mPlatImageView.setImageBitmap(image);

        }
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String idPlat = mPlats.get(adapterPosition).getIdProd();
            mClickHandler.onPlatSelected(idPlat);

        }
    }
}
