package com.soft.big.bigrest.Services;

import android.util.Log;

import com.soft.big.bigrest.Behaviors.DatabaseAccess;
import com.soft.big.bigrest.UI.LoginActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.soft.big.bigrest.Behaviors.Constants.USERS_TABLENAME;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class UserService {

    public final static String TAG = "UserService";

    private static String selectQueryBuilder(String username, String password){
        return "select * from "+ USERS_TABLENAME +" Where " +
                "Operateur = '"+ username +"'";
                //and " +
                //"Active = '"+ password +"'";
    }



    public static int login(Connection connection, String username, String password) {
        Statement statement;
        try {
            statement = connection.createStatement();

            ///create connection to configdb
            Connection configConnection = DatabaseAccess.configDbConnection();
            statement = configConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQueryBuilder(username, password));
            int id = 0;
            if(resultSet.next())
                id = resultSet.getInt("Id");
            return id;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return 0;
        }
    }
    public static String getUserName(Connection connection, String username, String password) {
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQueryBuilder(username, password));
            if(resultSet.next())
                return username;

            return "";
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());

            return "";
        }
    }



}
