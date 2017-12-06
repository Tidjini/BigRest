package com.soft.big.bigrest.Notifier;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.soft.big.bigrest.Behaviors.DatabaseAccess;
import com.soft.big.bigrest.UI.LoginActivity;

import java.sql.Connection;

/**
 * Created by Tidjini on 02/12/2017.
 */

public class BigRestService extends IntentService {

    public final static String TAG = "BigRestService";
    public final static String CONFIGURATION = "configuration";
    //public final static String CONNECTION_ERROR_FRAME = "connection_error";

    public BigRestService() {
        super(TAG);

   }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        /*Activity activity = intent.getex
        Connection connection = DatabaseAccess.databaseConnection();
        if(connection == null) {

            mConnectionErrorFrame.setVisibility(View.VISIBLE);

        }else {
            mConnectionErrorFrame.setVisibility(View.GONE);


        }*/

    }
}
