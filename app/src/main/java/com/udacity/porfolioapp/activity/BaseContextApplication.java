package com.udacity.porfolioapp.activity;

import android.app.Application;

import com.udacity.porfolioapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Juan PC on 21/01/2017.
 */

public class BaseContextApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
