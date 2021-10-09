package com.example.basicshoppingapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Fragment.ProductDetailsFragment;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.FavouriteProductResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
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
                remove_fav(favourite_products,position,ctx);

            }
        });


        return convertView;
    }

    void remove_fav(List<Product> list, int position, Context ctx){
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
                ((Activity)this.inflater.getContext()).runOnUiThread(()->{
                Toast.makeText(ctx, "Product is deleted from FP", Toast.LENGTH_LONG).show();
                });
            }


        }).start();

    }
}
