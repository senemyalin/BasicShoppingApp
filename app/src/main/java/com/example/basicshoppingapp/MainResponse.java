package com.example.basicshoppingapp;

import java.util.List;

import com.example.basicshoppingapp.Class.Category;
import com.example.basicshoppingapp.Class.Market;
import com.example.basicshoppingapp.Class.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MainResponse {

    @SerializedName("product")
    @Expose
    private List<Product> product = null;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("market")
    @Expose
    private List<Market> market = null;

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Market> getMarket() {
        return market;
    }

    public void setMarket(List<Market> market) {
        this.market = market;
    }

}