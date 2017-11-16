package com.soft.big.bigrest.Services;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.soft.big.bigrest.Model.DetailsOrder;
import com.soft.big.bigrest.Model.Order;
import com.soft.big.bigrest.Model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private static String selectOrderQueryBuilder(int id){

        return "select * from Cmd Where " +
                "Id = '"+ Integer.toString(id) +"' ";
    }

    public static Order getTableOpenOrderById(Connection connection, int idTable){
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

    public static Order getOrderById(Connection connection, int id){
        Statement statement;
        Order order = new Order();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectOrderQueryBuilder(id));
            if (resultSet.next()){

                int idUser = resultSet.getInt("IdUser");
                int idTable = resultSet.getInt("IdTable");
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static int createOrder(Connection connection, Order order){

        if (connection == null || order == null) return 0;

        try {

            @SuppressLint("WrongConstant")
            PreparedStatement statement = connection.prepareStatement(
                    "insert into Cmd (IdUser, IdTable, TotalHt) " +
                            "values ('"+order.getIdUser()+"', '"+order.getIdTable()+"', '"+order.getTotalHt()+"')"
                    ,
                    Statement.RETURN_GENERATED_KEYS);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return 0;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            return 0;
        }

    }

    public static Order updateOrderState(Connection connection, Order order){
        Order orderUpdated = new Order();
        try {

            @SuppressLint("WrongConstant")
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Cmd\n" +
                            "SET Effectuer = '"+order.isEffected()+"',\n" +
                            "    Close = '"+order.isClose()+"',\n" +
                            "Where\n" +
                            "Id = '"+ Integer.toString(order.getId()) +"' "
            );

            //TODO create our java preparedstatement using a sql update query
//            PreparedStatement ps = conn.prepareStatement(
//                    "UPDATE Messages SET description = ?, author = ? WHERE id = ? AND seq_num = ?");
//
//            // set the preparedstatement parameters
//            ps.setString(1,description);
//            ps.setString(2,author);
//            ps.setInt(3,id);
//            ps.setInt(4,seqNum);

            //Execute update request
            statement.executeUpdate();
            //get updated order
            orderUpdated = getOrderById(connection, order.getId());
            return orderUpdated;

        } catch (SQLException e) {
            return orderUpdated;
        }

    }


}
