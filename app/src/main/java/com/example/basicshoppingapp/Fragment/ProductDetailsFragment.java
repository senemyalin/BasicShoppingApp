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

import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Adapter.ShoppingCartAdapter;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Class.ProductCount;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.AddShoppingCartResponse;
import com.example.basicshoppingapp.Response.ShoppingCartResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import static com.example.basicshoppingapp.Adapter.ShoppingCartAdapter.url_add_to_shoppingcart;


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
        product_details.setText(product.getDescription());
        Picasso.get().load(product.getImage()).into(product_image);

        add_to_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(LoginActivity.user_ID >0){

                        String product_id = product.getId();
                        new Thread(() -> {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("user_id", String.valueOf(LoginActivity.user_ID));
                            map.put("product_id", product_id);
                            map.put("count", 1 + "");
                            AddShoppingCartResponse res = Helper.httpPost(AddShoppingCartResponse.class, url_add_to_shoppingcart, map);

                            if (res == null) {
                                // give the user an error
                                return;
                            }
                        }).start(); }
            }
        });

        add_to_favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if()
               // add_to_favourites.setImageResource();

            }
        });

        return view;

    }
}