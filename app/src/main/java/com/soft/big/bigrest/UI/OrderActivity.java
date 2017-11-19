package com.soft.big.bigrest.UI;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.soft.big.bigrest.R;

public class OrderActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        bindActivity();
    }


    private void bindActivity(){
        //Custom Toolbar
        mToolbar = findViewById(R.id.nav_action_custom_bar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getTableData();

    }


    @Override
    protected void onStart() {
        super.onStart();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.order_menu, menu);
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


}
