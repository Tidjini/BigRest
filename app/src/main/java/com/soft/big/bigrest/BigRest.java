package com.soft.big.bigrest;

import android.app.Application;

import com.soft.big.bigrest.Behaviors.TypefaceUtil;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.microsoft.appcenter.push.Push;

/**
 * Created by Tidjini on 10/11/2017.
 *
 * TODO get product family with spinner and filter adapter
 */

//Manifest file refer this application class
public class BigRest extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        // Fresco.initialize(this);
        // font from assets: "assets/fonts/Roboto-Regular.ttf
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Exo-Light.ttf");

        //code-push
        AppCenter.start(this, "62a530f3-a55e-42a4-a4c8-4d6bd5c615c0",
                Analytics.class, Crashes.class, Push.class);
    }
}
