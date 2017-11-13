package com.soft.big.bigrest.Services;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.soft.big.bigrest.Model.DetailsOrder;
import com.soft.big.bigrest.Model.Order;
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

    public static List<DetailsOrder> getDetails(Connection connection) {
        Statement statement;
        List<DetailsOrder> details = new ArrayList<DetailsOrder>();
        DetailsOrder detail;
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
                //todo int image = resultSet.g("Image");
                //DetailsOrder(String platName, String platDescription, double price, int imageId, int total)
                detail = new DetailsOrder(name,  remarque, price, fakeImage(i), 0  );
                //fake image
                i++;
                details.add(detail);
            }

            return details;
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


    private static int fakeImage(int position){
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
