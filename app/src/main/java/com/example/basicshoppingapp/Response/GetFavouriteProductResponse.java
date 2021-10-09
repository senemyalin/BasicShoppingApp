package com.example.basicshoppingapp.Response;

import com.example.basicshoppingapp.Class.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFavouriteProductResponse {

    @SerializedName("product")
    @Expose
    private List<Product> product = null;
    @SerializedName("message")
    @Expose
    private List<Boolean> message = null;

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }


    public List<Boolean> getMessage() {
        return message;
    }

    public void setMessage(List<Boolean> message) {
        this.message = message;
    }
}
