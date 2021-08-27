package com.example.basicshoppingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter {

    private List<Product> ShoppingCartList;
    LayoutInflater inflater;


    public ShoppingCartAdapter(Context ctx, List<Product> ShoppingCartList) {

        super();

        this.ShoppingCartList = ShoppingCartList;
        this.inflater = LayoutInflater.from(ctx);

    }

    @Override
    public int getCount() {
        return ShoppingCartList.size();
    }

    @Override
    public Object getItem(int position) {
        return ShoppingCartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.fragment_shoppingcart, null);
        }


        TextView name = (TextView) convertView.findViewById(R.id.txt_shoppingcart_name);
        TextView market = (TextView) convertView.findViewById(R.id.txt_shoppingcart_market);
        TextView price = (TextView) convertView.findViewById(R.id.txt_shoppingcart_price);
        ImageView image = (ImageView) convertView.findViewById(R.id.img_shoppingcart_image);

        name.setText(ShoppingCartList.get(position).getName());
        market.setText(ShoppingCartList.get(position).getMarket());
        price.setText(ShoppingCartList.get(position).getPrice());
        Picasso.get().load(ShoppingCartList.get(position).getImage()).into(image);

        return convertView;
    }
}
