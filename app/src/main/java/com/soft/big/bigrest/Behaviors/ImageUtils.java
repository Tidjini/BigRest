package com.soft.big.bigrest.Behaviors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.sql.Blob;
import java.sql.SQLException;

/**
 * Created by Tidjini on 18/11/2017.
 */

public class ImageUtils {

    /**
     * get blob image and convert into bitmap image
     * @param imageBlob
     * @throws SQLException
     */
    public static Bitmap setImageViewWithByteArray(Blob imageBlob) throws SQLException {
        //Blob imageBlob = resultSet.getBlob(yourBlobColumnIndex);
        byte[] data = imageBlob.getBytes(1, (int) imageBlob.length());
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap;
    }
}
