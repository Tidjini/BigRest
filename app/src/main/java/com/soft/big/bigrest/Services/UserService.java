package com.soft.big.bigrest.Services;

import android.util.Log;

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
                "Utilisateur = '"+ username +"' and " +
                "Motpass = '"+ password +"'";
    }



    public static boolean login(Connection connection, String username, String password) {
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQueryBuilder(username, password));
            return resultSet.next();
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());

            return false;
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
