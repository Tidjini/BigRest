package com.soft.big.bigrest.Services;

import android.net.NetworkInfo;
import android.util.Log;

import com.soft.big.bigrest.Adapters.TableAdapter;
import com.soft.big.bigrest.Model.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tidjini on 10/11/2017.
 */

public class TableService {

    public final static String TAG = "UserService";

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

    private static TableAdapter.State stateConverter(int state){
        if(state == 1) return TableAdapter.State.OCCUPIE;
        if(state == 2) return TableAdapter.State.SERVED;
        return TableAdapter.State.FREE;
    }

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
                int numero = resultSet.getInt("Numero");
                String remarque = resultSet.getString("Remarque");
                int maxNumber = resultSet.getInt("MaxNumber");
                int state = resultSet.getInt("State");

                table = new Table(id,  numero, maxNumber, name, remarque,  stateConverter(state));
                tables.add(table);
            }

            return tables;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }

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
                int state = resultSet.getInt("State");
                table = new Table(id,  numero, maxNumber, name, remarque,  stateConverter(state));
            }

            return table;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }



}
