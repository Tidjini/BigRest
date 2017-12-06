package com.soft.big.bigrest.UI.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.soft.big.bigrest.Adapters.TableAdapter;
import com.soft.big.bigrest.Behaviors.DatabaseAccess;
import com.soft.big.bigrest.Behaviors.Utils;
import com.soft.big.bigrest.Model.Table;
import com.soft.big.bigrest.R;
import com.soft.big.bigrest.Services.TableService;
import com.soft.big.bigrest.UI.TablesActivity;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TablesFragment extends Fragment implements TableAdapter.TablesClickHandler{

    //table list
    private RecyclerView mTablesRecyclerView;
    private TableAdapter mTableAdapter;
    private List<Table> mTables = new ArrayList<Table>();
    //for progress sync
    private ProgressBar mProgressBar;
    //listener of click
    OnTableSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnTableSelectedListener {
        void onTableSelected(Table table);
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
    //number of tables availables
    int mTablesAvailables = 0;

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

    //region bind ui
    private void bindFragment(View container){
        mProgressBar = container.findViewById(R.id.progress_tables);
        mActivity = (TablesActivity) getActivity();
        mTablesRecyclerView = container.findViewById(R.id.recycler_view);
        mTablesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mTableAdapter = new TableAdapter(getActivity(), mTables, this);
        mTablesRecyclerView.setAdapter(mTableAdapter);
        mTablesRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    //endregion

    //region tables
    public void executeTask()
    {
        //get tables with async task
        AsyncTables asyncTables = new AsyncTables();
        asyncTables.execute();
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
            if(connection == null) return null;
            return TableService.getTables(connection);
         }

        @Override
        protected void onPostExecute(List<Table> tables) {
            super.onPostExecute(tables);
            mProgressBar.setVisibility(View.GONE);

            if(tables == null) {
                ((TablesActivity) getActivity()).setConnectionError(true);
                return;
            }
            ((TablesActivity) getActivity()).setConnectionError(false);
            mTables = tables;
            mTableAdapter.refreshAdapter(mTables);
            //update user interface
            mTablesAvailables = 0;
            for(int i = 0; i < mTables.size(); i++)
                if(mTables.get(i).getEtat() == Utils.TableState.FREE)
                    mTablesAvailables++;
            mActivity.setTablesDisponible(mTablesAvailables);

            //select the first table in the list (init)
            //onTableSelected(mTables.get(0).getId());

        }
    }

    //endregion

    //implement the on select method to get the selected table
    @Override
    public void onTableSelected(Table table) {
        mCallback.onTableSelected(table);
    }


}
