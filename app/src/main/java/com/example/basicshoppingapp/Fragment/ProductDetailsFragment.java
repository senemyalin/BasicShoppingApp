package com.example.basicshoppingapp.Fragment;

import android.app.Activity;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.basicshoppingapp.Response.FavouriteProductResponse;
import com.example.basicshoppingapp.Response.ShoppingCartResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import static com.example.basicshoppingapp.Adapter.ShoppingCartAdapter.url_add_to_shoppingcart;


public class ProductDetailsFragment extends Fragment {


    public static Product product; //seçilen product

    public static final String url_addFavouriteProduct = "http://192.168.1.104/LoginRegister/addFavouriteProduct.php";
    static final String url_isFavouriteProduct = "http://192.168.1.104/LoginRegister/isFavouriteProduct.php";


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

        isFavouriteProduct(add_to_favourites,getActivity());

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

                add_remove_fav(add_to_favourites,getActivity());

            }
        });

        return view;

    }

    void add_remove_fav(ImageButton button, Activity activity){

        String product_id = product.getId();

        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            map.put("product_id",product_id);
            FavouriteProductResponse res = Helper.httpPost(FavouriteProductResponse.class, url_addFavouriteProduct, map);

            if (res == null) {
                // give the user an error
                return;
            }

            String message = res.getMessage();

            if(message.equals("Product is deleted from FP.")){

                activity.runOnUiThread(()->{
                    button.setImageResource(R.drawable.heart_icon_3351__1_);
                });

            }
            else {
                activity.runOnUiThread(()->{
                    button.setImageResource(R.drawable.heart_png_44632__1_);

                });
            }


        }).start();
    }

    void isFavouriteProduct(ImageButton add_to_favourites, Activity activity){
        String product_id = product.getId();

        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            map.put("product_id",product_id);
            FavouriteProductResponse res = Helper.httpPost(FavouriteProductResponse.class, url_isFavouriteProduct, map);

            String message = res.getMessage();

            if(message.equals("true")){
                activity.runOnUiThread(()->{
                add_to_favourites.setImageResource(R.drawable.heart_png_44632__1_);
                });
            }

        }).start();

    }


}