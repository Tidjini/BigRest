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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.soft.big.bigrest.Behaviors.Constants.DETAILS_ORDER_TABLENAME;
import static com.soft.big.bigrest.Services.MenuService.getPlatById;

/**
 * Created by Tidjini on 15/11/2017.
 */

public class DetailsOrderService {

    /**
     * Serve for errors source
     */
    public final static String TAG = "DetailsOrderService";


    private static String selectDetailsOrderByOrderIdQueryBuilder(){
        return "SELECT * FROM "+ DETAILS_ORDER_TABLENAME +" WHERE\n" +
                "NumCmd = ?";
    }

    private static String selectDetailsOrderByIdQueryBuilder(){

        return "select * from "+DETAILS_ORDER_TABLENAME+" Where\n" +
                "NbrLigne = ?";
    }

    private static String createDetailsOrderQueryBuilder(){
        return "INSERT INTO "+ DETAILS_ORDER_TABLENAME+" " +
                "(NumCmd, CodeProd, LibeProd, PrixProd, QttProd, TvaArt, MttvaArt,"+
                " RemArt, MtremArt, MtnetArt, TypPrd, Idmag) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        /*return "INSERT INTO "+ DETAILS_ORDER_TABLENAME+" " +
                "(NumCmd, CodeProd, LibeProd,"+
                " TypPrd, Idmag) " +
                " VALUES (?, ?, ?, ?)";*/

    }

    private static String updateDetailsOrderQueryBuilder(){
        return "UPDATE "+DETAILS_ORDER_TABLENAME+"\n" +
                "SET [QttProd] = ?,\n" +
                "    [MtnetArt] = ?\n" +
                "WHERE\n" +
                "NbrLigne = ?";
    }

    /**
     * get details list of given order
     * used when select table with open order
     * @param connection database connection
     * @param idOrder order id
     * @return
     */
    public static List<DetailsOrder> getDetailsOrderByOrderId(Connection connection, String idOrder){
        PreparedStatement statement;
        List<DetailsOrder> detailsOrders = new ArrayList<>();
        DetailsOrder detailOrder;
        try {
            statement = connection.prepareStatement(selectDetailsOrderByOrderIdQueryBuilder());
            statement.setString(1, idOrder);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int nbrLigne = resultSet.getInt("NbrLigne");
                String numCmd = resultSet.getString("NumCmd");
                String codeProd = resultSet.getString("CodeProd");
                String libeProd = resultSet.getString("LibeProd");
                BigDecimal prixProd = resultSet.getBigDecimal("PrixProd");
                BigDecimal qttProd = resultSet.getBigDecimal("QttProd");
                BigDecimal tvaArt = resultSet.getBigDecimal("TvaArt");
                BigDecimal mttvaArt = resultSet.getBigDecimal("MttvaArt");
                BigDecimal remArt = resultSet.getBigDecimal("RemArt");
                BigDecimal mtremArt = resultSet.getBigDecimal("MtremArt");
                BigDecimal mtnetArt = resultSet.getBigDecimal("MtnetArt");
                String typPrd = resultSet.getString("TypPrd");
                int idmag = resultSet.getInt("Idmag");

                detailOrder = new DetailsOrder(nbrLigne, numCmd, codeProd, libeProd, prixProd, qttProd, tvaArt, remArt,typPrd, idmag);
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
        PreparedStatement statement;
        DetailsOrder detailOrder = new DetailsOrder();
        try {
            statement = connection.prepareStatement(selectDetailsOrderByIdQueryBuilder());
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int nbrLigne = resultSet.getInt("NbrLigne");
                String numCmd = resultSet.getString("NumCmd");
                String codeProd = resultSet.getString("CodeProd");
                String libeProd = resultSet.getString("LibeProd");
                BigDecimal prixProd = resultSet.getBigDecimal("PrixProd");
                BigDecimal qttProd = resultSet.getBigDecimal("QttProd");
                BigDecimal tvaArt = resultSet.getBigDecimal("TvaArt");
                BigDecimal mttvaArt = resultSet.getBigDecimal("MttvaArt");
                BigDecimal remArt = resultSet.getBigDecimal("RemArt");
                BigDecimal mtremArt = resultSet.getBigDecimal("MtremArt");
                BigDecimal mtnetArt = resultSet.getBigDecimal("MtnetArt");
                String typPrd = resultSet.getString("TypPrd");
                int idmag = resultSet.getInt("Idmag");

                detailOrder = new DetailsOrder(nbrLigne, numCmd, codeProd, libeProd, prixProd, qttProd, tvaArt, remArt, typPrd, idmag);
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
                    createDetailsOrderQueryBuilder()
                    ,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, detailsOrder.getNumCmd());
            statement.setString(2, detailsOrder.getCodeProd());
            statement.setString(3, detailsOrder.getLibeProd());
            statement.setBigDecimal(4, detailsOrder.getPrixProd());
            statement.setBigDecimal(5, detailsOrder.getQttProd());
            statement.setBigDecimal(6, detailsOrder.getTvaArt());
            statement.setBigDecimal(7, detailsOrder.getMttvaArt());
            statement.setBigDecimal(8, detailsOrder.getRemArt());
            statement.setBigDecimal(9, detailsOrder.getMtremArt());
            statement.setBigDecimal(10, detailsOrder.getMtnetArt());
            statement.setString(11, detailsOrder.getTypPrd());
            statement.setInt(12, detailsOrder.getIdMag());
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
                    updateDetailsOrderQueryBuilder()
                    );
            statement.setBigDecimal(1, detailsOrder.getQttProd());
            statement.setBigDecimal(2, detailsOrder.getMtnetArt());
            statement.setInt(3, detailsOrder.getNbrLigne());
            //Execute update request
            statement.executeUpdate();
            //get updated detail order
            detailsOrderUpdated = getDetailsOrderById(connection, detailsOrder.getNbrLigne());
            return detailsOrderUpdated;
        } catch (SQLException e) {
            return detailsOrderUpdated;
        }

    }

    /**
     * Delete details order from data base
     * @param connection
     * @return
     */
    public static DetailsOrder deleteDetailsOrder(Connection connection, DetailsOrder detailsOrder){

        try {

            @SuppressLint("WrongConstant")
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM "+DETAILS_ORDER_TABLENAME+"\n" +
                            "Where\n" +
                            "NbrLigne = ?"
            );
            statement.setInt(1, detailsOrder.getNbrLigne());
            //Execute Delete request
            statement.executeUpdate();
            return detailsOrder;
        } catch (SQLException e) {
            return null;
        }
    }












}
