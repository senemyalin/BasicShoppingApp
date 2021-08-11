package com.example.basicshoppingapp;

import android.graphics.Bitmap;

import com.example.basicshoppingapp.Enum.Market;

public class Product {

    private Bitmap image;
    private String name;
    private Market market;
    private double price;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Market getCategory() {
        return market;
    }

    public void setCategory(Market market) {
        this.market = market;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
