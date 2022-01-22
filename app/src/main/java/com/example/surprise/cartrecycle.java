package com.example.surprise;

public class cartrecycle {
    int price,requiredquantity,totalprice;
    String name,imageurl,uid;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRequiredquantity() {
        return requiredquantity;
    }

    public void setRequiredquantity(int requiredquantity) {
        this.requiredquantity = requiredquantity;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public cartrecycle(int price, int requiredquantity, int totalprice, String name, String imageurl, String uid) {
        this.price = price;
        this.requiredquantity = requiredquantity;
        this.totalprice = totalprice;
        this.name = name;
        this.imageurl = imageurl;
        this.uid = uid;
    }

    public cartrecycle(){}

}
