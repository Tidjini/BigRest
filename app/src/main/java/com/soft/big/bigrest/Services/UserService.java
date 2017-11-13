package com.soft.big.bigrest.Services;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class UserService {

    public final static String TAG = "UserService";

    private static String selectQueryBuilder(String username, String password){
        return "select * from Users Where " +
                "Username = '"+ username +"' and " +
                "Password = '"+ password +"'";
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


}
