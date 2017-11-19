package com.soft.big.bigrest.UI.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import static com.soft.big.bigrest.Services.DetailsOrderService.createDetailsOrder;
import static com.soft.big.bigrest.Services.DetailsOrderService.updateDetailsOrder;
import static com.soft.big.bigrest.Services.OrderService.createOrder;
import static com.soft.big.bigrest.Services.OrderService.updateOrderState;

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
    private List<DetailsOrder> mDetailsOrderTemp = new ArrayList<>();
    private ProgressBar mProgressDetailsOrder;
    private TextView mTotalPriceTextView;

    //endregion

    //table data
    private int mIdTable;
    private Table mTable;

    private Connection mConnection;

    private int mIdUser;
    private boolean isTableFree;

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

    public void setData(int idTable, int idUser){
        mIdTable = idTable;

        mIdUser = idUser;
        //Init to default value when change table (no save state gone)
        mDetailsOrderTemp = new ArrayList<>();
        mDetailsOrderAdapter.refreshAdapter(mDetailsOrder);
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

        //if plat exist in list of order perform update operation
        boolean update = false;
        for (int j = 0; j < mDetailsOrder.size(); j++){
            if(mDetailsOrder.get(j).getPlatId() == idPlat){
                int total = mDetailsOrder.get(j).getTotal() + 1;
                DetailsOrder detailOrder =  mDetailsOrder.get(j);
                detailOrder.setTotal(total);
                mDetailsOrderAdapter.refreshItemAdapter(j, detailOrder);
                update = true;


            }
        }
        if(!update) {
            //get Plat object
            int platPosition = 0;
            for (int i = 0; i < mPlats.size(); i++ ){
                if(mPlats.get(i).getId() == idPlat )
                    platPosition = i;
            }

            DetailsOrder detailsOrder = new DetailsOrder(
                    0,
                    idPlat,
                    mPlats.get(platPosition).getName(),
                    1,
                    mPlats.get(platPosition).getPrice()
            );
            mDetailsOrderTemp.add(detailsOrder);
            //mDetailsOrder.add(detailsOrder);
            mDetailsOrderAdapter.refreshAdapter(mDetailsOrderTemp);
        }

        //update total price
        double totalPrice = 0;
        for(int i = 0; i<mDetailsOrderTemp.size(); i++)
            totalPrice += mDetailsOrderTemp.get(i).getTotalHt();
        mTotalPriceTextView.setText(totalPrice +" DA");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onSaveOrder(){
        Connection connection = DatabaseAccess.databaseConnection();
        if(mOrder == null) {
            //create order + order details
            int idOrder = onCreateOrder(connection);
            for(int i = 0; i < mDetailsOrder.size(); i++)
                onCreateDetailsOrder(connection, idOrder, mDetailsOrder.get(i));
        }else {
            //update details order + add details created (id == 0)
            for(int i = 0; i < mDetailsOrder.size(); i++){
                if (mDetailsOrder.get(i).getId() == 0){
                    onCreateDetailsOrder(connection, mOrder.getId(), mDetailsOrder.get(i));
                }
                else {
                    onUpdateDetailsOrder(connection, mDetailsOrder.get(i));
                }
            }
        }

        //refresh details order panel
        MenuFragment.AsyncOrder asyncOrder = new MenuFragment.AsyncOrder();
        asyncOrder.execute(mIdTable);

    }


    public void onServeOrder(){
        Connection connection = DatabaseAccess.databaseConnection();
        if(mOrder == null) return;
        //update order served to true
        mOrder.setEffected(true);
        updateOrderState(connection, mOrder);
        //refresh details order panel
        MenuFragment.AsyncOrder asyncOrder = new MenuFragment.AsyncOrder();
        asyncOrder.execute(mIdTable);
    }

    public void onCloseOrder(){
        Connection connection = DatabaseAccess.databaseConnection();
        if(mOrder == null) return;
        //update order close to true
        mOrder.setClose(true);
        updateOrderState(connection, mOrder);
        //refresh details order panel
        MenuFragment.AsyncOrder asyncOrder = new MenuFragment.AsyncOrder();
        asyncOrder.execute(mIdTable);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private int onCreateOrder(Connection connection){
        Order order = new Order(0, mIdTable, mIdUser, 0, false, false, "remarque");
        return createOrder(connection, order);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private int onCreateDetailsOrder(Connection connection, int orderId, DetailsOrder detailsOrder){
        detailsOrder.setCmdId(orderId);
        return createDetailsOrder(connection, detailsOrder);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private DetailsOrder onUpdateDetailsOrder(Connection connection, DetailsOrder detailsOrder){
        return updateDetailsOrder(connection, detailsOrder);
    }



    private boolean delete = false, modification = false;
    private int detailOrderNumber = 1;
    private int detailIdToDelete;
    private Dialog editDialog;
    @Override
    public void onDetailOrderSelected(int idDetailOrder) {

        DetailsOrder detailOrder = null;
        //get detail info
        for(int i=0; i<mDetailsOrderTemp.size(); i++){
            if(mDetailsOrder.get(i).getId() == idDetailOrder)
                detailOrder = mDetailsOrder.get(i);
        }
        if(detailOrder == null) return;

        // custom dialog
        editDialog = new Dialog(getContext());
        editDialog.setContentView(R.layout.row_dialog);
        editDialog.setTitle("Edit detail order ID:" + idDetailOrder);
        // set the custom dialog components - text, image and button
        TextView articleName = editDialog.findViewById(R.id.tv_article_name_dialog);
        articleName.setText(detailOrder.getPlatName());
        final TextView counter = editDialog.findViewById(R.id.tv_article_number_dialog);
        counter.setText(detailOrder.getTotal()+"");

        Button minusBtn = editDialog.findViewById(R.id.btn_minus_counter);
        Button plusBtn = editDialog.findViewById(R.id.btn_plus_counter);
        Button deleteBtn = editDialog.findViewById(R.id.btn_delete_dialog);
        Button okBtn = editDialog.findViewById(R.id.btn_confirmation_dialog);
        Button cancelBtn = editDialog.findViewById(R.id.btn_cancel_dialog);

        //init
        detailOrderNumber = detailOrder.getTotal();
        detailIdToDelete = idDetailOrder;
        modification = false;
        delete = false;


        // if button is clicked, close the custom dialog
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailOrderNumber--;
                if(detailOrderNumber < 1)
                    detailOrderNumber = 1;
                modification = true;
                counter.setText(detailOrderNumber+"");

            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailOrderNumber++;
                modification = true;
                counter.setText(detailOrderNumber+"");

            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete = true;
            }
        });

        final DetailsOrder finalDetailOrder = detailOrder;
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delete){
                    //delete with async task
                    MenuFragment.AsyncDelete asyncDelete= new MenuFragment.AsyncDelete();
                    asyncDelete.execute(finalDetailOrder);
                }
                //TODO update
                if(modification){
                    MenuFragment.AsyncModify asyncModify = new MenuFragment.AsyncModify();
                    asyncModify.execute(finalDetailOrder);
                }

                editDialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });

        editDialog.show();

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

    Order mOrder;
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
            mOrder = OrderService.getTableOpenOrderById(connection, idTable);
            if(mOrder == null) {
                isTableFree = true;
                return null;
            }
            return DetailsOrderService.getDetailsOrderByOrderId(connection, mOrder.getId());
        }

        @Override
        protected void onPostExecute(List<DetailsOrder> detailsOrders) {
            super.onPostExecute(detailsOrders);
            mProgressDetailsOrder.setVisibility(View.GONE);
            if(detailsOrders != null) {
                mDetailsOrderTemp = detailsOrders;
                mDetailsOrderAdapter.refreshAdapter(mDetailsOrderTemp);
            }
            //Sum price
            double totalPrice = 0;
            for(int i = 0; i<mDetailsOrderTemp.size(); i++)
                totalPrice += mDetailsOrderTemp.get(i).getTotalHt();
            mTotalPriceTextView.setText(totalPrice +" DA");

        }
    }

    class AsyncDelete extends AsyncTask<DetailsOrder, String, DetailsOrder>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDetailsOrder.setVisibility(View.VISIBLE);
        }

        @Override
        protected DetailsOrder doInBackground(DetailsOrder... details) {
            if(details == null || details.length <= 0) return null;
            DetailsOrder detail = details[0];
            Connection connection = DatabaseAccess.databaseConnection();
            return DetailsOrderService.deleteDetailsOrder(connection, detail);

        }

        @Override
        protected void onPostExecute(DetailsOrder detailsOrdersDeleted) {
            super.onPostExecute(detailsOrdersDeleted);
            mProgressDetailsOrder.setVisibility(View.GONE);
            if(detailsOrdersDeleted != null) {
                for (int i = 0; i<mDetailsOrderTemp.size(); i++)
                    if(mDetailsOrderTemp.get(i).getId() == detailIdToDelete)
                        mDetailsOrderTemp.remove(detailsOrdersDeleted);

                mDetailsOrderAdapter.refreshAdapter(mDetailsOrderTemp);
            }
            //Sum price
            double totalPrice = 0;
            for(int i = 0; i<mDetailsOrderTemp.size(); i++)
                totalPrice += mDetailsOrderTemp.get(i).getTotalHt();
            mTotalPriceTextView.setText(totalPrice +" DA");

            editDialog.dismiss();
        }
    }

    class AsyncModify extends AsyncTask<DetailsOrder, String, DetailsOrder>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDetailsOrder.setVisibility(View.VISIBLE);
        }

        @Override
        protected DetailsOrder doInBackground(DetailsOrder... details) {
            //todo custom this
            if(details == null || details.length <= 0) return null;
            DetailsOrder detail = details[0];
            //Connection connection = DatabaseAccess.databaseConnection();
            detail.setTotal(detailOrderNumber);
            return detail;

        }

        @Override
        protected void onPostExecute(DetailsOrder detailsOrdersUpdated) {
            super.onPostExecute(detailsOrdersUpdated);
            mProgressDetailsOrder.setVisibility(View.GONE);
            if(detailsOrdersUpdated != null) {
                for (int i = 0; i<mDetailsOrderTemp.size(); i++)
                    if(mDetailsOrderTemp.get(i).getId() == detailIdToDelete){
                        mDetailsOrderTemp.get(i).setTotal(detailOrderNumber);
                    }

                mDetailsOrderAdapter.refreshAdapter(mDetailsOrderTemp);
            }
            //Sum price
            double totalPrice = 0;
            for(int i = 0; i<mDetailsOrderTemp.size(); i++)
                totalPrice += mDetailsOrderTemp.get(i).getTotalHt();
            mTotalPriceTextView.setText(totalPrice +" DA");

            editDialog.dismiss();
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
