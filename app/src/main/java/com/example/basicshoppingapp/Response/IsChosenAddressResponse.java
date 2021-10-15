package com.example.basicshoppingapp.Response;

import com.example.basicshoppingapp.Class.Address;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IsChosenAddressResponse {

    @SerializedName("message")
    @Expose
    private List<Boolean> message = null;
    @SerializedName("address")
    @Expose
    private List<Address> address = null;

    public List<Boolean> getMessage() {
        return message;
    }

    public void setMessage(List<Boolean> message) {
        this.message = message;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }
}
