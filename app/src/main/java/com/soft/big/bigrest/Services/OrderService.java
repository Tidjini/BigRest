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
                "[IdTable] = ? " +
                "ORDER BY Id DESC"
                ;
    }

    private static String selectOrderQueryBuilder(int id){

        return "select * from "+ ORDER_TABLENAME +" Where " +
                "Id = '"+ id +"' ";
    }

    private static String selectAllOrderQueryBuilder(){

        return "SELECT TOP 1 * FROM "+ ORDER_TABLENAME +" ORDER BY Id DESC ";
    }





    public static Order getTableOpenOrderById(Connection connection, int idTable){
        PreparedStatement statement;
        Order order = null;
        try {
            statement = connection.prepareStatement(selectTableOpenOrderQueryBuilder());
            statement.setInt(1, idTable);
            //statement.setInt(2, 1); // state Open = 1 , Close = 2
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int idCmd = resultSet.getInt("Id");
                int codeClient = resultSet.getInt("IdClient");
                String nomClient = resultSet.getString("NomClient");
                Date dateCmd = resultSet.getDate("Date");
                int etatCmd = resultSet.getInt("Etat");
                BigDecimal htCmd = resultSet.getBigDecimal("MTHT");
                BigDecimal tvaCmd = resultSet.getBigDecimal("MTTVA");
                BigDecimal ttcCmd = resultSet.getBigDecimal("MTTTC");
                boolean payementEtat = resultSet.getBoolean("EtatPayement");
                int idUser = resultSet.getInt("CreerPar");
                int tableNum = resultSet.getInt("IdTable");
                Date dateCreation = resultSet.getDate("DateCreation");
                int userCreation = resultSet.getInt("CreerPar");
                Date dateModif = resultSet.getDate("DateModification");

                order = new Order(idCmd, codeClient, nomClient, dateCmd, htCmd, tvaCmd, ttcCmd,
                        payementEtat, etatCmd, idUser, tableNum, dateCreation, userCreation, dateModif, idUser);
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

                int idCmd = resultSet.getInt("Id");
                int codeClient = resultSet.getInt("IdClient");
                String nomClient = resultSet.getString("NomClient");
                Date dateCmd = resultSet.getDate("Date");
                int etatCmd = resultSet.getInt("Etat");
                BigDecimal htCmd = resultSet.getBigDecimal("MTHT");
                BigDecimal tvaCmd = resultSet.getBigDecimal("MTTVA");
                BigDecimal ttcCmd = resultSet.getBigDecimal("MTTTC");
                boolean payementEtat = resultSet.getBoolean("EtatPayement");
                int idUser = resultSet.getInt("CreerPar");
                int tableNum = resultSet.getInt("IdTable");
                Date dateCreation = resultSet.getDate("DateCreation");
                int userCreation = resultSet.getInt("CreerPar");
                Date dateModif = resultSet.getDate("DateModification");

                order = new Order(idCmd, codeClient, nomClient, dateCmd, htCmd, tvaCmd, ttcCmd,
                        payementEtat, etatCmd, idUser, tableNum, dateCreation, userCreation, dateModif, idUser);
            }

            return order;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }



    private static String createOrederBuilder(){
        return "insert into "+ORDER_TABLENAME+" (DateModification, DateCreation, CreerPar, NumBon, Date, Service, " +
                "Etat, EtatPayement, CodeCient, NomClient, MTRemise, MTHT, MTTVA, " +
                "MTTTC, MtTimbre, TableNum, NbrCouvert, NumChambre, NumSejour, " +
                "IdClient, IdTable, MontantTotalPaye, ExoTimbre, ModePaiement, ModeReglement) " +
                "values (?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '1', '1')";
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static int createOrder(Connection connection, Order order){

        if (connection == null || order == null) return 0;

        try {

            @SuppressLint("WrongConstant")
            PreparedStatement statement = connection.prepareStatement(
                    createOrederBuilder(),
                    Statement.RETURN_GENERATED_KEYS);

            //statement.setInt(1, order.getIdCmd());
            statement.setDate(1, new Date(System.currentTimeMillis()));
            statement.setDate(2, new Date(System.currentTimeMillis()));
            statement.setInt(3, order.getUserCreator());

            //TODO Make it
            statement.setString(4, "Bons from tablet");
            statement.setDate(5, new Date(System.currentTimeMillis()));
            statement.setInt(6, 1);
            statement.setInt(7, order.getEtatCmd());
            statement.setBoolean(8, order.getPaymentCmd());
            statement.setString(9, "CODE CLI/"+order.getCodeClient());
            statement.setString(10, order.getNomClient());
            statement.setBigDecimal(11, new BigDecimal(0));
            statement.setBigDecimal(12, order.getHtCmd());
            statement.setBigDecimal(13, order.getTvaCmd());
            statement.setBigDecimal(14, order.getTtcCmd());
            statement.setBigDecimal(15, new BigDecimal(0));
            statement.setString(16, "TABLE ID/"+order.getTable());
            statement.setInt(17, 1);
            statement.setString(18, "Chambre ID/"+order.getTable());
            statement.setString(19, "Sejour ID/"+order.getTable());


            statement.setInt(20, order.getCodeClient());
            statement.setInt(21, order.getTable());
            //statement.setInt(29, null);
            statement.setBigDecimal(22, new BigDecimal(0));
            statement.setBoolean(23, false);
            //statement.setInt(18, order.getIdCaisse());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return 0;
            }
            return order.getIdCmd();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }

    }


    private static String updateOrderBuilder(){
        return "UPDATE "+ORDER_TABLENAME+"\n" +
                //"SET [Effectuer] = ?,\n" + For serve or not
                "SET [Etat] = ?," +
                " [MTHT] = ?,"+
                " [MTTVA] = ?,"+
                " [MTTTC] = ?,"+
                " [EtatPayement] = ?,"+
                " [DateModification] = ?,"+
                " [CreerPar] = ?\n"+
                "Where\n" +
                "Id = ?";
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
            statement.setBoolean(5, order.getPaymentCmd());
            statement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            statement.setInt(7, order.getServerCode());
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

    public static int getLastOrder(Connection connection){

        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllOrderQueryBuilder());
            if (resultSet.next()){

                return resultSet.getInt("Id");

            }

            return 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return 0;
        }

    }
}
