package com.example.surprise;

import java.io.Serializable;

public class flowerrecycle implements Serializable {

    int price,quantity;
    double rating;
    String name,brief,description,imageurl,pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public flowerrecycle(){}


    public flowerrecycle(int price, int quantity, double rating, String name, String brief, String description, String imageurl, String pid) {
        this.price = price;
        this.quantity = quantity;
        this.rating = rating;
        this.name = name;
        this.brief = brief;
        this.description = description;
        this.imageurl = imageurl;
        this.pid = pid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
