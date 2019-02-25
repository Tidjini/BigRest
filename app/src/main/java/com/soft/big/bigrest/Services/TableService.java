package com.soft.big.bigrest.Services;


import android.support.design.widget.TabLayout;
import android.util.Log;

import com.soft.big.bigrest.Behaviors.Utils;
import com.soft.big.bigrest.Model.Order;
import com.soft.big.bigrest.Model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.soft.big.bigrest.Behaviors.Constants.ORDER_TABLENAME;
import static com.soft.big.bigrest.Behaviors.Constants.TABLES_TABLENAME;
import static com.soft.big.bigrest.Converters.TableStateConverter.stateConverter;
import static com.soft.big.bigrest.Services.OrderService.getTableOpenOrderById;

/**
 * Created by Tidjini on 10/11/2017.
 */

/**
 * you can add somme other information
 * like remarque, for additional info like place of table (close to a window....)
 *      Max number (for place information)
 */


public class TableService {



    public final static String TAG = "TableService";
    private static String selectAllQueryBuilder(){
        return "select [Id]\n" +
                "      , [Libelle]\n" +
                "      , [Etat] from "+TABLES_TABLENAME;
    }
    private static String selectTableQueryBuilder(int id){
        return "select [Id]\n" +
                "      , [Libelle]\n" +
                "      , [Etat] from "+TABLES_TABLENAME+" Where " +
                "Id = '"+ Integer.toString(id) +"'";
    }

    private static String updateTableQueryBuilder(){
        return "UPDATE "+TABLES_TABLENAME+" " +
                "SET [Etat] = ?" +
                " Where\n" +
                "Id = ?";
    }




    //Map list => get just one entry Map.Entry<TableAdapter.State , Integer>
    private static Map.Entry<Utils.TableState, String> getTableState(Connection connection, int idTable){
        //dictionary to get <State, order_id> pair
        Map<Utils.TableState, String> tableDictionary = new HashMap<>();
        //free state
        int state = 0;
        int orderId = 0;
        Order tableOpenOrder = getTableOpenOrderById(connection, idTable);
        if(tableOpenOrder != null)
        {
            orderId = tableOpenOrder.getIdCmd();
            //occupied
            if(tableOpenOrder.getEtatCmd() == 1)
                state = 1;
            //served
            if(tableOpenOrder.getEtatCmd() == 3)
                state = 2;

        }
        //put data
        tableDictionary.put(stateConverter(state), Integer.toString(orderId));
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
                String name = resultSet.getString("Libelle");
                int etat = resultSet.getInt("Etat");
                //get table state form orders, open order id
                Map.Entry<Utils.TableState, String> tableDictionaryEntry  =  getTableState(connection, id);
                //set data of order in table
                //the value of table dictionnary is the id of the opened command
                //the key is the table state
                table = new Table(id, tableDictionaryEntry.getValue(), name, stateConverter(etat));
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
                String name = resultSet.getString("Libelle");
                int state = resultSet.getInt("Etat");
                //state; open order id
                Map.Entry<Utils.TableState , String> tableDictionaryEntry  = getTableState(connection, id);
                //set data of order in table
                //the value of table dictionnary is the id of the opened command
                //the key is the table state
                table = new Table(id, tableDictionaryEntry.getValue(), name, stateConverter(state));

            }

            return table;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }


    public static Table updateTableStatue(Connection connection, Table table){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    updateTableQueryBuilder()
            );
            int tableState = stateConverter(table.getEtat());
            statement.setInt(1, tableState);
            statement.setInt(2, table.getId());

            statement.executeUpdate();

            return table;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }


}
