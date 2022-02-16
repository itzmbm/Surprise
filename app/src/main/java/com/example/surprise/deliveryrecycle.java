package com.example.surprise;

import java.io.Serializable;
import java.util.Date;

public class deliveryrecycle implements Serializable
{
    String name,address,did,uid;
    int mobileno,pincode,total;
    Date deliveryByDateTime,orderedDateTime;

    public deliveryrecycle(){}

    public deliveryrecycle(String name, String address, String did, String uid, int mobileno, int pincode, int total, Date deliveryByDateTime, Date orderedDateTime) {
        this.name = name;
        this.address = address;
        this.did = did;
        this.uid = uid;
        this.mobileno = mobileno;
        this.pincode = pincode;
        this.total = total;
        this.deliveryByDateTime = deliveryByDateTime;
        this.orderedDateTime = orderedDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getMobileno() {
        return mobileno;
    }

    public void setMobileno(int mobileno) {
        this.mobileno = mobileno;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getDeliveryByDateTime() {
        return deliveryByDateTime;
    }

    public void setDeliveryByDateTime(Date deliveryByDateTime) {
        this.deliveryByDateTime = deliveryByDateTime;
    }

    public Date getOrderedDateTime() {
        return orderedDateTime;
    }

    public void setOrderedDateTime(Date orderedDateTime) {
        this.orderedDateTime = orderedDateTime;
    }
}
