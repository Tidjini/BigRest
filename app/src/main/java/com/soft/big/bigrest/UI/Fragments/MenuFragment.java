package com.soft.big.bigrest.UI.Fragments;

import java.util.Date;
import android.app.Dialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.soft.big.bigrest.Adapters.DetailsOrderAdapter;
import com.soft.big.bigrest.Adapters.MenuAdapter;
import com.soft.big.bigrest.Behaviors.DatabaseAccess;
import com.soft.big.bigrest.Behaviors.Utils;
import com.soft.big.bigrest.Model.Category;
import com.soft.big.bigrest.Model.DetailsOrder;
import com.soft.big.bigrest.Model.Order;
import com.soft.big.bigrest.Model.Plat;
import com.soft.big.bigrest.Model.Table;
import com.soft.big.bigrest.R;
import com.soft.big.bigrest.Services.DetailsOrderService;
import com.soft.big.bigrest.Services.FamillyService;
import com.soft.big.bigrest.Services.MenuService;
import com.soft.big.bigrest.Services.OrderService;
import com.soft.big.bigrest.Services.TableService;
import com.soft.big.bigrest.UI.TablesActivity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.soft.big.bigrest.Behaviors.Constants.TABLE_ID_EXTRA_MESSAGE;
import static com.soft.big.bigrest.Behaviors.ImageUtils.unbindDrawables;
import static com.soft.big.bigrest.Services.DetailsOrderService.createDetailsOrder;
import static com.soft.big.bigrest.Services.DetailsOrderService.updateDetailsOrder;
import static com.soft.big.bigrest.Services.OrderService.createOrder;
import static com.soft.big.bigrest.Services.OrderService.getLastOrder;
import static com.soft.big.bigrest.Services.OrderService.updateOrderState;
import static com.soft.big.bigrest.Services.TableService.getTableFromId;
import static com.soft.big.bigrest.Services.TableService.updateTableStatue;

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
    private Table mTable;
    private Connection mConnection;
    private String mUsername;
    //private boolean isTableFree;


    //spinner
    private Spinner mSpinner;
    private ArrayList<String> mCategories = new ArrayList<>();
    private ArrayList<Integer> mCategoriesId;
    //family adapter
    ArrayAdapter<String> dataAdapter;
    Filter mFamilleFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View mRootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container);

        bindFragment(rootView);
        mRootView = rootView;
        executeMenuTask();
        return rootView;
    }

    //Clear resources
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(mRootView);
    }

    //region bind ui
    private void bindFragment(View container){
        //Menu
        //click handler, use this
        mProgressMenu = container.findViewById(R.id.progress_menu);
        mMenuRecyclerView = container.findViewById(R.id.rv_menu);
        mMenuRecyclerView.setHasFixedSize(true);
        mMenuAdapter = new MenuAdapter(getActivity(), mPlats, this);
        mMenuLinearLayoutManager = new GridLayoutManager(getContext(), 2);
        mMenuRecyclerView.setLayoutManager(mMenuLinearLayoutManager);
        mMenuRecyclerView.setAdapter(mMenuAdapter);


        mSpinner = container.findViewById(R.id.familly_chooser);


        //Details Order
        mTotalPriceTextView = container.findViewById(R.id.tv_total_price_order);
        mProgressDetailsOrder = container.findViewById(R.id.progress_details_order);
        mDetailsOrderRecyclerView = container.findViewById(R.id.rv_order_order_layout);
        mDetailsOrderRecyclerView.setHasFixedSize(true);
        mDetailsOrderAdapter = new DetailsOrderAdapter(getActivity(), mDetailsOrder, this);
        mDetailsOrderLinearLayoutManager = new LinearLayoutManager(getContext());
        mDetailsOrderRecyclerView.setLayoutManager(mDetailsOrderLinearLayoutManager);
        mDetailsOrderRecyclerView.setAdapter(mDetailsOrderAdapter);

        //
        // getSpinnerListening();
    }
    //endregion
    //region spinner categories
    private void getSpinnerListening(){
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //todo in async way
               // mMenuAdapter.getFilter().filter(mCategoriesId.get(i).toString());
                mFamilleFilter =  mMenuAdapter.getFilter();
                AsyncFilter filter = new AsyncFilter();
                filter.execute(mCategoriesId.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    class AsyncFilter extends AsyncTask<Integer, String, Filter> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressMenu.setVisibility(View.VISIBLE);
        }

        @Override
        protected Filter doInBackground(Integer... categories) {
            if(categories == null || categories.length <= 0) return null;
            mFamilleFilter.filter(categories[0].toString());
            return mFamilleFilter;
        }

        @Override
        protected void onPostExecute(Filter filter) {
            super.onPostExecute(filter);
            mProgressMenu.setVisibility(View.GONE);
            if(filter == null) return;
            mMenuAdapter.setFilter((MenuAdapter.FamilleFilter) filter);
        }
    }

    //endregion
    /**
     * Get data from activity (and Tables Fragment)
     */
    //region table selected
    //set data of selected table
    public void setData(Table table, String username){
        mTable = table;
        mUsername = username;
        //Init to default value when change table (no save state gone)
        mOrder = null;
        mDetailsOrderTemp = new ArrayList<>();
        mDetailsOrderAdapter.refreshAdapter(mDetailsOrder);

        MenuFragment.AsyncOrder asyncOrder = new MenuFragment.AsyncOrder();
        asyncOrder.execute(mTable);
    }
    //selected table order if exist
    Order mOrder = null;
    /**
     * Get Just if Order is open (no close)
     * so the Table is still occupied
     */
    boolean mConnectionError = false;
    class AsyncOrder extends AsyncTask<Table, String, List<DetailsOrder>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDetailsOrder.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<DetailsOrder> doInBackground(Table... tables) {
            if(tables == null || tables.length <= 0) return null;
            Table table = tables[0];
            //if table is free don't get order
            if(table.getEtat() == Utils.TableState.FREE) return null;
            mOrder = null;
            Connection connection = DatabaseAccess.databaseConnection(MenuFragment.this.getActivity());
            if(connection == null) {
                mConnectionError = true;
                return null;
            }else{
                mConnectionError = false;
            }

            mOrder = OrderService.getTableOpenOrderById(connection, table.getId());
            if(mOrder == null) {
                //isTableFree = true;
                return null;
            }
            return DetailsOrderService.getDetailsOrderByOrderId(connection, mOrder.getIdCmd());
        }

        @Override
        protected void onPostExecute(List<DetailsOrder> detailsOrders) {
            super.onPostExecute(detailsOrders);
            mProgressDetailsOrder.setVisibility(View.GONE);

            ((TablesActivity) getActivity()).setConnectionError(mConnectionError);


            if(detailsOrders != null){
                mDetailsOrderTemp = detailsOrders;
                mDetailsOrderAdapter.refreshAdapter(mDetailsOrderTemp);

            }
            //Sum price
            BigDecimal totalPrice = BigDecimal.valueOf(0);
            for(int i = 0; i<mDetailsOrderTemp.size(); i++)
                totalPrice  = totalPrice.add(mDetailsOrderTemp.get(i).getMtnetArt());
            String totalPriceFormat = Utils.Formater.getBigDecimalFormat(totalPrice, 2) + " DA";
            mTotalPriceTextView.setText(totalPriceFormat);


        }
    }

    //endregion
    //region Menu
    private void executeMenuTask()
    {
        MenuFragment.AsyncMenu asyncMenu = new MenuFragment.AsyncMenu();
        asyncMenu.execute();

        MenuFragment.AsyncCategorie asyncCategorie = new MenuFragment.AsyncCategorie();
        asyncCategorie.execute();
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
            Connection connection = DatabaseAccess.databaseConnection(MenuFragment.this.getActivity());
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
     * Get Menu Categories
     */
    class AsyncCategorie extends AsyncTask<String, String, List<Category>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Category> doInBackground(String... strings) {
            Connection connection = DatabaseAccess.databaseConnection(MenuFragment.this.getActivity());
            return FamillyService.getFammillies(connection);
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            super.onPostExecute(categories);
            if(categories == null) return;

            mCategories = new ArrayList<>();
            mCategoriesId = new ArrayList<>();
            mCategories.add("All");
            mCategoriesId.add(0);
            for(int i=0; i<categories.size(); i++){
                mCategories.add(categories.get(i).getName());
                mCategoriesId.add(categories.get(i).getId());
            }
            dataAdapter =new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    mCategories);
            //dataAdapter.notifyDataSetChanged();

            mSpinner.setAdapter(dataAdapter);
            getSpinnerListening();
        }
    }

    //endregion
    //region Select Plat
    @Override
    public void onPlatSelected(int idPlat) {
        //if plat exist in list of order perform update operation
        boolean update = false;
        for (int j = 0; j < mDetailsOrder.size(); j++){
            if(mDetailsOrder.get(j).getCodeProd() == (idPlat)){
                BigDecimal total = mDetailsOrder.get(j).getQttProd().add(BigDecimal.valueOf(1));
                DetailsOrder detailOrder =  mDetailsOrder.get(j);
                detailOrder.setQttProd(total);
                mDetailsOrderAdapter.refreshItemAdapter(j, detailOrder);
                update = true;
            }
        }
        if(!update) {
            //get Plat object
            int platPosition = 0;
            for (int i = 0; i < mPlats.size(); i++ ){
                if(mPlats.get(i).getIdProd() == idPlat )
                    platPosition = i;
            }
            DetailsOrder detailsOrder = new DetailsOrder(
                    0,
                    0,
                    idPlat,
                    mPlats.get(platPosition).getDesignProf(),
                    mPlats.get(platPosition).getPrixProdVente(),
                    BigDecimal.valueOf(1),
                    mPlats.get(platPosition).getTva(),
                    BigDecimal.valueOf(0),
                   0,
                    mPlats.get(platPosition).getImpriment()

            );
            mDetailsOrderTemp.add(detailsOrder);
            mDetailsOrderAdapter.refreshAdapter(mDetailsOrderTemp);
        }

        //update total price
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for(int i = 0; i<mDetailsOrderTemp.size(); i++)
            totalPrice  = totalPrice.add(mDetailsOrderTemp.get(i).getMtnetArt());
        String totalPriceFormat = Utils.Formater.getBigDecimalFormat(totalPrice, 2) + " DA";
        mTotalPriceTextView.setText(totalPriceFormat);
    }
    //endregion
    //region save
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onSaveOrder(int userId){

        MenuFragment.AsyncSave asyncSave = new MenuFragment.AsyncSave();

        asyncSave.execute(userId);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private int onCreateOrder(Connection connection, int userId){
        Log.e("CREATE ORDER", "onCreateOrder ....");

        if(mDetailsOrder.size() < 0) return 0;
        //date time = now
        Date nowDate = new Date();
        //get last order to increment new one
        int lastOrderId = getLastOrder(connection);
        if(lastOrderId == 0) return 0;

        int orderId = lastOrderId + 1;
        //int cmfId = String.format(orderId);

        //get total ht, tva, ttc
        BigDecimal ht;
        BigDecimal tva = new BigDecimal(0);
        BigDecimal ttc = new BigDecimal(0);

        for(int i=0; i<mDetailsOrder.size(); i++){
            tva = tva.add(mDetailsOrder.get(i).getMttvaArt());
            ttc = ttc.add(mDetailsOrder.get(i).getMtnetArt());
        }
        ht = ttc.subtract(tva);
        Order order = new Order(orderId, 1, "Client Divers", nowDate, ht, tva, ttc,
                false, 1, 1, mTable.getId(), nowDate, userId, nowDate, userId);


        return createOrder(connection, order);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private int onCreateDetailsOrder(Connection connection, int orderId, DetailsOrder detailsOrder, int userId){
        detailsOrder.setNumCmd(orderId);
        return createDetailsOrder(connection, detailsOrder, userId);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private DetailsOrder onUpdateDetailsOrder(Connection connection, DetailsOrder detailsOrder){
        return updateDetailsOrder(connection, detailsOrder);
    }
    class AsyncSave extends AsyncTask<Integer, String, Order>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDetailsOrder.setVisibility(View.VISIBLE);
        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Order doInBackground(Integer... userId) {


            Connection connection = DatabaseAccess.databaseConnection(MenuFragment.this.getActivity());
            if(connection == null) {
                mConnectionError = true;
                Log.e("CREATE ORDER", "connection == null....");
                return null;
            }else {
                mConnectionError = false;
                Log.e("CREATE ORDER", "connection != null....");
            }

            if(mOrder == null) {
                //create order + order details
                int idOrder = onCreateOrder(connection, userId[0]);
                if (idOrder == 0) return null;
                for(int i = 0; i < mDetailsOrder.size(); i++){
                    onCreateDetailsOrder(connection, idOrder, mDetailsOrder.get(i), userId[0]);

                    Log.e("CREATE DETAILS ORDER", "creating order ....");
                }
            }else {
                //update order ht, tva, ttc,
                BigDecimal ht;
                BigDecimal tva = BigDecimal.valueOf(0);
                BigDecimal ttc = BigDecimal.valueOf(0);
                //update details order + add details created (id == 0)
                for(int i = 0; i < mDetailsOrder.size(); i++){
                    if (mDetailsOrder.get(i).getNbrLigne() == 0){
                        onCreateDetailsOrder(connection, mOrder.getIdCmd(), mDetailsOrder.get(i), userId[0]);
                    }
                    else {
                        onUpdateDetailsOrder(connection, mDetailsOrder.get(i));
                    }
                    tva = tva.add(mDetailsOrder.get(i).getMttvaArt());
                    ttc = ttc.add(mDetailsOrder.get(i).getMtnetArt());
                }

                ht = ttc.subtract(tva);
                mOrder.setHtCmd(ht);
                mOrder.setTvaCmd(tva);
                mOrder.setTtcCmd(ttc);
                mOrder.setUserModification(1);
                //mOrder.setUserModification(mUsername);
                //apply the update
                updateOrderState(connection, mOrder);

            }

            //update table state
            Table table = getTableFromId(connection, mTable.getId());
            table.setEtat(Utils.TableState.OCCUPIE);
            updateTableStatue(connection, table);
            return mOrder;

        }

        @Override
        protected void onPostExecute(Order order) {
            super.onPostExecute(order);
            mProgressDetailsOrder.setVisibility(View.GONE);
            ((TablesActivity) getActivity()).setConnectionError(mConnectionError);

            if(order == null) return;

            //refresh details order panel
            MenuFragment.AsyncOrder asyncOrder = new MenuFragment.AsyncOrder();
            asyncOrder.execute(mTable);
        }
    }
    //endregion
    //region hidden

    //TODO if we serve order statue
    /*public void onServeOrder(){
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
        MenuFragment.AsyncClose asyncClose = new MenuFragment.AsyncClose();
        asyncClose.execute("");
    }
    class AsyncClose extends AsyncTask<String, String, Order>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDetailsOrder.setVisibility(View.VISIBLE);
        }
        @Override
        protected Order doInBackground(String... strings) {
            Connection connection = DatabaseAccess.databaseConnection(MenuFragment.this.getActivity());
            if(mOrder == null) return null;
            //update order close to true
            mOrder.setEtatCmd(2);
            updateOrderState(connection, mOrder);
            //update table state
            Table table = getTableFromId(connection, mIdTable);
            table.setEtat(Utils.TableState.FREE);
            updateTableStatue(connection, table);
            return mOrder;



        }
        @Override
        protected void onPostExecute(Order order) {
            super.onPostExecute(order);
            mProgressDetailsOrder.setVisibility(View.GONE);
            if(order == null) return;
            //refresh details order panel
            MenuFragment.AsyncOrder asyncOrder = new MenuFragment.AsyncOrder();
            asyncOrder.execute(mIdTable);
        }
    }
    */
    //endregion
    //region Details Order function
    private boolean delete = false, modification = false;
    private BigDecimal detailOrderNumber = BigDecimal.valueOf(1);
    private int detailIdToDelete;
    private Dialog editDialog;
    @Override
    public void onDetailOrderSelected(int idDetailOrder) {

        DetailsOrder detailOrder = null;
        //get detail info
        for(int i=0; i<mDetailsOrderTemp.size(); i++){
            if(mDetailsOrder.get(i).getNbrLigne() == idDetailOrder)
                detailOrder = mDetailsOrder.get(i);
        }
        if(detailOrder == null) return;

        // custom dialog
        editDialog = new Dialog(getContext());
        editDialog.setContentView(R.layout.row_dialog);
        editDialog.setTitle("Edit "+ detailOrder.getLibeProd());
        // set the custom dialog components - text, image and button
        //TextView articleName = editDialog.findViewById(R.id.tv_article_name_dialog);

        //articleName.setText(detailOrder.getLibeProd());
        final TextView counter = editDialog.findViewById(R.id.tv_article_number_dialog);
        String qte = Utils.Formater.getBigDecimalFormat(detailOrder.getQttProd(), 0);
        counter.setText(qte);

        Button minusBtn = editDialog.findViewById(R.id.btn_minus_counter);
        Button plusBtn = editDialog.findViewById(R.id.btn_plus_counter);
        TextView deleteBtn = editDialog.findViewById(R.id.btn_delete_dialog);
        TextView okBtn = editDialog.findViewById(R.id.btn_confirmation_dialog);
        Button cancelBtn = editDialog.findViewById(R.id.btn_cancel_dialog);

        //init
        detailOrderNumber = detailOrder.getQttProd();
        detailIdToDelete = idDetailOrder;
        modification = false;
        delete = false;


        // if button is clicked, close the custom dialog
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailOrderNumber = detailOrderNumber.subtract(BigDecimal.valueOf(1));
                String qte = Utils.Formater.getBigDecimalFormat(detailOrderNumber, 0);
                if(qte.equals("0"))
                    detailOrderNumber = BigDecimal.valueOf(1);
                modification = true;
                qte = Utils.Formater.getBigDecimalFormat(detailOrderNumber, 0);
                counter.setText(qte);

            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailOrderNumber = detailOrderNumber.add(BigDecimal.valueOf(1));
                modification = true;
                String qte = Utils.Formater.getBigDecimalFormat(detailOrderNumber, 0);
                counter.setText(qte);

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
            Connection connection = DatabaseAccess.databaseConnection(MenuFragment.this.getActivity());
            return DetailsOrderService.deleteDetailsOrder(connection, detail);

        }

        @Override
        protected void onPostExecute(DetailsOrder detailsOrdersDeleted) {
            super.onPostExecute(detailsOrdersDeleted);
            mProgressDetailsOrder.setVisibility(View.GONE);
            if(detailsOrdersDeleted != null) {
                for (int i = 0; i<mDetailsOrderTemp.size(); i++)
                    if(mDetailsOrderTemp.get(i).getNbrLigne() == detailIdToDelete)
                        mDetailsOrderTemp.remove(detailsOrdersDeleted);

                mDetailsOrderAdapter.refreshAdapter(mDetailsOrderTemp);
            }
            //Sum price
            BigDecimal totalPrice = BigDecimal.valueOf(0);
            for(int i = 0; i<mDetailsOrderTemp.size(); i++)
                totalPrice  = totalPrice.add(mDetailsOrderTemp.get(i).getMtnetArt());
            String totalPriceFormat = Utils.Formater.getBigDecimalFormat(totalPrice, 2) + " DA";
            mTotalPriceTextView.setText(totalPriceFormat);

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
            detail.setQttProd(detailOrderNumber);
            return detail;

        }

        @Override
        protected void onPostExecute(DetailsOrder detailsOrdersUpdated) {
            super.onPostExecute(detailsOrdersUpdated);
            mProgressDetailsOrder.setVisibility(View.GONE);
            if(detailsOrdersUpdated != null) {
                for (int i = 0; i<mDetailsOrderTemp.size(); i++)
                    if(mDetailsOrderTemp.get(i).getNbrLigne() == detailIdToDelete){
                        mDetailsOrderTemp.get(i).setQttProd(detailOrderNumber);
                    }

                mDetailsOrderAdapter.refreshAdapter(mDetailsOrderTemp);
            }
            //Sum price
            BigDecimal totalPrice = BigDecimal.valueOf(0);
            for(int i = 0; i<mDetailsOrderTemp.size(); i++)
                totalPrice  = totalPrice.add(mDetailsOrderTemp.get(i).getMtnetArt());
            String totalPriceFormat = Utils.Formater.getBigDecimalFormat(totalPrice, 2) + " DA";
            mTotalPriceTextView.setText(totalPriceFormat);

            editDialog.dismiss();
        }
    }

    //endregion
}
