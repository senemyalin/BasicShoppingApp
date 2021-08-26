package com.example.basicshoppingapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.R;
import com.squareup.picasso.Picasso;

public class ProductDetailsFragment extends Fragment {


    public static Product product; //seçilen product

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        TextView product_name;
        TextView product_market;
        TextView product_price;
        TextView product_details;
        ImageView product_image;
        Button add_to_shopping_cart;
        ImageButton add_to_favourites;


        product_name = view.findViewById(R.id.txt_product_details_name);
        product_market = view.findViewById(R.id.txt_product_details_market);
        product_price = view.findViewById(R.id.txt_product_details_price);
        product_image = view.findViewById(R.id.img_product_details_image);
        product_details = view.findViewById(R.id.txt_product_details_details);
        add_to_shopping_cart = view.findViewById(R.id.btn_product_details_add);
        add_to_favourites = view.findViewById(R.id.btn_product_details_fav);

        product_name.setText(product.getName());
        product_market.setText(product.getMarket());
        product_price.setText(product.getPrice());
       //product_details.setText(product.getDetails());
        Picasso.get().load(product.getImage()).into(product_image);


        return view;

    }
}