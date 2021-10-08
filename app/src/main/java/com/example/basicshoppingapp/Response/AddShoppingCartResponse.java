package com.example.basicshoppingapp.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddShoppingCartResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("count")
    @Expose
    private Integer count;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
