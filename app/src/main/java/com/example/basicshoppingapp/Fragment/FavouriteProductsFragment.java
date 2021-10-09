package com.example.basicshoppingapp.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Adapter.FavouriteProductsAdapter;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Class.ProductCount;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.GetFavouriteProductResponse;
import com.example.basicshoppingapp.Response.ShoppingCartResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavouriteProductsFragment extends Fragment {

    FavouriteProductsAdapter favouriteProductsAdapter;
    public static List<Product> favourite_product = new ArrayList<>();
    public static final String url_getFavouriteProduct = "http://192.168.1.104/LoginRegister/getFavouriteProduct.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shoppingcart_listview_layout, container, false);

        ListView FavouriteProductList = (ListView) view.findViewById(R.id.shoppingcart_listview);
        favouriteProductsAdapter = new FavouriteProductsAdapter(getActivity(),favourite_product);
        FavouriteProductList.setAdapter(favouriteProductsAdapter);

        if (LoginActivity.user_ID != 0) {
            updateFavouriteProduct(getActivity(),favourite_product,favouriteProductsAdapter);
        }


        return view;
    }

    public static void updateFavouriteProduct(Activity activity, List<Product> list, BaseAdapter adapter){
        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            GetFavouriteProductResponse res = Helper.httpPost(GetFavouriteProductResponse.class, url_getFavouriteProduct, map);


            if (res == null) {
                // give the user an error
                return;
            }

            List<Boolean> message = res.getMessage();

            if (message.get(0)) {
                activity.runOnUiThread(list::clear);
                activity.runOnUiThread(() ->
                        favourite_product = res.getProduct()
                );


            }

        }).start();
    }
}