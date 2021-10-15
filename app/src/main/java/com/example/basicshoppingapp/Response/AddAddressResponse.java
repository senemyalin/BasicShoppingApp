package com.example.basicshoppingapp.Response;

import com.example.basicshoppingapp.Class.Address;
import com.example.basicshoppingapp.Class.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddAddressResponse {

    @SerializedName("message")
    @Expose
    private List<String> message = null;
    @SerializedName("address")
    @Expose
    private List<Address> address = null;

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

}
