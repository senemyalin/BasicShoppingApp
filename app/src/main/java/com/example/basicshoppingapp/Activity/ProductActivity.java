package com.example.basicshoppingapp.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicshoppingapp.Adapter.CategoryAdapter;
import com.example.basicshoppingapp.Adapter.ProductAdapter;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    RecyclerView productList;
    ProductAdapter productAdapter;
    public static List<Product> products; //listelenen productlar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);


        productList = findViewById(R.id.recyclerView_product);

        productAdapter = new ProductAdapter(this, products);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,false);
        productList.setLayoutManager(gridLayoutManager);
        productList.setAdapter(productAdapter);

    }




}
