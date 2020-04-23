package com.jabari.driver.global;

import android.app.Activity;
import android.content.res.Resources;

import com.jabari.driver.R;

import es.dmoral.toasty.Toasty;

public class ExceptionHandler {

    private final Activity myContext;

    public ExceptionHandler(Activity context) {
        myContext = context;
    }

    public void generateError(String err) {
        if (err.equals("connection"))
            Toasty.error(myContext.getBaseContext(), Resources.getSystem().getString(R.string.exp_connection), Toasty.LENGTH_LONG).show();
        if (err.equals("null"))
            Toasty.error(myContext.getBaseContext(), Resources.getSystem().getString(R.string.exp_null), Toasty.LENGTH_LONG).show();
        if (err.equals("update location stopped"))
            Toasty.error(myContext.getBaseContext(), Resources.getSystem().getString(R.string.exp_update_location_stopped), Toasty.LENGTH_SHORT).show();
        if (err.equals("location setting"))
            Toasty.error(myContext.getBaseContext(), Resources.getSystem().getString(R.string.exp_location_setting), Toasty.LENGTH_LONG).show();
    }

    public void generateSuccess(String success) {

        if (success.equals("visibility"))
            Toasty.success(myContext.getBaseContext(), Resources.getSystem().getString(R.string.success_visibility), Toasty.LENGTH_LONG).show();
    }
}
