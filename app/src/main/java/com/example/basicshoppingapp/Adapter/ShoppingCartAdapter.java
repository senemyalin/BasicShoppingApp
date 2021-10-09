package com.example.basicshoppingapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Class.ProductCount;
import com.example.basicshoppingapp.Fragment.ShoppingCartFragment;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.AddShoppingCartResponse;
import com.example.basicshoppingapp.Response.ShoppingCartResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter {

    public List<ProductCount> shoppingCartList;
    LayoutInflater inflater;
    public static final String url_add_to_shoppingcart = "http://192.168.1.104/LoginRegister/addShoppingCart.php";
    Context ctx;
    public ShoppingCartAdapter(Context ctx, List<ProductCount> shoppingCartList) {
        super();
        this.ctx=ctx;

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
        ImageView img_remove = (ImageView) convertView.findViewById(R.id.img_shoppingcart_remove);

        if (!shoppingCartList.isEmpty()) {

            name.setText(shoppingCartList.get(position).getProduct().getName());
            market.setText(shoppingCartList.get(position).getProduct().getMarket());
            price.setText(shoppingCartList.get(position).getProduct().getPrice());
            count.setText(String.valueOf(shoppingCartList.get(position).getCount()));
            Picasso.get().load(shoppingCartList.get(position).getProduct().getImage()).into(image);
        }


        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginActivity.user_ID > 0) {
                    addToCart(position,1);
                }
            }
        });

        img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginActivity.user_ID > 0) {
                    addToCart(position,-1);
                }

            }
        });

        return convertView;
    }

    void addToCart(int position, int count){
        String product_id = shoppingCartList.get(position).getProduct().getId();
        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            map.put("product_id", product_id);
            map.put("count", count + "");
            AddShoppingCartResponse res = Helper.httpPost(AddShoppingCartResponse.class, url_add_to_shoppingcart, map);
            if (res == null) {

                // give the user an error
                return;
            }

            ((Activity)this.inflater.getContext()).runOnUiThread(()->{
                ShoppingCartFragment.updateCart((Activity)ctx, shoppingCartList, this);
            });
        }).start();
    }

}
