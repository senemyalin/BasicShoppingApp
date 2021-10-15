package com.example.basicshoppingapp.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;


import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Activity.MainActivity;
import com.example.basicshoppingapp.Adapter.ShoppingCartAdapter;
import com.example.basicshoppingapp.Class.Market;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Class.ProductCount;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.AddShoppingCartResponse;
import com.example.basicshoppingapp.Response.FavouriteProductResponse;
import com.example.basicshoppingapp.Response.ShoppingCartResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.ContentValues.TAG;
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


        final CharSequence[] items = {"Call", "See on the map?", "Send an email", "Visit the Website"};

        product_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Market market = MainActivity.marketsState.getItem().get(Integer.parseInt(product.getMarket_id()));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(product.getMarket())
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0: // Call
                                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                        callIntent.setData(Uri.parse("tel:"+ market.getPhone_number()));
                                        startActivity(callIntent);
                                        break;
                                    case 1: // See on the map
                                        Intent seeIntent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr="+ market.getX_coordinate() +"," + market.getY_coordinate()));
                                        startActivity(seeIntent);
                                        break;
                                    case 2: // Send an email
                                        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                                        sendIntent.setData(Uri.parse("mailto:"));
                                        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {market.getEmail()});
                                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Get Information");
                                        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                            startActivity(sendIntent);
                                        }
                                        break;
                                    case 3: // Visit the Website
                                        String url = market.getWebsite();
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                        break;
                                }

                            }
                        });

                builder.create().show();

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
                    button.setImageResource(R.drawable.ic_empty_star);
                });

            }
            else {
                activity.runOnUiThread(()->{
                    button.setImageResource(R.drawable.ic_fully_star);

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
                add_to_favourites.setImageResource(R.drawable.ic_fully_star);
                });
            }

        }).start();

    }


}