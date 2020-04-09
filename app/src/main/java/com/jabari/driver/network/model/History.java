package com.jabari.driver.network.model;

public class History {

    private String cash;
    private Boolean returned;
    private Boolean pay_right;
    private String payment_company;
    private String receiver_address;
    private String sender_address;

    public String getReciever_address() {
        return receiver_address;
    }

    public void setReciever_address(String reciever_address) {
        this.receiver_address = reciever_address;
    }

    public String getSender_address() {
        return sender_address;
    }

    public void setSender_address(String sender_address) {
        this.sender_address = sender_address;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public Boolean getPay_right() {
        return pay_right;
    }

    public void setPay_right(Boolean pay_right) {
        this.pay_right = pay_right;
    }

    public String getPayment_company() {
        return payment_company;
    }

    public void setPayment_company(String payment_company) {
        this.payment_company = payment_company;
    }
}
