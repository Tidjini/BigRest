package com.soft.big.bigrest.Services;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.soft.big.bigrest.Adapters.TableAdapter;
import com.soft.big.bigrest.Model.DetailsOrder;
import com.soft.big.bigrest.Model.Order;
import com.soft.big.bigrest.Model.Plat;
import com.soft.big.bigrest.Model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.soft.big.bigrest.Services.MenuService.getPlatById;

/**
 * Created by Tidjini on 15/11/2017.
 */

public class DetailsOrderService {

    /**
     * Serve for errors source
     */
    public final static String TAG = "DetailsOrderService";


    private static String selectDetailsOrderByOrderIdQueryBuilder(int idOrder){

        return "select * from DetailsCmd Where " +
                "IdCmd = '"+ Integer.toString(idOrder) +"' ";
    }

    private static String selectDetailsOrderByIdQueryBuilder(int id){

        return "select * from DetailsCmd Where\n" +
                "Id = '"+ Integer.toString(id) +"' ";
    }

    /**
     * get details list of given order
     * used when select table with open order
     * @param connection database connection
     * @param idOrder order id
     * @return
     */
    public static List<DetailsOrder> getDetailsOrderByOrderId(Connection connection, int idOrder){
        Statement statement;
        List<DetailsOrder> detailsOrders = new ArrayList<>();
        DetailsOrder detailOrder;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectDetailsOrderByOrderIdQueryBuilder(idOrder));
            while (resultSet.next()){
                int id = resultSet.getInt("Id");
                int idPlat = resultSet.getInt("IdPlat");
                int total = resultSet.getInt("Total");

                //get plat information
                Plat plat = getPlatById(connection, idPlat);
                //set information for custom constructor
                detailOrder = new DetailsOrder(id, plat.getName(), total, plat.getPrice());
                detailsOrders.add(detailOrder);
            }

            return detailsOrders;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }

    /**
     * get detail order
     * used after updating data
     * @param connection
     * @param id
     * @return
     */
    public static DetailsOrder getDetailsOrderById(Connection connection, int id){
        Statement statement;
        DetailsOrder detailOrder = new DetailsOrder();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectDetailsOrderByIdQueryBuilder(id));
            if (resultSet.next()){
                int idPlat = resultSet.getInt("IdPlat");
                int total = resultSet.getInt("Total");

                //get plat information
                Plat plat = getPlatById(connection, idPlat);
                //set information for custom constructor
                detailOrder = new DetailsOrder(id, plat.getName(), total, plat.getPrice());

            }

            return detailOrder;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }


    /**
     * create Detail order
     * when click on order button we fetch all details and create them using this method
     * @param connection
     * @param detailsOrder
     * @return
     */

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static int createDetailsOrder(Connection connection, DetailsOrder detailsOrder){

        if (connection == null || detailsOrder == null) return 0;

        try {

            @SuppressLint("WrongConstant")
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO DetailsCmd (IdCmd, IdPlat, Total, TotalPrice) " +
                            "VALUES ('"+detailsOrder.getCmdId()+"', '"+detailsOrder.getPlatId()+"', '"+detailsOrder.getTotal()+"', '"+detailsOrder.getTotalHt()+"')"
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

    /**
     * Update detail order
     * @param connection
     * @param detailsOrder
     * @return
     */
    public static DetailsOrder updateDetailsOrder(Connection connection, DetailsOrder detailsOrder){
        DetailsOrder detailsOrderUpdated = new DetailsOrder();
        try {

            @SuppressLint("WrongConstant")
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE DetailsCmd\n" +
                            "SET Total = '"+detailsOrder.getTotal()+"',\n" +
                            "    TotalPrice = '"+detailsOrder.getTotalHt()+"',\n" +
                            "Where\n" +
                            "Id = '"+ Integer.toString(detailsOrder.getId()) +"' "
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
            //get updated detail order
            detailsOrderUpdated = getDetailsOrderById(connection, detailsOrder.getId());
            return detailsOrderUpdated;
        } catch (SQLException e) {
            return detailsOrderUpdated;
        }

    }

    /**
     * Delete details order from data base
     * @param connection
     * @param detailsOrder
     * @return
     */
    public static boolean deleteDetailsOrder(Connection connection, DetailsOrder detailsOrder){

        try {

            @SuppressLint("WrongConstant")
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM DetailsCmd\n" +
                            "Where\n" +
                            "Id = '"+ Integer.toString(detailsOrder.getId()) +"' "
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

            //Execute Delete request
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }












}
