package com.example.surprise;

public class flowerrecycle {
    String name;
    int price;
    String imageurl;

public flowerrecycle(){}

    public String getImageurl() {
        return imageurl;
    }

    public flowerrecycle(String name, int price, String imageurl) {
        this.name = name;
        this.price = price;
        this.imageurl = imageurl;
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
