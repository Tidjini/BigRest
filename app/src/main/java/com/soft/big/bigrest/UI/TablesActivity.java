package com.soft.big.bigrest.UI;

import android.content.pm.ActivityInfo;
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
import android.widget.TextView;

import com.soft.big.bigrest.Behaviors.Constants;
import com.soft.big.bigrest.R;
import com.soft.big.bigrest.UI.Fragments.MenuFragment;
import com.soft.big.bigrest.UI.Fragments.TablesFragment;


public class TablesActivity extends AppCompatActivity implements TablesFragment.OnTableSelectedListener{

    //drawer & action bar & toggle
    private DrawerLayout mDrawerLayout;
    // private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    private TextView mTableNumberTextView;

    private TablesFragment mTablesFragment;

    private TextView mTableDisponiblesTextView;
    private TextView mServerAddressTextView;

    private Snackbar mSnackbar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        if(mTablesFragment == null)
            mTablesFragment = (TablesFragment)
                    getSupportFragmentManager().findFragmentById(R.id.tables_fragment);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.tables_title);

        if(!getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }



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
    public void onTableSelected(int idTable) {
        // Get Fragment B
        if(mMenuFragment == null)
            mMenuFragment = (MenuFragment)
                getSupportFragmentManager().findFragmentById(R.id.menu_fragment);
        //TODO get table number
        mTableNumberTextView.setText("Table n° " + idTable);
        //TODO get user id to set user name
        mMenuFragment.setData(idTable, 1);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onSaveClicked(View view){

        if(mSnackbar != null) mSnackbar.dismiss();
        mSnackbar = Snackbar
                .make(view, "Really want to save order.", Snackbar.LENGTH_LONG)
                .setAction("SAVE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mMenuFragment == null) return;
                        mMenuFragment.onSaveOrder();
                        //refresh Tables
                        if(mTablesFragment == null) return;
                        mTablesFragment.executeTask();
                    }
                });

        mSnackbar.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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


    }

    public void onCloseClicked(View view){


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

    }

    public void setTablesDisponible(int tablesDisponible){
        mTableDisponiblesTextView.setText(tablesDisponible+ " Tables disponibles.");
    }

    public void setServerAddress(String serverAddress){
        mServerAddressTextView.setText(serverAddress);
    }
}
