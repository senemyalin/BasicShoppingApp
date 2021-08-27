package com.example.basicshoppingapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.basicshoppingapp.Adapter.ShoppingCartAdapter;
import com.example.basicshoppingapp.Class.ProductCount;
import com.example.basicshoppingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends Fragment {

    ShoppingCartAdapter shoppingCartAdapter;
    public static List<ProductCount> productShoppingCartList = new ArrayList<>(); //listelenen productlar

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.shoppingcart_listview_layout, container, false);

        if( productShoppingCartList.size() != 0) {
            ListView ShoppingCartList = (ListView) view.findViewById(R.id.shoppingcart_listview);

            shoppingCartAdapter = new ShoppingCartAdapter(getActivity(), productShoppingCartList);

            ShoppingCartList.setAdapter(shoppingCartAdapter);

            return view;
        }
        else return null;

    }
}
