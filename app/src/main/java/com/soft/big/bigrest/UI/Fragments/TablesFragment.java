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

import com.soft.big.bigrest.Adapters.TableAdapter;
import com.soft.big.bigrest.Behaviors.DatabaseAccess;
import com.soft.big.bigrest.Model.Table;
import com.soft.big.bigrest.R;
import com.soft.big.bigrest.Services.TableService;
import com.soft.big.bigrest.UI.OrderActivity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.soft.big.bigrest.Behaviors.Constants.TABLE_ID_EXTRA_MESSAGE;


/**
 *
 */
public class TablesFragment extends Fragment implements TableAdapter.TableAdapterOnClickHandler{

    private StaggeredGridLayoutManager mGridLayoutManager;
    private RecyclerView mTablesRecyclerView;
    private TableAdapter mTableAdapter;

    private List<Table> mTables = new ArrayList<Table>();
    private FrameLayout mProgressFrameLayout;


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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tables, container);
        bindFragment(rootView);


        mTableAdapter = new TableAdapter(getActivity(), mTables, this);
        mTablesRecyclerView.setAdapter(mTableAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        executeTask();
    }

    private void bindFragment(View container){
        mProgressFrameLayout = container.findViewById(R.id.fl_tables_process);


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
        mTablesRecyclerView.setLayoutManager(mLinearLayoutManager);


    }


    private void executeTask()
    {
        AsyncTables asyncTables = new AsyncTables();
        asyncTables.execute();


    }

    @Override
    public void onClick(int idTable) {
        mCallback.onTableSelected(idTable);
        //TODO Intent intent = new Intent(getContext(), OrderActivity.class);
        //intent.putExtra(TABLE_ID_EXTRA_MESSAGE, idTable);
        //getContext().startActivity(intent);
    }

    class AsyncTables extends AsyncTask<String, String, List<Table>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressFrameLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Table> doInBackground(String... strings) {
            //TODO Connection connection = DatabaseAccess.databaseConnection();
            //TODO return TableService.getTables(connection);
            //Fake
            Table table;
            ArrayList<Table> tables = new ArrayList<>();
            for(int i = 0;  i<10; i++){
                table = new Table(i,  i, i, "Table "+ i, "Rmarque bien "+ i,  TableAdapter.State.FREE);
                tables.add(table);

            }
            for(int i = 10;  i<12; i++){
                table = new Table(i,  i, i, "Table "+ i, "Rmarque bien "+ i,  TableAdapter.State.SERVED);
                tables.add(table);

            }
            for(int i = 12;  i<100; i++){
                table = new Table(i,  i, i, "Table "+ i, "Rmarque bien "+ i,  TableAdapter.State.OCCUPIE);
                tables.add(table);

            }
            return tables;



        }

        @Override
        protected void onPostExecute(List<Table> tables) {
            super.onPostExecute(tables);
            mProgressFrameLayout.setVisibility(View.GONE);

            if(tables == null) return;
            mTables = tables;
            mTableAdapter.refreshAdapter(mTables);

        }
    }
}
