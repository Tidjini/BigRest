package com.soft.big.bigrest.Services;

import android.graphics.Bitmap;
import android.util.Log;

import com.soft.big.bigrest.Model.Plat;
import com.soft.big.bigrest.R;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.soft.big.bigrest.Behaviors.Constants.ARTICLES_TABLENAME;
import static com.soft.big.bigrest.Behaviors.ImageUtils.setImageViewWithByteArray;

/**
 * Created by Tidjini on 11/11/2017.
 */

public class MenuService {

    public final static String TAG = "UserService";

    private static String selectQueryBuilder(){
        return "select * from "+ARTICLES_TABLENAME;
    }

    private static String selectQueryWithIdBuilder(int id){
        return "select * from "+ARTICLES_TABLENAME+"\n" +
                "Where\n" +
                "IdProd = '"+ id +"' ";
    }

    public static List<Plat> getPlats(Connection connection) {
        Statement statement;
        List<Plat> plats = new ArrayList<Plat>();
        Plat plat;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQueryBuilder());

            //for fake image
            while (resultSet.next()){
                int id = resultSet.getInt("Id");
                int famProd = resultSet.getInt("IdFamille");
                int typeProd = resultSet.getInt("Tprod");
                String name = resultSet.getString("Designation");
                String refProd = resultSet.getString("RefProduit");
                String famProduit = resultSet.getString("FamProd");
                BigDecimal tva = resultSet.getBigDecimal("TvaValue");
                BigDecimal prixProdVente = resultSet.getBigDecimal("PrixVprod");
                Blob imageBlob = resultSet.getBlob("Image");
                Bitmap image = null;
                if(imageBlob != null)
                    image =  setImageViewWithByteArray(imageBlob);
                //public Plat(  Bitmap imageProd) {
                plat = new Plat(id,  name, refProd, famProd, typeProd, tva, prixProdVente, image);
                //for fake image
                plats.add(plat);
            }

            return plats;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());

            return null;
        }
    }


    public static Plat getPlatById(Connection connection, int id) {
        Statement statement;
        Plat plat = new Plat();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQueryWithIdBuilder(id));

            //for fake image
            int i = 0;
            if (resultSet.next()){
                //int id = resultSet.getInt("Id");
                int famProd = resultSet.getInt("IdFamille");
                int typeProd = resultSet.getInt("Tprod");
                String name = resultSet.getString("Designation");
                String refProd = resultSet.getString("RefProduit");
                String famProduit = resultSet.getString("FamProd");
                BigDecimal tva = resultSet.getBigDecimal("TvaValue");
                BigDecimal prixProdVente = resultSet.getBigDecimal("PrixVprod");
                Blob imageBlob = resultSet.getBlob("Image");
                Bitmap image = null;
                if(imageBlob != null)
                    image =  setImageViewWithByteArray(imageBlob);
                //public Plat(  Bitmap imageProd) {
                plat = new Plat(id,  name, refProd, famProd, typeProd, tva, prixProdVente, image);
                //for fake image

            }

            return plat;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());

            return null;
        }
    }


}
