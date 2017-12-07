package com.soft.big.bigrest.Behaviors;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import static com.soft.big.bigrest.Behaviors.Constants.CONNECT;
import static com.soft.big.bigrest.Behaviors.Constants.DATABASE_NAME;
import static com.soft.big.bigrest.Behaviors.Constants.DATABASE_PASSWORD;
import static com.soft.big.bigrest.Behaviors.Constants.DATABASE_USERNAME;
import static com.soft.big.bigrest.Behaviors.Constants.INSTANCE_NAME;
import static com.soft.big.bigrest.Behaviors.Constants.INTEGRATED_SECURITY;
import static com.soft.big.bigrest.Behaviors.Constants.SERVER_IP;

/**
 * Created by Tidjini on 15/11/2017.
 */

public class Configuration {


    SharedPreferences sharedPreferences;

    /**
     * Configuration class : get server information to connect
     */
    public Configuration(Activity activity) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

    }

    public String getServerAddress() {
        return sharedPreferences.getString("ip_address", SERVER_IP);
    }

    public String getDatabaseName() {
        return sharedPreferences.getString("database_name", DATABASE_NAME);
    }

    public String getDatabaseUsername() {
        return sharedPreferences.getString("database_username", DATABASE_USERNAME);
    }

    public String getDatabasePassword() {
        return sharedPreferences.getString("database_password", DATABASE_PASSWORD);
    }

    public String getInstance() {
        return sharedPreferences.getString("instance_name", INSTANCE_NAME);
    }
    public boolean getIntegratedSecurtity() {
        return sharedPreferences.getBoolean("integrated_security", INTEGRATED_SECURITY);
    }

}
