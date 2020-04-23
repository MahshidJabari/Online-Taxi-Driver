package com.jabari.driver.network.model;

import com.google.gson.annotations.SerializedName;

public class Coordinate {
    @SerializedName("latitude")
    String latitude;
    @SerializedName("longitude")
    String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}