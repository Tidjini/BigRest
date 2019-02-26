package com.soft.big.bigrest.Converters;

import com.soft.big.bigrest.Adapters.TableAdapter;
import com.soft.big.bigrest.Behaviors.Utils;

/**
 * Created by Tidjini on 15/11/2017.
 */

public class TableStateConverter {

    public static Utils.TableState stateConverter(int state){
        if(state == 0) return Utils.TableState.FREE;
        if(state == 1) return Utils.TableState.OCCUPIE;
        return Utils.TableState.FREE;
    }
    public static int stateConverter(Utils.TableState  state){
        if(state == Utils.TableState.OCCUPIE) return 1;
        return 0;
    }
}
