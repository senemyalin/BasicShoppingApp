package com.example.basicshoppingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Class.ProductCount;
import com.example.basicshoppingapp.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter {

    List<ProductCount> shoppingCartList;
    LayoutInflater inflater;


    public ShoppingCartAdapter(Context ctx, List<ProductCount> shoppingCartList) {

        super();

        this.shoppingCartList = shoppingCartList;
        this.inflater = LayoutInflater.from(ctx);

    }

    @Override
    public int getCount() {
        return shoppingCartList.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingCartList.get(position);
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
        TextView count = (TextView) convertView.findViewById(R.id.txt_shoppingcart_count);
        ImageView image = (ImageView) convertView.findViewById(R.id.img_shoppingcart_image);
        ImageView img_add = (ImageView) convertView.findViewById(R.id.img_shoppingcart_add);
        ImageView img_remove= (ImageView) convertView.findViewById(R.id.img_shoppingcart_remove);

        name.setText(shoppingCartList.get(position).getProduct().getName());
        market.setText(shoppingCartList.get(position).getProduct().getMarket());
        price.setText(shoppingCartList.get(position).getProduct().getPrice());
        count.setText(String.valueOf(shoppingCartList.get(position).getCount()));
        Picasso.get().load(shoppingCartList.get(position).getProduct().getImage()).into(image);

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCartList.get(position).addCount(1);
                count.setText(String.valueOf(shoppingCartList.get(position).getCount()));
                notifyDataSetChanged();
            }
        });

        img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shoppingCartList.get(position).getCount()>1) {
                    shoppingCartList.get(position).removeCount(1);
                    count.setText(String.valueOf(shoppingCartList.get(position).getCount()));
                }
                else{
                    shoppingCartList.remove(position);
                }

                notifyDataSetChanged();

            }
        });

        return convertView;
    }
}
