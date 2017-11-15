package com.soft.big.bigrest.UI;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
//in application


        bindActivity();

    }


    private void teblet(){
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
    protected void onStart() {
        super.onStart();

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
     * @param position
     */

    @Override
    public void onTableSelected(int position) {
        // Get Fragment B
        MenuFragment menuFragment = (MenuFragment)
                getSupportFragmentManager().findFragmentById(R.id.menu_fragment);
        //TODO update menuFragment.updateText(text);
        mTableNumberTextView.setText("Table np; " + position);
        menuFragment.setData();
    }
}
