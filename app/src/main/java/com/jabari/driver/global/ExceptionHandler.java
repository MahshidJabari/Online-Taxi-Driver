package com.jabari.driver.global;

import android.app.Activity;

import com.jabari.driver.R;

import es.dmoral.toasty.Toasty;

public class ExceptionHandler {

    private final Activity myContext;

    public ExceptionHandler(Activity context) {
        myContext = context;
    }

    public void generateError(String err) {
        switch (err) {
            case "connection":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_connection), Toasty.LENGTH_LONG).show();
                break;
            case "null":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_null), Toasty.LENGTH_LONG).show();
                break;
            case "update location stopped":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_update_location_stopped), Toasty.LENGTH_SHORT).show();
                break;
            case "location setting":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_location_setting), Toasty.LENGTH_LONG).show();
                break;
            case "invalid phone":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_invalid_phone), Toasty.LENGTH_LONG).show();
                break;
            case "wrong code":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_wrong_code), Toasty.LENGTH_LONG).show();
                break;
            case "wrong phone":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_wrong_phone), Toasty.LENGTH_LONG).show();
                break;
            case "law":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_law), Toasty.LENGTH_LONG).show();
                break;
            case "anonymous":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_anonymous), Toasty.LENGTH_LONG).show();
                break;
            case "null_doc":
                Toasty.error(myContext.getBaseContext(), myContext.getString(R.string.exp_null_doc), Toasty.LENGTH_LONG).show();
                break;
        }
    }

    public void generateSuccess(String success) {

        switch (success) {
            case "visibility":
                Toasty.success(myContext.getBaseContext(), myContext.getString(R.string.success_visibility), Toasty.LENGTH_LONG).show();
                break;
            case "code":
                Toasty.success(myContext.getBaseContext(), myContext.getString(R.string.success_code), Toasty.LENGTH_LONG).show();
                break;
            case "sheba":
                Toasty.success(myContext.getBaseContext(), myContext.getString(R.string.success_sheba), Toasty.LENGTH_LONG).show();
                break;
        }
    }
}
