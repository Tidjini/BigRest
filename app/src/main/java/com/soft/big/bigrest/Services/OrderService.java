package com.soft.big.bigrest.Services;

import android.util.Log;

import com.soft.big.bigrest.Model.Order;
import com.soft.big.bigrest.Model.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Tidjini on 15/11/2017.
 */

public class OrderService {

    public final static String TAG = "OrderService";

    /**
     * Open Order means Close Field = false (0)
     * @param idTable
     * @return
     */
    private static String selectTableOpenOrderQueryBuilder(int idTable){

        return "select * from Cmd Where " +
                "IdTable = '"+ Integer.toString(idTable) +"' And " +
                "Close = '0'";
    }

    public static Order getTableOpenOrderFromId(Connection connection, int idTable){
        Statement statement;
        Order order = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectTableOpenOrderQueryBuilder(idTable));
            if (resultSet.next()){
                int id = resultSet.getInt("Id");
                int idUser = resultSet.getInt("IdUser");
                double totalHT = resultSet.getDouble("TotalHT");
                String remarque = resultSet.getString("Remarque");
                boolean served = resultSet.getBoolean("Effectuer");
                boolean close = resultSet.getBoolean("Close");

                order = new Order(id,  idTable, idUser, totalHT, served,  close, remarque);
            }

            return order;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }

}
