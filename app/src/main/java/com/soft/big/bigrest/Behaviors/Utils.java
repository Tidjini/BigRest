package com.soft.big.bigrest.Behaviors;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Tidjini on 16/11/2017.
 */

public class Utils {

    public enum TableState{
        FREE,
        OCCUPIE,
        SERVED

    }


    public static class Formater{

        public static String getBigDecimalFormat(BigDecimal number, int range){
            number = number.setScale(range, BigDecimal.ROUND_DOWN);

            DecimalFormat decimalFormat = new DecimalFormat();

            decimalFormat.setMaximumFractionDigits(range);

            decimalFormat.setMinimumFractionDigits(0);

            decimalFormat.setGroupingUsed(false);

            return decimalFormat.format(number);
        }
    }
}
