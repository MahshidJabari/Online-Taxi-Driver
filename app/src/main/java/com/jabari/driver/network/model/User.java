package com.jabari.driver.network.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("firstName")
    String FirstName;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("mobile")
    String mobileNum;
    @SerializedName("age")
    String age;
    @SerializedName("meli")
    String meli;
    @SerializedName("sheba")
    String sheba;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMeli() {
        return meli;
    }

    public void setMeli(String meli) {
        this.meli = meli;
    }

    public String getSheba() {
        return sheba;
    }

    public void setSheba(String sheba) {
        this.sheba = sheba;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @SerializedName("identity")
    String identity;
    @SerializedName("address")
    String address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    String id;
    @SerializedName("jwtAccessToken")

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }


    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

}
