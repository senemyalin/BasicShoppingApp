package com.example.basicshoppingapp.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicshoppingapp.Class.Product;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShoppingCartAdapter extends BaseAdapter {

    private ArrayList<Product> productShoppingCartList;


    public ShoppingCartAdapter(Context ctx,
                               ArrayList<Product> productShoppingCartList) {

        super();

        this.productShoppingCartList = productShoppingCartList;

    }

    @Override
    public int getCount() {
        return productShoppingCartList.size();
    }

    @Override
    public Object getItem(int position) {
        return productShoppingCartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
