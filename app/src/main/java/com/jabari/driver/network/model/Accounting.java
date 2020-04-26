package com.jabari.driver.network.model;

import com.google.gson.annotations.SerializedName;

public class Accounting {
    @SerializedName("dayIncome")
    String dayIncome;
    @SerializedName("weekIncome")
    String weekIncome;
    @SerializedName("monthIncome")
    String monthIncome;
    @SerializedName("dayVisible")
    String dayVisible;
    @SerializedName("weekVisible")
    String weekVisible;
    @SerializedName("monthVisible")
    String monthVisible;
    @SerializedName("comissionPercent")
    String commissionPercent;
    @SerializedName("commisionDayLeft")
    String commissionDayLeft;
    @SerializedName("totalIncome")
    String totalIncome;
    @SerializedName("totalCommission")
    String totalCommission;

    public String getDayIncome() {
        return dayIncome;
    }

    public void setDayIncome(String dayIncome) {
        this.dayIncome = dayIncome;
    }

    public String getWeekIncome() {
        return weekIncome;
    }

    public void setWeekIncome(String weekIncome) {
        this.weekIncome = weekIncome;
    }

    public String getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }

    public String getDayVisible() {
        return dayVisible;
    }

    public void setDayVisible(String dayVisible) {
        this.dayVisible = dayVisible;
    }

    public String getWeekVisible() {
        return weekVisible;
    }

    public void setWeekVisible(String weekVisible) {
        this.weekVisible = weekVisible;
    }

    public String getMonthVisible() {
        return monthVisible;
    }

    public void setMonthVisible(String monthVisible) {
        this.monthVisible = monthVisible;
    }

    public String getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(String commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public String getCommissionDayLeft() {
        return commissionDayLeft;
    }

    public void setCommissionDayLeft(String commissionDayLeft) {
        this.commissionDayLeft = commissionDayLeft;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(String totalCommission) {
        this.totalCommission = totalCommission;
    }


}
