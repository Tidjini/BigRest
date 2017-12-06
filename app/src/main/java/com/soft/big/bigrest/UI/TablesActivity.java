package com.soft.big.bigrest.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.soft.big.bigrest.Behaviors.Configuration;
import com.soft.big.bigrest.Behaviors.Constants;
import com.soft.big.bigrest.Behaviors.DatabaseAccess;
import com.soft.big.bigrest.Model.Table;
import com.soft.big.bigrest.R;
import com.soft.big.bigrest.Services.TableService;
import com.soft.big.bigrest.UI.Fragments.MenuFragment;
import com.soft.big.bigrest.UI.Fragments.TablesFragment;

import java.sql.Connection;

import static com.soft.big.bigrest.Behaviors.Constants.TABLE_ID_EXTRA_MESSAGE;
import static com.soft.big.bigrest.Behaviors.Constants.USER_NAME_EXTRA_MESSAGE;


public class TablesActivity extends AppCompatActivity implements TablesFragment.OnTableSelectedListener{

    //drawer & action bar & toggle
    private DrawerLayout mDrawerLayout;
    // private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    FrameLayout mProgressFrameLayout;
    FrameLayout mConnectionErrorFrame;

    private TablesFragment mTablesFragment;

    private TextView mTableNumberTextView;
    private TextView mTableDisponiblesTextView;
    private TextView mUsernameTextView;
    private TextView mServerAddressTextView;

    private Snackbar mSnackbar;




    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        if(mTablesFragment == null)
            mTablesFragment = (TablesFragment)
                    getSupportFragmentManager().findFragmentById(R.id.tables_fragment);

        mUsername = getIntent().getStringExtra(Constants.USER_NAME_EXTRA_MESSAGE);

        bindActivity();

    }


    private void tablet(){
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            // do something
        } else {
            // do something else
        }
    }

    private void bindActivity(){
        //Custom Toolbar
        mToolbar = findViewById(R.id.nav_action_custom_bar);
        setSupportActionBar(mToolbar);
        mTableNumberTextView = findViewById(R.id.table_name_number);
        mServerAddressTextView = findViewById(R.id.server_ip);
        mTableDisponiblesTextView = findViewById(R.id.tables_avaibles);
        mUsernameTextView = findViewById(R.id.user_name);

        mProgressFrameLayout = findViewById(R.id.progress_main);
        mConnectionErrorFrame = findViewById(R.id.connection_error_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.tables_title);

        if(!getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }



    }


    public void setConnectionError(boolean connectionError){
        if(connectionError)
            mConnectionErrorFrame.setVisibility(View.VISIBLE);
        else
            mConnectionErrorFrame.setVisibility(View.GONE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tables_menu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.send_order_menu:
                //todo send order ;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //Toast.makeText(this, "Orders "+ createLogicOrder(), Toast.LENGTH_LONG).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //TODO update this when address come from config
        setServerAddress(Constants.SERVER_IP);
        setUsername(mUsername);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet_land);
        if (tabletSize) {
            // do something
            AsyncConnectionTest asyncConnectionTest = new AsyncConnectionTest();
            asyncConnectionTest.execute("");
        }


    }

    /**
     * explain
     *  Table selected => check if is Active;
     *      Yes => display command content
     *      else => new command
     *
     *
     *
     *
     * @param idTable
     */

    MenuFragment mMenuFragment = null;

    @Override
    public void onTableSelected(Table table) {
        // Get Fragment B
        if(mMenuFragment == null)
            mMenuFragment = (MenuFragment)
                getSupportFragmentManager().findFragmentById(R.id.menu_fragment);
        mTableNumberTextView.setText(table.getLibelle().toUpperCase());
        mMenuFragment.setData(table, mUsername);
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onSaveClicked(View view){

        if(mMenuFragment == null) return;
        mMenuFragment.onSaveOrder();
        //refresh Tables
        if(mTablesFragment == null) return;
        mTablesFragment.executeTask();
       // if(mSnackbar != null) mSnackbar.dismiss();
       /* mSnackbar = Snackbar
                .make(view, "Really want to save order.", Snackbar.LENGTH_LONG)
                .setAction("SAVE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

        mSnackbar.show();*/

    }

    public void onRefreshClicked(View view){
        //refresh Tables
        if(mTablesFragment == null) return;
        mTablesFragment.executeTask();
    }

    /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onServieClicked(View view){

        if(mSnackbar != null) mSnackbar.dismiss();
        mSnackbar = Snackbar
                .make(view, "Really want to serve order.", Snackbar.LENGTH_LONG)
                .setAction("SERVE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mMenuFragment == null) return;
                        mMenuFragment.onServeOrder();
                        //refresh Tables
                        if(mTablesFragment == null) return;
                        mTablesFragment.executeTask();
                    }
                });

        mSnackbar.show();


    }*/

    /*public void onCloseClicked(View view){


        mSnackbar = Snackbar
                .make(view, "Really want to close order.", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mMenuFragment == null) return;
                        mMenuFragment.onCloseOrder();
                        //refresh Tables
                        if(mTablesFragment == null) return;
                        mTablesFragment.executeTask();
                    }
                });

        mSnackbar.show();

    }*/

    public void setTablesDisponible(int tablesDisponible){
        mTableDisponiblesTextView.setText(tablesDisponible+ " Tables disponibles.");
    }

    public void setServerAddress(String serverAddress){
        Configuration configuration = new Configuration(TablesActivity.this);
        mServerAddressTextView.setText(configuration.getServerAddress());
    }

    public void setUsername(String username){
        mUsernameTextView.setText(username);
    }

    public void onConfigClicked(View view){
        Intent intent = new Intent(this, ConfigurationActivity.class);
        startActivity(intent);
    }


    class AsyncConnectionTest extends AsyncTask<String, String, Connection> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressFrameLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected Connection doInBackground(String... strings) {

            return DatabaseAccess.databaseConnection(TablesActivity.this);

        }

        @Override
        protected void onPostExecute(Connection connection) {
            super.onPostExecute(connection);
            mProgressFrameLayout.setVisibility(View.GONE);
            if(connection == null) {
                //todo display
                mConnectionErrorFrame.setVisibility(View.VISIBLE);

            }else {
                mConnectionErrorFrame.setVisibility(View.GONE);


            }

        }
    }
}
