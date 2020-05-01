package com.jabari.driver.global;


import androidx.multidex.MultiDexApplication;

import com.jabari.driver.R;

import me.cheshmak.android.sdk.core.Cheshmak;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CustomFontApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        Cheshmak.with(this);
        Cheshmak.initTracker("FsvcK9oB1DEmBQVn3/w7jg==");
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("font/iransans200.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()

        );
    }
}
