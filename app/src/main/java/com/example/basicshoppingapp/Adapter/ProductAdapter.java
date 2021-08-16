package com.example.basicshoppingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicshoppingapp.Activity.ProductActivity;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<Product> product;
    LayoutInflater inflater;



    public ProductAdapter(Context ctx, List<Product> product){
        this.product= product;
        this.inflater = LayoutInflater.from(ctx);
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
        holder.product_name.setText(product.get(position).getName());
        holder.product_market.setText(product.get(position).getMarket());
        holder.product_price.setText(product.get(position).getPrice());

        Picasso.get().load(product.get(position).getImage()).into(holder.product_image);

        holder.add_to_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(ProductAdapter.this, ProductActivity.class);
                //startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return product.size();
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
