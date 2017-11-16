package com.soft.big.bigrest.Services;


import android.util.Log;

import com.soft.big.bigrest.Adapters.TableAdapter;
import com.soft.big.bigrest.Model.Order;
import com.soft.big.bigrest.Model.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.soft.big.bigrest.Converters.TableStateConverter.stateConverter;
import static com.soft.big.bigrest.Services.OrderService.getTableOpenOrderById;

/**
 * Created by Tidjini on 10/11/2017.
 */

public class TableService {

    public final static String TAG = "TableService";
    private static String selectAllQueryBuilder(){
        return "select Id\n" +
                "      , Name\n" +
                "      , Numero\n" +
                "      , Remarque\n" +
                "      , MaxNumber\n" +
                "      , State from ResTables";
    }
    private static String selectTableQueryBuilder(int id){

        return "select Id\n" +
                "      , Name\n" +
                "      , Numero\n" +
                "      , Remarque\n" +
                "      , MaxNumber\n" +
                "      , State from ResTables Where " +
                "Id = '"+ Integer.toString(id) +"'";
    }




    //Map list => get just one entry Map.Entry<TableAdapter.State , Integer>
    private static Map.Entry<TableAdapter.State , Integer> getTableState(Connection connection, int idTable){
        //dictionary to get <State, order_id> pair
        Map<TableAdapter.State, Integer> tableDictionary = new HashMap<TableAdapter.State, Integer>();
        //free state
        int state = 0, orderId = 0;
        Order tableOpenOrder = getTableOpenOrderById(connection, idTable);
        if(tableOpenOrder != null)
        {
            orderId = tableOpenOrder.getId();
            //occupied
            if(!tableOpenOrder.isClose())
                state = 1;
            //served
            if(tableOpenOrder.isEffected())
                state = 2;

        }
        //put data
        tableDictionary.put(stateConverter(state), orderId);
        return  tableDictionary.entrySet().iterator().next();
    }


    /**
     * Get Tables list with all informations data + state + id of opened order
     * @param connection
     * @return
     */
    public static List<Table> getTables(Connection connection){
        Statement statement;
        List<Table> tables = new ArrayList<Table>();
        Table table;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllQueryBuilder());
            while (resultSet.next()){
                int id = resultSet.getInt("Id");
                String name = resultSet.getString("Name");
                int number = resultSet.getInt("Numero");
                String remarque = resultSet.getString("Remarque");
                int maxNumber = resultSet.getInt("MaxNumber");
                //get table state form orders, open order id
                Map.Entry<TableAdapter.State , Integer> tableDictionaryEntry  = getTableState(connection, id);
                //set data of order in table
                table = new Table(id,  number, maxNumber, remarque, tableDictionaryEntry.getValue(), name, tableDictionaryEntry.getKey());
                tables.add(table);
            }

            return tables;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }

    /**
     * Called when table is selected; to check table state
     * @param connection
     * @param id
     * @return
     */
    public static Table getTableFromId(Connection connection, int id){
        Statement statement;
        Table table = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectTableQueryBuilder(id));
            if (resultSet.next()){
                String name = resultSet.getString("Name");
                int numero = resultSet.getInt("Numero");
                String remarque = resultSet.getString("Remarque");
                int maxNumber = resultSet.getInt("MaxNumber");
                //state; open order id
                Map.Entry<TableAdapter.State , Integer> tableDictionaryEntry  = getTableState(connection, id);
                //set data of order in table
                table = new Table(id,  numero, maxNumber, remarque, tableDictionaryEntry.getValue(), name, tableDictionaryEntry.getKey());

            }

            return table;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }



}
