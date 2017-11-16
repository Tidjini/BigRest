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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soft.big.bigrest.Adapters.DetailsOrderAdapter;
import com.soft.big.bigrest.Adapters.MenuAdapter;
import com.soft.big.bigrest.Behaviors.DatabaseAccess;
import com.soft.big.bigrest.Model.DetailsOrder;
import com.soft.big.bigrest.Model.Order;
import com.soft.big.bigrest.Model.Plat;
import com.soft.big.bigrest.Model.Table;
import com.soft.big.bigrest.R;
import com.soft.big.bigrest.Services.DetailsOrderService;
import com.soft.big.bigrest.Services.MenuService;
import com.soft.big.bigrest.Services.OrderService;
import com.soft.big.bigrest.Services.TableService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.soft.big.bigrest.Behaviors.Constants.TABLE_ID_EXTRA_MESSAGE;

/**
 * Created by Tidjini on 13/11/2017.
 */

//first extends from fragments
public class MenuFragment extends Fragment implements MenuAdapter.MenuClickHandler, DetailsOrderAdapter.DetailOrderClickHandler{

    //region Menu Fields
    private LinearLayoutManager mMenuLinearLayoutManager;
    private RecyclerView mMenuRecyclerView;
    private MenuAdapter mMenuAdapter;

    private List<Plat> mPlats = new ArrayList<>();
    private ProgressBar mProgressMenu;
    //endregion

    //region Order Fields
    private LinearLayoutManager mDetailsOrderLinearLayoutManager;
    private RecyclerView mDetailsOrderRecyclerView;
    private DetailsOrderAdapter mDetailsOrderAdapter;

    private List<DetailsOrder> mDetailsOrder = new ArrayList<>();
    private ProgressBar mProgressDetailsOrder;
    private TextView mTotalPriceTextView;

    //endregion

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


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO verify this or put in onStart()
        executeMenuTask();
    }

    private void bindFragment(View container){

        //TODO TabletPhoneUiSwitcher(); for now we use just Tablet view
        //Menu
        //click handler, use this
        mProgressMenu = container.findViewById(R.id.progress_menu);
        mMenuRecyclerView = container.findViewById(R.id.rv_menu);
        mMenuRecyclerView.setHasFixedSize(true);
        mMenuAdapter = new MenuAdapter(getActivity(), mPlats, this);
        mMenuLinearLayoutManager = new GridLayoutManager(getContext(), 2);
        mMenuRecyclerView.setLayoutManager(mMenuLinearLayoutManager);
        mMenuRecyclerView.setAdapter(mMenuAdapter);

        //Details Order
        mTotalPriceTextView = container.findViewById(R.id.tv_total_price_order);
        mProgressDetailsOrder = container.findViewById(R.id.progress_details_order);
        mDetailsOrderRecyclerView = container.findViewById(R.id.rv_order_order_layout);
        mDetailsOrderRecyclerView.setHasFixedSize(true);
        mDetailsOrderAdapter = new DetailsOrderAdapter(getActivity(), mDetailsOrder, this);
        mDetailsOrderLinearLayoutManager = new LinearLayoutManager(getContext());
        mDetailsOrderRecyclerView.setLayoutManager(mDetailsOrderLinearLayoutManager);
        mDetailsOrderRecyclerView.setAdapter(mDetailsOrderAdapter);
    }

    //TODO Switch Tablet and phone
    private void TabletPhoneUiSwitcher(){
//        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
//        if (tabletSize) {
//            mLinearLayoutManager = new GridLayoutManager(getContext(), 2);
//        } else {
//            mLinearLayoutManager = new LinearLayoutManager(getContext());
//        }

    }

    //TODO in phone (Activities)
    private void getTableData(){
        mConnection = DatabaseAccess.databaseConnection();
        Intent intent = getActivity().getIntent();
        mIdTable = intent.getIntExtra(TABLE_ID_EXTRA_MESSAGE, 0);
        if(mIdTable == 0) return;
        mTable = TableService.getTableFromId(mConnection, mIdTable);
        //TODO getSupportActionBar().setTitle("Table  n° "+mTable.getNumero());

    }

    /**
     * Get data from activity (and Tables Fragment)
     */
    public void setData(int idTable){

        MenuFragment.AsyncOrder asyncOrder = new MenuFragment.AsyncOrder();
        asyncOrder.execute(idTable);
    }


    private void executeMenuTask()
    {
        MenuFragment.AsyncMenu asyncMenu = new MenuFragment.AsyncMenu();
        asyncMenu.execute();
    }

    @Override
    public void onPlatSelected(int idPlat) {

    }

    @Override
    public void onDetailOrderSelected(int idDetailOrder) {

    }

    /**
     * Get Menu Data
     */
    class AsyncMenu extends AsyncTask<String, String, List<Plat>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressMenu.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Plat> doInBackground(String... strings) {
            Connection connection = DatabaseAccess.databaseConnection();
            return MenuService.getPlats(connection);
        }

        @Override
        protected void onPostExecute(List<Plat> plats) {
            super.onPostExecute(plats);
            mProgressMenu.setVisibility(View.GONE);
            if(plats == null) return;
            mPlats = plats;
            mMenuAdapter.refreshAdapter(mPlats);
        }
    }

    /**
     * Get Just if Order is open (no close)
     * so the Table is still occupied
     */
    class AsyncOrder extends AsyncTask<Integer, String, List<DetailsOrder>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDetailsOrder.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<DetailsOrder> doInBackground(Integer... integers) {
            if(integers == null || integers.length <= 0) return null;
            int idTable = integers[0];
            Connection connection = DatabaseAccess.databaseConnection();
            Order openOrder = OrderService.getTableOpenOrderById(connection, idTable);
            return DetailsOrderService.getDetailsOrderByOrderId(connection, openOrder.getId());
        }

        @Override
        protected void onPostExecute(List<DetailsOrder> detailsOrders) {
            super.onPostExecute(detailsOrders);
            mProgressDetailsOrder.setVisibility(View.GONE);
            if(detailsOrders == null) return;

            mDetailsOrder = detailsOrders;
            mDetailsOrderAdapter.refreshAdapter(mDetailsOrder);
            //TODO Sum price
            mTotalPriceTextView.setText("0,00 DA");

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
