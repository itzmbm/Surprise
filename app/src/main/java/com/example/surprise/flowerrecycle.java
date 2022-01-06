package com.example.surprise;

import java.io.Serializable;

public class flowerrecycle implements Serializable {
    String name;
    int price;
    double rating;
    String brief;
    String description;
    String imageurl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public flowerrecycle(){}



    public flowerrecycle(String name, int price, double rating, String brief, String imageurl,String description) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.brief = brief;
        this.imageurl = imageurl;
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public flowerrecycle(String name, int price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
