package com.example.basicshoppingapp.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Activity.MainActivity;
import com.example.basicshoppingapp.Adapter.ShoppingCartAdapter;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Class.ProductCount;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.ShoppingCartResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCartFragment extends Fragment {

    ShoppingCartAdapter shoppingCartAdapter;
    public static List<ProductCount> productShoppingCartList = new ArrayList<>(); //listelenen productlar

    static final String url_get_shoppingcart = "http://192.168.1.104/LoginRegister/getShoppingCart.php";
    public static List<Product> user_product = new ArrayList<>();
    public static List<Integer> user_product_count = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        productShoppingCartList = new ArrayList<>();
        View view = inflater.inflate(R.layout.shoppingcart_listview_layout, container, false);

        ListView ShoppingCartList = (ListView) view.findViewById(R.id.shoppingcart_listview);
        shoppingCartAdapter = new ShoppingCartAdapter(getActivity(), productShoppingCartList);
        ShoppingCartList.setAdapter(shoppingCartAdapter);

        Activity activity = getActivity();
        if (LoginActivity.user_ID != 0) {
            MainActivity.addressState.addListener(() -> {
                updateCart(activity, productShoppingCartList, shoppingCartAdapter, String.valueOf(MainActivity.addressState.getItem().getId()));
            });
            updateCart(getActivity(), productShoppingCartList, shoppingCartAdapter, String.valueOf(MainActivity.addressState.getItem().getId()));
        }

        return view;

    }

    public static void updateCart(Activity activity, List<ProductCount> list, BaseAdapter adapter, String address_id) {
        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            map.put("address_id", address_id);
            ShoppingCartResponse res = Helper.httpPost(ShoppingCartResponse.class, url_get_shoppingcart, map);
            if (res == null) {
                // give the user an error

                return;
            }
            List<Boolean> message = res.getMessage();
            List<ProductCount> newList=new ArrayList<>();
            if (message.get(0)) {
                user_product = res.getProduct();
                user_product_count = res.getCount();
                for (int i = 0; i < user_product.size(); i++) {
                    ProductCount productCount = new ProductCount(user_product.get(i), user_product_count.get(i));
                    newList.add(productCount);
                }

            }
            activity.runOnUiThread(() -> {
                list.clear();
                list.addAll(newList);

                adapter.notifyDataSetInvalidated();
                adapter.notifyDataSetChanged();
            });

        }).start();
    }
}
