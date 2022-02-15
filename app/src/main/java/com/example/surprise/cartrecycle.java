package com.example.surprise;

public class cartrecycle {
    int price,requiredquantity,totalprice;
    String name,imageurl,uid,cid,pid;

    public cartrecycle(int price, int requiredquantity, int totalprice, String name, String imageurl, String uid, String cid, String pid) {
        this.price = price;
        this.requiredquantity = requiredquantity;
        this.totalprice = totalprice;
        this.name = name;
        this.imageurl = imageurl;
        this.uid = uid;
        this.cid = cid;
        this.pid = pid;
    }

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public cartrecycle(){}

}
