package com.example.basicshoppingapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Class.ProductCount;
import com.example.basicshoppingapp.Fragment.ProductDetailsFragment;
import com.example.basicshoppingapp.Fragment.ShoppingCartFragment;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.AddShoppingCartResponse;
import com.example.basicshoppingapp.Response.ShoppingCartResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import static com.example.basicshoppingapp.Adapter.ShoppingCartAdapter.url_add_to_shoppingcart;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<Product> productList;
    LayoutInflater inflater;
    Context ctx;


    public ProductAdapter(Context ctx, List<Product> product){
        this.productList = product;
        this.inflater = LayoutInflater.from(ctx);
        this.ctx=ctx;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.product_grid_layout,parent,false);
        return new ProductAdapter.ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ProductAdapter.ViewHolder holder, int position) {
        holder.product_name.setText(productList.get(position).getName());
        holder.product_market.setText(productList.get(position).getMarket());
        holder.product_price.setText(productList.get(position).getPrice());

        Picasso.get().load(productList.get(position).getImage()).into(holder.product_image);


        holder.product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailsFragment.product = productList.get(position);
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.constraintLayout, new ProductDetailsFragment(),"Product Details Fragment").addToBackStack("Product Fragment")
                        .commit();

            }
        });

        holder.add_to_shopping_cart.setOnClickListener(v -> {

                if(LoginActivity.user_ID >0){
                    String product_id = productList.get(position).getId();

                    new Thread(() -> {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("user_id", String.valueOf(LoginActivity.user_ID));
                        map.put("product_id", product_id);
                        map.put("count", 1 + "");
                        AddShoppingCartResponse res = Helper.httpPost(AddShoppingCartResponse.class, url_add_to_shoppingcart, map);

                        if (res == null) {
                            // give the user an error
                            return;
                        }

                    }).start();
                }


        });

    }

    @Override
    public int getItemCount() {

        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView product_name;
        TextView product_market;
        TextView product_price;
        ImageView product_image;
        Button add_to_shopping_cart;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            product_name = itemView.findViewById(R.id.txt_product_name);
            product_market = itemView.findViewById(R.id.txt_product_market);
            product_price = itemView.findViewById(R.id.txt_product_price);
            product_image = itemView.findViewById(R.id.img_product_image);
            add_to_shopping_cart = itemView.findViewById(R.id.btn_add_to_shopping_cart);

        }
    }
}
