package com.soft.big.bigrest.Services;

import android.util.Log;

import com.soft.big.bigrest.Adapters.TableAdapter;
import com.soft.big.bigrest.Model.DetailsOrder;
import com.soft.big.bigrest.Model.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tidjini on 15/11/2017.
 */

public class DetailsOrderService {

    public final static String TAG = "DetailsOrderService";

    private static String selectDetailsOrderQueryBuilder(int idOrder){

        return "select * from DetailsCmd Where " +
                "IdCmd = '"+ Integer.toString(idOrder) +"' ";
    }


    public static List<DetailsOrder> getDetailsOrder(Connection connection, int idOrder){
        Statement statement;
        List<DetailsOrder> detailsOrders = new ArrayList<DetailsOrder>();
        DetailsOrder detailOrder;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectDetailsOrderQueryBuilder(idOrder));
            while (resultSet.next()){
                int id = resultSet.getInt("Id");
                int idPlat = resultSet.getInt("IdPlat");
                int total = resultSet.getInt("Total");
                double totalPrice = resultSet.getDouble("TotalPrice");
                String remarque = resultSet.getString("Remarque");
                //set data of order in table

                //TODO detailOrder = new DetailsOrder(id,  number, maxNumber, remarque, tableDictionaryEntry.getValue(), name, tableDictionaryEntry.getKey());
                //TODO tables.add(table);
            }

            return detailsOrders;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }
}
