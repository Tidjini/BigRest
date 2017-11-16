package com.soft.big.bigrest.Services;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.soft.big.bigrest.Model.DetailsOrder;
import com.soft.big.bigrest.Model.Order;
import com.soft.big.bigrest.Model.Plat;
import com.soft.big.bigrest.Model.Table;
import com.soft.big.bigrest.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.soft.big.bigrest.Behaviors.Constants.SQL_INSERT;

/**
 * Created by Tidjini on 11/11/2017.
 */

public class MenuService {

    public final static String TAG = "UserService";

    private static String selectQueryBuilder(){
        return "select * from Plats";
    }

    private static String selectQueryWithIdBuilder(int id){
        return "select * from Plats\n" +
                "Where\n" +
                "Id = '"+ Integer.toString(id) +"' ";
    }

    public static List<Plat> getPlats(Connection connection) {
        Statement statement;
        List<Plat> plats = new ArrayList<Plat>();
        Plat plat;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQueryBuilder());

            //for fake image
            int i = 0;
            while (resultSet.next()){
                int id = resultSet.getInt("Id");
                int idClass = resultSet.getInt("IdClass");
                String name = resultSet.getString("Name");
                String remarque = resultSet.getString("Remarque");
                double price = resultSet.getDouble("Price");
                //TODO as binary data not resources int image = resultSet.g("Image");
                //Plat(int id, int idClass, Double price, String name, String remarque, int imageResource)
                plat = new Plat(id,  idClass, price, name, remarque, fakeImage(i%8));
                //for fake image
                i++;
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
            ResultSet resultSet = statement.executeQuery(selectQueryBuilder());

            //for fake image
            int i = 0;
            if (resultSet.next()){
                int idClass = resultSet.getInt("IdClass");
                String name = resultSet.getString("Name");
                String remarque = resultSet.getString("Remarque");
                double price = resultSet.getDouble("Price");
                //TODO as binary data not resources int image = resultSet.g("Image");
                //Plat(int id, int idClass, Double price, String name, String remarque, int imageResource)
                plat = new Plat(id,  idClass, price, name, remarque, fakeImage(i%8));
                //for fake image

            }

            return plat;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());

            return null;
        }
    }

    public static int fakeImage(int position){
        //note a single Random object is reused here

        switch (position){
            case 2:
                return R.drawable.p_02;
            case 3:
                return R.drawable.p_03;
            case 4:
                return R.drawable.p_04;
            case 5:
                return R.drawable.p_05;
            case 6:
                return R.drawable.p_06;
            case 7:
                return R.drawable.p_07;
            case 8:
                return R.drawable.p_08;
            default:
                return R.drawable.p_01;

        }

    }
}
