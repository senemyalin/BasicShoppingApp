package com.example.basicshoppingapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicshoppingapp.Adapter.ProductAdapter;
import com.example.basicshoppingapp.Adapter.ShoppingCartAdapter;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.R;

import java.util.List;

public class ShoppingCartFragment extends Fragment {

    ShoppingCartAdapter shoppingCartAdapter;
    public static List<Product> productShoppingCartList; //listelenen productlar

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_shoppingcart, container, false);

        ListView ShoppingCartList = (ListView) view.findViewById(R.id.shoppingcart_listview);

        shoppingCartAdapter = new ShoppingCartAdapter(getActivity(), productShoppingCartList);

        ShoppingCartList.setAdapter(shoppingCartAdapter);

        return view;
    }
}
