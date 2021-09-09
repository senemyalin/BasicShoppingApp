package com.example.basicshoppingapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.basicshoppingapp.R;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        favourite_products = favourite_products.findViewById(R.id.btn_favourite_products);
        addresses = addresses.findViewById(R.id.btn_addresses);
        change_password = change_password.findViewById(R.id.btn_change_password);
        support = support.findViewById(R.id.btn_support);
        log_out = log_out.findViewById(R.id.btn_logout);

        name = name.findViewById(R.id.txt_username);
        email = email.findViewById(R.id.txt_email);
        phone_number = phone_number.findViewById(R.id.txt_phonenumber);
        profile_pic = profile_pic.findViewById(R.id.imageView_profile_pic);
        edit_profile = edit_profile.findViewById(R.id.img_editprofile);


        favourite_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.constraintLayout, new FavouriteProductsFragment()).addToBackStack("Profile Fragment")
                        .commit();
            }
        });

        addresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.constraintLayout, new AddressesFragment()).addToBackStack("Profile Fragment")
                        .commit();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.constraintLayout, new ChangePasswordFragment()).addToBackStack("Profile Fragment")
                        .commit();
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.constraintLayout, new SupportFragment()).addToBackStack("Profile Fragment")
                        .commit();
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
