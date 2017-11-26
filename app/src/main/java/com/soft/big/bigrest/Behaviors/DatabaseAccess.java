package com.soft.big.bigrest.Behaviors;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class DatabaseAccess {

    public final static String TAG = "DatabaseAccess";

    public static Connection databaseConnection(){
        //policy of the application content
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;
        String connectionUrl;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionUrl = connectionUrlBuilder("true");
            connection  = DriverManager.getConnection(connectionUrl);
        }catch (SQLException exception){
            Log.e(TAG, exception.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;


    }

    /**
     * to build connection url for database
     * @return
     */
    private static String connectionUrlBuilder(String integratedSecurity){
        return Constants.URL_PREFIX +Constants.SERVER_IP +";instanceName="+Constants.INSTANCE_NAME+";DatabaseName="+Constants.DATABASE_NAME+
                ";integratedSecurity="+integratedSecurity+
                ";user="+Constants.DATABASE_USERNAME +
                ";password="+Constants.DATABASE_PASSWORD;

       /* return Constants.URL_PREFIX +Constants.SERVER_IP +":1433"+"/"+Constants.DATABASE_NAME+";instanceName="+Constants.INSTANCE_NAME+
                ";integratedSecurity="+integratedSecurity+
                ";user="+Constants.DATABASE_USERNAME +
                ";password="+Constants.DATABASE_PASSWORD;*/
    }
}
