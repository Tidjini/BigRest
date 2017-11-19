package com.soft.big.bigrest.Services;

import android.graphics.Bitmap;
import android.util.Log;

import com.soft.big.bigrest.Model.Plat;
import com.soft.big.bigrest.R;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.soft.big.bigrest.Behaviors.ImageUtils.setImageViewWithByteArray;

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
                Blob imageBlob = resultSet.getBlob("BImage");
                Bitmap image = null;
                if(imageBlob != null)
                    image =  setImageViewWithByteArray(imageBlob);
                plat = new Plat(id,  idClass, price, name, remarque, image, fakeImage(i%8));
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
            ResultSet resultSet = statement.executeQuery(selectQueryWithIdBuilder(id));

            //for fake image
            int i = 0;
            if (resultSet.next()){
                int idClass = resultSet.getInt("IdClass");
                String name = resultSet.getString("Name");
                String remarque = resultSet.getString("Remarque");
                double price = resultSet.getDouble("Price");
                Blob imageBlob = resultSet.getBlob("BImage");
                Bitmap image = null;
                if(imageBlob != null)
                    image =  setImageViewWithByteArray(imageBlob);
                plat = new Plat(id,  idClass, price, name, remarque, image, fakeImage(i%8));
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
