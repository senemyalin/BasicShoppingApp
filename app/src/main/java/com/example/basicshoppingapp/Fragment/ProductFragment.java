package com.example.basicshoppingapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.basicshoppingapp.Activity.MainActivity;
import com.example.basicshoppingapp.Adapter.ProductAdapter;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.R;

import java.util.List;

public class ProductFragment extends Fragment {

    RecyclerView productList;
    ProductAdapter productAdapter;
    public static List<Product> products; //listelenen productlar

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_product, container, false);


        productList = view.findViewById(R.id.recyclerView_product);

        productAdapter = new ProductAdapter(getActivity(), products);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL,false);
        productList.setLayoutManager(gridLayoutManager);
        productList.setAdapter(productAdapter);

        return view;

    }


}