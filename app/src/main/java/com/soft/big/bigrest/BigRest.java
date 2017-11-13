package com.soft.big.bigrest;

import android.app.Application;

import com.soft.big.bigrest.Behaviors.TypefaceUtil;

/**
 * Created by Tidjini on 10/11/2017.
 */

//Manifest file refer this application class
public class BigRest extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        // Fresco.initialize(this);
        // font from assets: "assets/fonts/Roboto-Regular.ttf
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Quicksand-Regular.ttf");

    }
}
