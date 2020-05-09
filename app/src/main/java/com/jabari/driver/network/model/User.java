package com.jabari.driver.network.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("name")
    String name;

    @SerializedName("mobile")
    String mobileNum;
    @SerializedName("age")
    String age;
    @SerializedName("nationalNumber")
    String NationalNumber;
    @SerializedName("shebaNumber")
    String sheba;
    @SerializedName("email")
    String email;
    @SerializedName("idNumber")
    String identity;
    @SerializedName("address")
    String address;
    @SerializedName("fatherName")
    String fatherName;

    @SerializedName("vehicle")
    Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNationalNumber() {
        return NationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.NationalNumber = nationalNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }


    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

}
