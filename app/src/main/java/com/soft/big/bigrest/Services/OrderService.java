package com.soft.big.bigrest.Services;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.soft.big.bigrest.Model.DetailsOrder;
import com.soft.big.bigrest.Model.Order;
import com.soft.big.bigrest.Model.Table;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import static com.soft.big.bigrest.Behaviors.Constants.ORDER_TABLENAME;

/**
 * Created by Tidjini on 15/11/2017.
 */

public class OrderService {

    public final static String TAG = "OrderService";

    /**
     * Open Order means Close Field = false (0)
     * @return
     */
    private static String selectTableOpenOrderQueryBuilder(){

        return "SELECT TOP 1 * FROM "+ ORDER_TABLENAME +" WHERE " +
                "[TableNum] = ? " +
                "ORDER BY idCmd DESC"
                ;
    }

    private static String selectOrderQueryBuilder(int id){

        return "select * from "+ ORDER_TABLENAME +" Where " +
                "idCmd = '"+ id +"' ";
    }

    private static String selectAllOrderQueryBuilder(){

        return "SELECT TOP 1 * FROM "+ ORDER_TABLENAME +" ORDER BY idCmd DESC ";
    }

    private static String createOrederBuilder(){
        return "insert into "+ORDER_TABLENAME+" (idCmd, CodeClient, NomClient, DateCmd, HtCmd, TvaCmd, TtcCmd, " +
                "EtatCmd, PayementCmd, ServeurCode, ServeurNom, TableNum, dateCreation, UserCreation, " +
                "DateModif, UserModif, Restepaie, IdCaisse) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    private static String updateOrderBuilder(){
        return "UPDATE "+ORDER_TABLENAME+"\n" +
                //"SET [Effectuer] = ?,\n" + For serve or not
                "SET [EtatCmd] = ?," +
                " [HtCmd] = ?,"+
                " [TvaCmd] = ?,"+
                " [TtcCmd] = ?,"+
                " [PayementCmd] = ?,"+
                " [DateModif] = ?,"+
                " [UserModif] = ?\n"+
                "Where\n" +
                "IdCmd = ?";
    }

    public static Order getTableOpenOrderById(Connection connection, int idTable){
        PreparedStatement statement;
        Order order = null;
        try {
            statement = connection.prepareStatement(selectTableOpenOrderQueryBuilder());
            statement.setString(1, Integer.toString(idTable));
            //statement.setInt(2, 1); // state Open = 1 , Close = 2
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int idCmd = resultSet.getInt("idCmd");
                String codeClient = resultSet.getString("CodeClient");
                String nomClient = resultSet.getString("NomClient");
                Date dateCmd = resultSet.getDate("DateCmd");
                int etatCmd = resultSet.getInt("EtatCmd");
                BigDecimal htCmd = resultSet.getBigDecimal("HtCmd");
                BigDecimal tvaCmd = resultSet.getBigDecimal("TvaCmd");
                BigDecimal ttcCmd = resultSet.getBigDecimal("TtcCmd");
                BigDecimal payementCmd = resultSet.getBigDecimal("PayementCmd");
                String codeServer = resultSet.getString("ServeurCode");
                String serveurNom = resultSet.getString("ServeurNom");
                String tableNum = resultSet.getString("TableNum");
                Date dateCreation = resultSet.getDate("dateCreation");
                String userCreation = resultSet.getString("UserCreation");
                Date dateModif = resultSet.getDate("DateModif");
                String userModif = resultSet.getString("UserModif");
                BigDecimal restePaie = resultSet.getBigDecimal("Restepaie");
                int idCaisse = resultSet.getInt("IdCaisse");

                order = new Order(idCmd, codeClient, nomClient, dateCmd, htCmd, tvaCmd, ttcCmd, payementCmd, etatCmd, codeServer, serveurNom, tableNum, dateCreation, userCreation, dateModif, userModif, restePaie, idCaisse);
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

                int idCmd = resultSet.getInt("idCmd");
                String codeClient = resultSet.getString("CodeClient");
                String nomClient = resultSet.getString("NomClient");
                Date dateCmd = resultSet.getDate("DateCmd");
                int etatCmd = resultSet.getInt("EtatCmd");
                BigDecimal htCmd = resultSet.getBigDecimal("HtCmd");
                BigDecimal tvaCmd = resultSet.getBigDecimal("TvaCmd");
                BigDecimal ttcCmd = resultSet.getBigDecimal("TtcCmd");
                BigDecimal payementCmd = resultSet.getBigDecimal("PayementCmd");
                String codeServer = resultSet.getString("ServeurCode");
                String serveurNom = resultSet.getString("ServeurNom");
                String tableNum = resultSet.getString("TableNum");
                Date dateCreation = resultSet.getDate("dateCreation");
                String userCreation = resultSet.getString("UserCreation");
                Date dateModif = resultSet.getDate("DateModif");
                String userModif = resultSet.getString("UserModif");
                BigDecimal restePaie = resultSet.getBigDecimal("Restepaie");
                int idCaisse = resultSet.getInt("IdCaisse");

                order = new Order(idCmd, codeClient, nomClient, dateCmd, htCmd, tvaCmd, ttcCmd, payementCmd, etatCmd, codeServer, serveurNom, tableNum, dateCreation, userCreation, dateModif, userModif, restePaie, idCaisse);

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
                    createOrederBuilder(),
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, order.getIdCmd());
            statement.setString(2, order.getCodeClient());
            statement.setString(3, order.getNomClient());
            statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            statement.setBigDecimal(5, order.getHtCmd());
            statement.setBigDecimal(6, order.getTvaCmd());
            statement.setBigDecimal(7, order.getTtcCmd());
            statement.setInt(8, order.getEtatCmd());
            statement.setBigDecimal(9, order.getPaymentCmd());
            statement.setString(10, order.getServerCode());
            statement.setString(11, order.getServerName());
            statement.setString(12, order.getTable());
            statement.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
            statement.setString(14, order.getUserCreator());
            statement.setTimestamp(15, new Timestamp(System.currentTimeMillis()));
            statement.setString(16, order.getUserModification());
            statement.setBigDecimal(17, order.getRestePaie());
            statement.setInt(18, order.getIdCaisse());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return 0;
            }
            return order.getIdCmd();
        } catch (SQLException e) {
            return 0;
        }

    }

    public static Order updateOrderState(Connection connection, Order order){
        Order orderUpdated = new Order();
        try {

            @SuppressLint("WrongConstant")
            PreparedStatement statement = connection.prepareStatement(
                    updateOrderBuilder()
            );

            statement.setInt(1, order.getEtatCmd());
            statement.setBigDecimal(2, order.getHtCmd());
            statement.setBigDecimal(3, order.getTvaCmd());
            statement.setBigDecimal(4, order.getTtcCmd());
            statement.setBigDecimal(5, order.getPaymentCmd());
            statement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            statement.setString(7, order.getUserModification());
            statement.setInt(8, order.getIdCmd());
            //Execute update request
            statement.executeUpdate();
            //get updated order
            orderUpdated = getOrderById(connection, order.getIdCmd());
            return orderUpdated;

        } catch (SQLException e) {
            return orderUpdated;
        }

    }

    public static String getLastOrder(Connection connection){

        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllOrderQueryBuilder());
            if (resultSet.next()){

                return resultSet.getString("idCmd");

            }

            return null;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }

    }
}
