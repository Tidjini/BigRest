package com.soft.big.bigrest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.big.bigrest.Behaviors.Constants;
import com.soft.big.bigrest.Behaviors.Utils;
import com.soft.big.bigrest.Model.Order;
import com.soft.big.bigrest.Model.Table;
import com.soft.big.bigrest.R;
import com.soft.big.bigrest.UI.OrderActivity;

import java.util.List;

import static com.soft.big.bigrest.Behaviors.Constants.TABLE_ID_EXTRA_MESSAGE;

/**
 * Created by Tidjini on 10/11/2017.
 */

public class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Table> mTables;

    //Click handler
    private final TablesClickHandler mClickHandler;

    /**
     * click interface handler
     */
    public interface TablesClickHandler {
        void onTableSelected(int idTable);
    }



    public TableAdapter(Context mContext, List<Table> mTables, TablesClickHandler clickHandler) {
        this.mContext = mContext;
        this.mTables = mTables;
        this.mClickHandler = clickHandler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //TODO update this "not really necessary we use one Table type"
        View layoutView;
        switch (viewType){
            case 1:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.table_list_big_item, parent, false);
                return new BigTableViewHolder(layoutView);

            default:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.table_list_item, parent, false);
                return new TableViewHolder(layoutView);
        }




    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        //if(mTables.get(position).getMaxNumber() > 6)
        //    return 1;
        return 0;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //TODO update this "not really necessary we use one Table type"
        switch (holder.getItemViewType()) {
            case 1:
                /*BigTableViewHolder  bigTableViewHolder = (BigTableViewHolder) holder;
                bigTableViewHolder.bind(mTables.get(position).getLibelle()+"",
                        mTables.get(position).getMaxNumber()+" Seates",
                        mTables.get(position).getState()
                );
*/
                break;

            default:
                TableViewHolder  tableViewHolder = (TableViewHolder) holder;
                tableViewHolder.bind(mTables.get(position).getLibelle()+"",

                        mTables.get(position).getEtat()
                );
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTables.size();
    }


    public void refreshAdapter(List<Table> list){
        this.mTables.clear();
        this.mTables.addAll(list);
        this.notifyDataSetChanged();
    }

    /**
     * View holder of tables
     */
    class TableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mTableImageView, mTableStateIconImageView;
        private TextView mTableNumberTextView, mTableCapaciteTextView;

        public TableViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTableImageView = itemView.findViewById(R.id.iv_table_state_item);
            mTableStateIconImageView = itemView.findViewById(R.id.iv_table_state_item_icon);
            mTableNumberTextView = itemView.findViewById(R.id.tv_table_number_item);
            //mTableCapaciteTextView = itemView.findViewById(R.id.tv_capacite_item);
        }


        public void bind(String number, Utils.TableState state){
            //mTableCapaciteTextView.setText(capacite);
            mTableNumberTextView.setText(number);
            int iconStateVisibility = View.VISIBLE, stateIconDraw = R.mipmap.ic_get_app_black_24dp;
            int stateImage = R.drawable.free_table;
            //int stateImage = R.drawable.table_back;
            switch (state){
                case SERVED:
                    //todo reference
                    stateIconDraw = R.mipmap.ic_check_circle_black_24dp;
                    //stateImage = R.drawable.occupie_table;
                    break;
                case OCCUPIE:
                    //todo reference
                    stateIconDraw = R.mipmap.ic_get_app_black_24dp;
                    //stateImage = R.drawable.occupie_table;
                    break;
                default:

                    iconStateVisibility = View.GONE;


            }
            //set Icon visibility
            mTableStateIconImageView.setVisibility(iconStateVisibility);
            mTableStateIconImageView.setImageResource(stateIconDraw);
            //table resource
            mTableImageView.setImageResource(stateImage);

        }

        @Override
        public void onClick(View view) {

            int adapterPosition = getAdapterPosition();
            int idTable = mTables.get(adapterPosition).getId();
            mClickHandler.onTableSelected(idTable);
        }


    }

    class BigTableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mTableImageView, mTableStateIconImageView;
        private TextView mTableNumberTextView, mTableCapaciteTextView;

        public BigTableViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTableImageView = itemView.findViewById(R.id.iv_table_big_state_item);
            mTableStateIconImageView = itemView.findViewById(R.id.iv_table_big_state_item_icon);
           // mTableNumberTextView = itemView.findViewById(R.id.tv_table_big_number_item);
            mTableCapaciteTextView = itemView.findViewById(R.id.tv_big_capacite_item);
        }


        public void bind(String number, String capacite ,Utils.TableState  state){
            mTableCapaciteTextView.setText(capacite);
            mTableNumberTextView.setText(number);
            int iconStateVisibility = View.VISIBLE, stateIconDraw = R.mipmap.ic_notifications_black_24dp;
            int stateImage = R.drawable.free_big_table;
            switch (state){
                case SERVED:
                    //todo reference
                    stateIconDraw = R.mipmap.ic_notifications_none_black_24dp;
                    stateImage = R.drawable.occupie_big_table;
                    break;
                case OCCUPIE:
                    //todo reference
                    stateIconDraw = R.mipmap.ic_notifications_black_24dp;
                    stateImage = R.drawable.occupie_big_table;
                    break;
                default:

                    iconStateVisibility = View.GONE;


            }
            //set Icon visibility
            mTableStateIconImageView.setVisibility(iconStateVisibility);
            mTableStateIconImageView.setImageResource(stateIconDraw);
            //table resource
            mTableImageView.setImageResource(stateImage);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), OrderActivity.class);
            int tableId = mTables.get(getPosition()).getId();
            intent.putExtra(TABLE_ID_EXTRA_MESSAGE, tableId);
            view.getContext().startActivity(intent);
        }
    }









}

