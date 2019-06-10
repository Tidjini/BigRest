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
import java.sql.Date;
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




    private static String selectDetailsOrderByIdQueryBuilder(){

        return "select * from "+DETAILS_ORDER_TABLENAME+" Where\n" +
                "Id = ?";
    }



    private static String updateDetailsOrderQueryBuilder(){
        return "UPDATE "+DETAILS_ORDER_TABLENAME+"\n" +
                "SET [Qte] = ?,\n" +
                "    [MtTotal] = ?,\n" +
                "    [Imprimante] = ?\n" +
                "WHERE\n" +
                "Id = ?";
    }


    private static String selectDetailsOrderByOrderIdQueryBuilder(){
        return "SELECT * FROM "+ DETAILS_ORDER_TABLENAME +" WHERE\n" +
                "[IdBon] = ?";
    }
    /**
     * get details list of given order
     * used when select table with open order
     * @param connection database connection
     * @param idOrder order id
     * @return
     */
    public static List<DetailsOrder> getDetailsOrderByOrderId(Connection connection, int idOrder){
        PreparedStatement statement;
        List<DetailsOrder> detailsOrders = new ArrayList<>();
        DetailsOrder detailOrder;
        try {
            statement = connection.prepareStatement(selectDetailsOrderByOrderIdQueryBuilder());
            statement.setInt(1, idOrder);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int nbrLigne = resultSet.getInt("Id");
                int numCmd = resultSet.getInt("IdBon");
                int codeProd = resultSet.getInt("IdProduit");
                String libeProd = resultSet.getString("ProduitDesignation");
                BigDecimal prixProd = resultSet.getBigDecimal("PrixVProduit");
                BigDecimal qttProd = resultSet.getBigDecimal("Qte");
                BigDecimal tvaArt = resultSet.getBigDecimal("TvaProduit");
                BigDecimal mttvaArt = resultSet.getBigDecimal("MtTotal");
                BigDecimal remArt = resultSet.getBigDecimal("Remise");
               // BigDecimal mtremArt = resultSet.getBigDecimal("MtremArt");
                BigDecimal mtnetArt = resultSet.getBigDecimal("MtTotal");
                //String typPrd = resultSet.getString("TypPrd");
                //int idmag = resultSet.getInt("Idmag");
                String imprimente = resultSet.getString("Imprimante");

                detailOrder = new DetailsOrder(nbrLigne, numCmd, codeProd, libeProd, prixProd, qttProd, tvaArt, remArt, codeProd, imprimente);

                detailOrder.getCodeProd();
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
                int nbrLigne = resultSet.getInt("Id");
                int numCmd = resultSet.getInt("IdBon");
                int codeProd = resultSet.getInt("IdProduit");
                String libeProd = resultSet.getString("ProduitDesignation");
                BigDecimal prixProd = resultSet.getBigDecimal("PrixVProduit");
                BigDecimal qttProd = resultSet.getBigDecimal("Qte");
                BigDecimal tvaArt = resultSet.getBigDecimal("TvaProduit");
                BigDecimal mttvaArt = resultSet.getBigDecimal("MtTotal");
                BigDecimal remArt = resultSet.getBigDecimal("Remise");
                // BigDecimal mtremArt = resultSet.getBigDecimal("MtremArt");
                BigDecimal mtnetArt = resultSet.getBigDecimal("MtTotal");
                String imprimente = resultSet.getString("Imprimante");
                //String typPrd = resultSet.getString("TypPrd");
                //int idmag = resultSet.getInt("Idmag");

                detailOrder = new DetailsOrder(nbrLigne, numCmd, codeProd, libeProd, prixProd, qttProd, tvaArt, remArt, codeProd, imprimente);
            }

            return detailOrder;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
            return null;
        }
    }





    private static String createDetailsOrderQueryBuilder(){
        return "INSERT INTO "+ DETAILS_ORDER_TABLENAME+" " +
                "(DateModification, DateCreation, CreerPar, IdProduit, IdBon, ProduitDesignation, TvaProduit,"+
                " PrixVProduit, Qte, QteEnStock, Remise, Supplement, MtTotal, MtSupplement, Imprimante, IsPrinted, Modified, ModifierPar) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

        /*return "INSERT INTO "+ DETAILS_ORDER_TABLENAME+" " +
                "(NumCmd, CodeProd, LibeProd,"+
                " TypPrd, Idmag) " +
                " VALUES (?, ?, ?, ?)";*/

    }
    /**
     * create Detail order
     * when click on order button we fetch all details and create them using this method
     * @param connection
     * @param detailsOrder
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static int createDetailsOrder(Connection connection, DetailsOrder detailsOrder, int userId){

        if (connection == null || detailsOrder == null) return 0;

        try {

            @SuppressLint("WrongConstant")
            PreparedStatement statement = connection.prepareStatement(
                    createDetailsOrderQueryBuilder()
                    ,
                    Statement.RETURN_GENERATED_KEYS);
            //statement.setInt(1, detailsOrder.getNbrLigne());
            statement.setDate(1, new Date(System.currentTimeMillis()));
            statement.setDate(2, new Date(System.currentTimeMillis()));
            //set user id
            statement.setInt(3, userId);
            statement.setInt(4, detailsOrder.getCodeProd());
            statement.setInt(5, detailsOrder.getNumCmd());
            statement.setString(6, detailsOrder.getLibeProd());
            statement.setBigDecimal(7, detailsOrder.getTvaArt());
            statement.setBigDecimal(8, detailsOrder.getPrixProd());
            statement.setBigDecimal(9, detailsOrder.getQttProd());
            statement.setBigDecimal(10, detailsOrder.getQttProd());
            statement.setBigDecimal(11, detailsOrder.getRemArt());
            statement.setString(12, "");
            statement.setBigDecimal(13, detailsOrder.getMtnetArt());
            statement.setBigDecimal(14, new BigDecimal(0));
            statement.setString(15,  detailsOrder.getImprimente());
            statement.setBoolean(16, false);
            statement.setBoolean(17, false);
            statement.setInt(18, userId);

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
            Log.e(TAG, e.getMessage());
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
            statement.setString(3,  detailsOrder.getImprimente());
            statement.setInt(4, detailsOrder.getNbrLigne());

            //Execute update request
            statement.executeUpdate();
            //get updated detail order
            detailsOrderUpdated = getDetailsOrderById(connection, detailsOrder.getNbrLigne());
            return detailsOrderUpdated;
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
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
            Log.e(TAG, e.getMessage());
            return null;
        }
    }












}
