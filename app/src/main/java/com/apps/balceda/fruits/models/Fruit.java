package com.apps.balceda.fruits.models;

import java.io.Serializable;

public class Fruit implements Serializable {
    private String name;
    private String image;
    private String price;
    private String priceUnit;


    public Fruit() {
    }

    public Fruit(String name, String image, String price, String priceUnit) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.priceUnit = priceUnit;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
