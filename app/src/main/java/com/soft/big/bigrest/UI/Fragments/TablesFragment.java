package com.soft.big.bigrest.UI.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.soft.big.bigrest.Adapters.TableAdapter;
import com.soft.big.bigrest.Behaviors.DatabaseAccess;
import com.soft.big.bigrest.Behaviors.Utils;
import com.soft.big.bigrest.Model.Table;
import com.soft.big.bigrest.R;
import com.soft.big.bigrest.Services.TableService;
import com.soft.big.bigrest.UI.OrderActivity;
import com.soft.big.bigrest.UI.TablesActivity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.soft.big.bigrest.Behaviors.Constants.TABLE_ID_EXTRA_MESSAGE;


/**
 *
 */
public class TablesFragment extends Fragment implements TableAdapter.TablesClickHandler{

    private RecyclerView mTablesRecyclerView;
    private TableAdapter mTableAdapter;

    private List<Table> mTables = new ArrayList<Table>();
    private ProgressBar mProgressBar;


    OnTableSelectedListener mCallback;



    // Container Activity must implement this interface
    public interface OnTableSelectedListener {
        void onTableSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnTableSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    TablesActivity mActivity;
    int mTablesDisponibles = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tables, container);
        bindFragment(rootView);




        executeTask();


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
/*        mTables.clear();
        mTableAdapter.notifyDataSetChanged();*/
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void bindFragment(View container){
        mProgressBar = container.findViewById(R.id.progress_tables);
        mActivity = (TablesActivity) getActivity();



        mTablesRecyclerView = container.findViewById(R.id.recycler_view);
        mTablesRecyclerView.setHasFixedSize(true);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
//      TODO if (tabletSize) {
//            // do something
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),
//                    2);
//            mTablesRecyclerView.setLayoutManager(gridLayoutManager);
//
//        } else {
//           // do something else
//           mGridLayoutManager = new StaggeredGridLayoutManager(3,
//                   StaggeredGridLayoutManager.VERTICAL);
//           mTablesRecyclerView.setLayoutManager(mGridLayoutManager);
//        }

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mTableAdapter = new TableAdapter(getActivity(), mTables, this);

        mTablesRecyclerView.setAdapter(mTableAdapter);

        mTablesRecyclerView.setLayoutManager(mLinearLayoutManager);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void executeTask()
    {
        AsyncTables asyncTables = new AsyncTables();
        asyncTables.execute();


    }



    @Override
    public void onTableSelected(int idTable) {

        mCallback.onTableSelected(idTable);
        //TODO Intent intent = new Intent(getContext(), OrderActivity.class);
        //intent.putExtra(TABLE_ID_EXTRA_MESSAGE, idTable);
        //getContext().startActivity(intent);
    }

    class AsyncTables extends AsyncTask<String, String, List<Table>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Table> doInBackground(String... strings) {
            Connection connection = DatabaseAccess.databaseConnection(TablesFragment.this.getActivity());
            return TableService.getTables(connection);
            //Fake
            /*Table table;
            ArrayList<Table> tables = new ArrayList<>();
            for(int i = 0;  i<10; i++){
                table = new Table(i,  i, i, "Table "+ i, "Rmarque bien "+ i,  Utils.TableState.FREE);
                tables.add(table);

            }
            for(int i = 10;  i<12; i++){
                table = new Table(i,  i, i, "Table "+ i, "Rmarque bien "+ i,  Utils.TableState.SERVED);
                tables.add(table);

            }
            for(int i = 12;  i<100; i++){
                table = new Table(i,  i, i, "Table "+ i, "Rmarque bien "+ i,  Utils.TableState.OCCUPIE);
                tables.add(table);

            }
            return tables;*/



        }

        @Override
        protected void onPostExecute(List<Table> tables) {
            super.onPostExecute(tables);
            mProgressBar.setVisibility(View.GONE);

            if(tables == null) return;
            mTables = tables;
            mTableAdapter.refreshAdapter(mTables);
            //update user interface
            mTablesDisponibles = 0;
            for(int i = 0; i < mTables.size(); i++)
                if(mTables.get(i).getEtat() == Utils.TableState.FREE)
                    mTablesDisponibles++;
            mActivity.setTablesDisponible(mTablesDisponibles);

            //select the first table in the list (init)
            onTableSelected(mTables.get(0).getId());




        }
    }
}
