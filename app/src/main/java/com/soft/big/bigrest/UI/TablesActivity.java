package com.soft.big.bigrest.UI;

import android.content.pm.ActivityInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.soft.big.bigrest.R;
import com.soft.big.bigrest.UI.Fragments.TablesFragment;


public class TablesActivity extends AppCompatActivity implements TablesFragment.OnTableSelectedListener{

    //drawer & action bar & toggle
    private DrawerLayout mDrawerLayout;
    // private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;






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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.tables_title);

        if(!getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * explain
     *  Table selected => check if is Active;
     *      Yes =>
     *
     *
     *
     * @param position
     */

    @Override
    public void onTableSelected(int position) {

    }
}
