package com.example.basicshoppingapp.Response;

import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Class.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserInfoResponse {

    @SerializedName("user")
    @Expose
    private List<User> user = null;
    @SerializedName("message")
    @Expose
    private List<Boolean> message = null;

    public List<User> getUser() {
        return user;
    }

    public void setProduct(List<User> user) {
        this.user = user;
    }


    public List<Boolean> getMessage() {
        return message;
    }

    public void setMessage(List<Boolean> message) {
        this.message = message;
    }
}
