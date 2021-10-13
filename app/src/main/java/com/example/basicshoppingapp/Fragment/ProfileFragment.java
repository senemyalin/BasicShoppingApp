package com.example.basicshoppingapp.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Activity.SignUpActivity;
import com.example.basicshoppingapp.Adapter.ShoppingCartAdapter;
import com.example.basicshoppingapp.Class.ProductCount;
import com.example.basicshoppingapp.Class.User;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.GetUserInfoResponse;
import com.example.basicshoppingapp.Response.ShoppingCartResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileFragment extends Fragment {

    Button favourite_products;
    Button addresses;
    Button change_password;
    Button support;
    Button log_out;
    TextView name;
    TextView email;
    TextView phone_number;
    ImageView profile_pic;
    ImageView edit_profile;


    static final String url_getUserInfo="http://192.168.1.104/LoginRegister/getUserInfo.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        favourite_products = view.findViewById(R.id.btn_favourite_products);
        addresses = view.findViewById(R.id.btn_addresses);
        change_password = view.findViewById(R.id.btn_change_password);
        support = view.findViewById(R.id.btn_support);
        log_out = view.findViewById(R.id.btn_logout);

        name = view.findViewById(R.id.txt_username);
        email = view.findViewById(R.id.txt_email);
        phone_number = view.findViewById(R.id.txt_phonenumber);
        profile_pic = view.findViewById(R.id.imageView_profile_pic);
        edit_profile = view.findViewById(R.id.img_editprofile);

        getUserInfo(getActivity());

        favourite_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new FavouriteProductsFragment()).addToBackStack("Profile Fragment")
                        .commit();
            }
        });

        addresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new AddressesFragment()).addToBackStack("Profile Fragment")
                        .commit();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ChangePasswordFragment()).addToBackStack("Profile Fragment")
                        .commit();
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SupportFragment()).addToBackStack("Profile Fragment")
                        .commit();
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.user_ID = 0;
                ShoppingCartFragment.productShoppingCartList.clear();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
               //? Intent intent = new Intent(ProfileFragment.)
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ChangeUserInfoFragment()).addToBackStack("Profile Fragment")
                        .commit();
            }
        });

        return view;
    }

    void getUserInfo(Activity activity){
        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            GetUserInfoResponse res = Helper.httpPost(GetUserInfoResponse.class, url_getUserInfo, map);
            if (res == null) {
                // give the user an error

                return;
            }
            List<Boolean> message = res.getMessage();
            List<User> user_list = res.getUser();
            if (message.get(0)) {
                User user = user_list.get(0);
                activity.runOnUiThread(() -> {
                    name.setText(user.getFullname());
                    email.setText(user.getEmail());
                    phone_number.setText(String.valueOf(user.getPhonenumber()));
                });
            }



        }).start();
    }
}
