package com.soft.big.bigrest.Behaviors;

import android.app.Activity;
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
    public static Configuration configuration;

    public static Connection databaseConnection(Activity activity){

        configuration = new Configuration(activity);
        //policy of the application content
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;
        String connectionUrl;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionUrl = connectionUrlBuilder(configuration);
            connection  = DriverManager.getConnection(connectionUrl);
        }catch (SQLException exception){
            Log.e(TAG, exception.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return connection;


    }
    public static Connection configDbConnection(){

        //policy of the application content
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;
        String connectionUrl;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionUrl = connectionConfigUrlBuilder();
            connection  = DriverManager.getConnection(connectionUrl);
        }catch (SQLException exception){
            Log.e(TAG, exception.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return connection;


    }

    /**
     * to build connection url for database
     * @return
     */
    private static String connectionConfigUrlBuilder(){


        return Constants.URL_PREFIX +configuration.getServerAddress() +";instanceName="+configuration.getInstance()+";DatabaseName="+configuration.getDatabaseName()
               // + "Configuration"
                +";integratedSecurity="+configuration.getIntegratedSecurtity()
                + ";user="+configuration.getDatabaseUsername()
                + ";password="+configuration.getDatabasePassword();

       /* return Constants.URL_PREFIX +Constants.SERVER_IP +":1433"+"/"+Constants.DATABASE_NAME+";instanceName="+Constants.INSTANCE_NAME+
                ";integratedSecurity="+integratedSecurity+
                ";user="+Constants.DATABASE_USERNAME +
                ";password="+Constants.DATABASE_PASSWORD;*/
    }
    private static String connectionUrlBuilder(Configuration configuration){


        return Constants.URL_PREFIX +configuration.getServerAddress() +";instanceName="+configuration.getInstance()+";DatabaseName="+configuration.getDatabaseName()+
                ";integratedSecurity="+configuration.getIntegratedSecurtity()
                + ";user="+configuration.getDatabaseUsername()
                + ";password="+configuration.getDatabasePassword();

       /* return Constants.URL_PREFIX +Constants.SERVER_IP +":1433"+"/"+Constants.DATABASE_NAME+";instanceName="+Constants.INSTANCE_NAME+
                ";integratedSecurity="+integratedSecurity+
                ";user="+Constants.DATABASE_USERNAME +
                ";password="+Constants.DATABASE_PASSWORD;*/
    }
}
