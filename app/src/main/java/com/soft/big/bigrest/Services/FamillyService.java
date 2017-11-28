package com.soft.big.bigrest.Services;

import android.graphics.Bitmap;
import android.util.Log;

import com.soft.big.bigrest.Model.Category;
import com.soft.big.bigrest.Model.Plat;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.soft.big.bigrest.Behaviors.Constants.FAMILLY_TABLENAME;
import static com.soft.big.bigrest.Behaviors.ImageUtils.setImageViewWithByteArray;

/**
 * Created by Tidjini on 28/11/2017.
 */

public class FamillyService {

    public final static String TAG = "FamillyService";

    private static String selectQueryBuilder(){
        return "select * from "+ FAMILLY_TABLENAME +" ";
    }

    public static List<Category> getFammillies(Connection connection) {
        Statement statement;
        List<Category> categories = new ArrayList<>();
        Category category;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQueryBuilder());

            while (resultSet.next()){
                int id = resultSet.getInt("IdFam");
                String libelle = resultSet.getString("LibFam");
                int impCuis = resultSet.getInt("ImpCuis");
                int tFam = resultSet.getInt("TFam");


                category = new Category(id, libelle, impCuis, tFam);
                categories.add(category);
            }

            return categories;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());

            return null;
        }
    }

}
