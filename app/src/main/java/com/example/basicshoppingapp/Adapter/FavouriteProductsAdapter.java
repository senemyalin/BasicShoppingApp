package com.example.basicshoppingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouriteProductsAdapter extends BaseAdapter {

    public List<Product> favourite_products;
    LayoutInflater inflater;
    Context ctx;

    public static final String url_getFavouriteProduct = "http://192.168.1.104/LoginRegister/getFavouriteProduct.php";

    public FavouriteProductsAdapter(Context ctx, List<Product> favourite_products){
        super();
        this.ctx=ctx;
        this.favourite_products = favourite_products;
        this.inflater = LayoutInflater.from(ctx);

    }

    @Override
    public int getCount() {
        return favourite_products.size();
    }

    @Override
    public Object getItem(int position) {
        return favourite_products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_favourite_products, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.txt_favourite_product_name);
        TextView details = (TextView) convertView.findViewById(R.id.txt_favourite_product_details);
        TextView price = (TextView) convertView.findViewById(R.id.txt_favourite_product_price);
        ImageView image = (ImageView) convertView.findViewById(R.id.img_favourite_products);
        ImageView img_add_remove = (ImageView) convertView.findViewById(R.id.img_favourite_products_add);

        if (!favourite_products.isEmpty()) {

            name.setText(favourite_products.get(position).getName());
            details.setText(favourite_products.get(position).getDescription());
            price.setText(favourite_products.get(position).getPrice());
            Picasso.get().load(favourite_products.get(position).getImage()).into(image);
        }

        img_add_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        return convertView;
    }

    void remove_fav(){

    }
}
