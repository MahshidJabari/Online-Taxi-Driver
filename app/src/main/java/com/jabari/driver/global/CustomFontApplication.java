package com.jabari.driver.global;


import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.jabari.driver.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CustomFontApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/iransans200.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()

        );
    }
}
