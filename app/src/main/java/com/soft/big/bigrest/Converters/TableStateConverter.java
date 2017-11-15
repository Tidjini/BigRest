package com.soft.big.bigrest.Converters;

import com.soft.big.bigrest.Adapters.TableAdapter;

/**
 * Created by Tidjini on 15/11/2017.
 */

public class TableStateConverter {

    public static TableAdapter.State stateConverter(int state){
        if(state == 1) return TableAdapter.State.OCCUPIE;
        if(state == 2) return TableAdapter.State.SERVED;
        return TableAdapter.State.FREE;
    }
}
