package com.jabari.driver.global;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_TOKEN = "token";
    private static final String IS_USER = "user";
    private static final String PREF_NAME = "his-welcome";
    private static final String IS_Phone = "phone";
    private static final String Doc_Meli = "meli";
    private static final String Doc_ElectricBill = "electric_bill";
    private static final String Doc_WaterBill = "water_bill";
    private static final String Doc_Licence = "licence";
    private static final String Doc_Id = "id";
    private static final String Doc_Military = "military";
    private static final String Doc_GreenPaper = "greenPaper";
    private static final String Sheba = "sheba";

    public PrefManager(Context context) {

        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getTOken() {
        String result = "";
        result = pref.getString(IS_TOKEN, result);

        return result;

    }

    public void setToken(String token) {

        editor.putString(IS_TOKEN, token);
        editor.commit();
    }
    public String getSheba() {
        String result = "";
        result = pref.getString(Sheba, result);
        return result;

    }

    public void setSheba(String sheba) {
        editor.putString(Sheba, sheba);
        editor.commit();
    }
    public String getDocMeli() {
        String result = "";
        result = pref.getString(Doc_Meli, result);
        return result;

    }

    public void setDocMeli(String meli) {
        editor.putString(Doc_Meli, meli);
        editor.commit();
    }

    public String getId() {
        String result = "";
        result = pref.getString(Doc_Id, result);
        return result;

    }

    public void setId(String id) {
        editor.putString(Doc_Id, id);
        editor.commit();
    }

    public String getLicence() {
        String result = "";
        result = pref.getString(Doc_Licence, result);
        return result;

    }

    public void setLicence(String id) {
        editor.putString(Doc_Licence, id);
        editor.commit();
    }

    public String getElectricBill() {
        String result = "";
        result = pref.getString(Doc_ElectricBill, result);
        return result;

    }

    public void setElectricBill(String electric) {
        editor.putString(Doc_ElectricBill, electric);
        editor.commit();
    }

    public String getWaterBill() {
        String result = "";
        result = pref.getString(Doc_WaterBill, result);
        return result;

    }

    public void setWaterBill(String water) {
        editor.putString(Doc_WaterBill, water);
        editor.commit();
    }

    public String getMilitary() {
        String result = "";
        result = pref.getString(Doc_Military, result);
        return result;

    }

    public void setMilitary(String military) {
        editor.putString(Doc_Military, military);
        editor.commit();
    }

    public String getGreenPaper() {
        String result = "";
        result = pref.getString(Doc_GreenPaper, result);
        return result;

    }

    public void setGreenPaper(String greenPaper) {
        editor.putString(Doc_GreenPaper, greenPaper);
        editor.commit();
    }


    public void removeToken() {
        editor.remove(IS_TOKEN);
        editor.commit();
    }

    public void setPhoneNum(String emailVal) {

        editor.putString(IS_Phone, emailVal);
        editor.commit();
    }

    public String getPhoneNum() {

        String result = "";
        return pref.getString(IS_Phone, result);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void removeUser() {
        editor.remove(IS_USER);
        editor.commit();
    }


}
