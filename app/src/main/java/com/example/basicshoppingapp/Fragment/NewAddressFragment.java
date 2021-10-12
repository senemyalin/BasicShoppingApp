package com.example.basicshoppingapp.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.FavouriteProductResponse;

import java.util.HashMap;
import java.util.List;


public class NewAddressFragment extends Fragment {

    static final String url_addAddress = "http://192.168.1.104/LoginRegister/addAddress.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_address, container, false);

        TextView address_name;
        TextView country;
        TextView city;
        TextView district;
        Button add;

        address_name = view.findViewById(R.id.txt_new_address_name);
        country = view.findViewById(R.id.txt_new_address_country);
        city = view.findViewById(R.id.txt_new_address_city);
        district = view.findViewById(R.id.txt_new_address_district);
        add = view.findViewById(R.id.btn_new_address_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_address(String.valueOf(address_name.getText()),
                                String.valueOf(country.getText()),
                                String.valueOf(city.getText()),
                                String.valueOf(district.getText()),
                                getActivity(),v);
            }
        });

        return view;
    }

    void add_new_address(String address_name,
                         String country, String city, String district, Activity activity, View v){

        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            map.put("address_name",address_name);
            map.put("country", country);
            map.put("city", city);
            map.put("district", district);
            FavouriteProductResponse res = Helper.httpPost(FavouriteProductResponse.class, url_addAddress, map);

            if (res == null) {
                // give the user an error
                return;
            }

            String message = res.getMessage();

            if(message.equals("Address name, Country, City or District can not be empty")){
                activity.runOnUiThread(()->{
                    Toast.makeText(getContext(), "Address name, Country, City or District can not be empty", Toast.LENGTH_LONG).show();
                });
            }
            else if(message.equals("You already have this address name!")){
                activity.runOnUiThread(()->{
                    Toast.makeText(getContext(), "You already have this address name!", Toast.LENGTH_LONG).show();
                });
            }
            else{
                activity.runOnUiThread(()->{
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new AddressesFragment())
                            .commit();
                });
            }



        }).start();

    }
}