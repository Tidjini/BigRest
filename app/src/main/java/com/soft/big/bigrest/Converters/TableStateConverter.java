package com.soft.big.bigrest.Converters;

import com.soft.big.bigrest.Adapters.TableAdapter;
import com.soft.big.bigrest.Behaviors.Utils;

/**
 * Created by Tidjini on 15/11/2017.
 */

public class TableStateConverter {

    public static Utils.TableState stateConverter(int state){
        if(state == 1) return Utils.TableState.OCCUPIE;
        if(state == 2) return Utils.TableState.SERVED;
        return Utils.TableState.FREE;
    }
}
