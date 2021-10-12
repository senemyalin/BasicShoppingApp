package com.example.basicshoppingapp.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Adapter.FavouriteProductsAdapter;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Class.ProductCount;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.FavouriteProductResponse;
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

        SwipeMenuListView FavouriteProductList = (SwipeMenuListView) view.findViewById(R.id.shoppingcart_listview);
        favouriteProductsAdapter = new FavouriteProductsAdapter(getActivity(),favourite_product);
        FavouriteProductList.setAdapter(favouriteProductsAdapter);

        // set creator
        FavouriteProductList.setMenuCreator(creator);

        FavouriteProductList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        remove_fav(favourite_product,position,getActivity(),favouriteProductsAdapter);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


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

            List<Product> newList = res.getProduct();
            if (message.get(0)) {

                activity.runOnUiThread(() ->{
                        list.clear();
                        list.addAll(newList);

                        adapter.notifyDataSetInvalidated();
                        adapter.notifyDataSetChanged();
                });


            }

        }).start();
    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {

            // create "delete" item
            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                    0x3F, 0x25)));
            // set item width
            deleteItem.setWidth(180);
            // set a icon
            deleteItem.setIcon(R.drawable.ic_delete);
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };

    void remove_fav(List<Product> list, int position, Activity activity, BaseAdapter adapter){
        String product_id = list.get(position).getId();

        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            map.put("product_id",product_id);
            FavouriteProductResponse res = Helper.httpPost(FavouriteProductResponse.class, ProductDetailsFragment.url_addFavouriteProduct, map);

            if (res == null) {
                // give the user an error
                return;
            }

            String message = res.getMessage();

            if(message.equals("Product is deleted from FP.")){
                activity.runOnUiThread(()->{
                    adapter.notifyDataSetInvalidated();
                    adapter.notifyDataSetChanged();

                    updateFavouriteProduct(getActivity(),list,adapter);
                    Toast.makeText(getContext(), "Product is deleted from FP", Toast.LENGTH_LONG).show();


                });
            }


        }).start();

    }

}