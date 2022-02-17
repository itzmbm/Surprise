package com.example.surprise;

import java.io.Serializable;

public class orderrecycle implements Serializable {
    int totalprice,quantity;
    String name,imageurl;
    public orderrecycle(){ }

    public orderrecycle(int totalprice, int quantity, String name, String imageurl) {
        this.totalprice = totalprice;
        this.quantity = quantity;
        this.name = name;
        this.imageurl = imageurl;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
