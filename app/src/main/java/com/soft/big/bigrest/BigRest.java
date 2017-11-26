package com.soft.big.bigrest;

import android.app.Application;

import com.soft.big.bigrest.Behaviors.TypefaceUtil;

/**
 * Created by Tidjini on 10/11/2017.
 *
 * TODO update table statue; create cmd = etat = 2; close etat = 1 free
 * TODO get product family
 * TODO kill all resources in onDestroy
 * TODO Refresh Button
 * TODO Parameters
 * TODO fragment of error connexion with text and button of config server
 * TODO tell the user to use tablet with message
 */

//Manifest file refer this application class
public class BigRest extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        // Fresco.initialize(this);
        // font from assets: "assets/fonts/Roboto-Regular.ttf
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Exo-Light.ttf");

    }
}
