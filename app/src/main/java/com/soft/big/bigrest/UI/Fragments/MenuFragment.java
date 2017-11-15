package com.soft.big.bigrest.UI.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.soft.big.bigrest.Adapters.MenuAdapter;
import com.soft.big.bigrest.Behaviors.DatabaseAccess;
import com.soft.big.bigrest.Model.DetailsOrder;
import com.soft.big.bigrest.Model.Order;
import com.soft.big.bigrest.Model.Table;
import com.soft.big.bigrest.R;
import com.soft.big.bigrest.Services.TableService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.soft.big.bigrest.Behaviors.Constants.TABLE_ID_EXTRA_MESSAGE;
import static com.soft.big.bigrest.Services.MenuService.fakeImage;

/**
 * Created by Tidjini on 13/11/2017.
 */

//first extends from fragments
public class MenuFragment extends Fragment{

    //tools
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mOrderRecyclerView;
    private MenuAdapter mMenuAdapter;

    private List<DetailsOrder> mDetailsOrder = new ArrayList<DetailsOrder>();
    private FrameLayout mProgressFrameLayout;

    private TextView mTotalPriceTextView;

    //table data
    private int mIdTable;
    private Table mTable;

    private Connection mConnection;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container);
        bindFragment(rootView);

        mMenuAdapter = new MenuAdapter(getActivity(), mDetailsOrder);//click handler, this
        mOrderRecyclerView.setAdapter(mMenuAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //TODO getTableData();
        executeTask();
    }

    private void bindFragment(View container){
        //TODO mProgressFrameLayout = container.findViewById(R.id.fl_order_process);

        mOrderRecyclerView = container.findViewById(R.id.rv_menu);
        mOrderRecyclerView.setHasFixedSize(true);

        mTotalPriceTextView = container.findViewById(R.id.tv_total_price_order);
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            mLinearLayoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            mLinearLayoutManager = new LinearLayoutManager(getContext());
        }

        mOrderRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void getTableData(){
        mConnection = DatabaseAccess.databaseConnection();
        Intent intent = getActivity().getIntent();
        mIdTable = intent.getIntExtra(TABLE_ID_EXTRA_MESSAGE, 0);
        if(mIdTable == 0) return;
        mTable = TableService.getTableFromId(mConnection, mIdTable);
        //TODO getSupportActionBar().setTitle("Table  n° "+mTable.getNumero());

    }

    public void setData(){
        mTotalPriceTextView.setText("0,00 DA");
    }


    private void executeTask()
    {
        MenuFragment.AsyncDetails asyncMenu = new MenuFragment.AsyncDetails();
        asyncMenu.execute();


    }

    class AsyncDetails extends AsyncTask<String, String, List<DetailsOrder>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO mProgressFrameLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<DetailsOrder> doInBackground(String... strings) {
            //TODO Connection connection = DatabaseAccess.databaseConnection();
            //TODO return MenuService.getDetails(connection);

            DetailsOrder detail;
            ArrayList<DetailsOrder> details = new ArrayList<>();
            for(int i = 0;  i<300; i++){
                //TODO detail = new DetailsOrder("name "+ i,  "description du menu "+i, i*1000, fakeImage(i%8), 0  );
                //TODO details.add(detail);

            }

            return details;

        }

        @Override
        protected void onPostExecute(List<DetailsOrder> detailsOrders) {
            super.onPostExecute(detailsOrders);
            //mProgressFrameLayout.setVisibility(View.GONE);

            if(detailsOrders == null) return;
            mDetailsOrder = detailsOrders;
            mMenuAdapter.refreshAdapter(mDetailsOrder);

        }
    }

    //send order
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean createLogicOrder(){
        List<DetailsOrder> detailsOrders = new ArrayList<>();
        for(int i=0; i<mDetailsOrder.size(); i++){
            if(mDetailsOrder.get(i).getTotal() > 0)
                detailsOrders.add(mDetailsOrder.get(i));
        }

        if(detailsOrders.size() <= 0) return false;
        //todo id user
        //TODO Order order = new Order(mIdTable, 1, 0, false, close, "remarquos");
        return true;
        //Connection connection = DatabaseAccess.databaseConnection();
        //return createOrder(connection, null );
    }
}
