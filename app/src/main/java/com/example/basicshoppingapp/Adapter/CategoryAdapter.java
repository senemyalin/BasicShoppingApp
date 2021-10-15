package com.example.basicshoppingapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicshoppingapp.Activity.MainActivity;
import com.example.basicshoppingapp.Class.Category;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Fragment.CategoryFragment;
import com.example.basicshoppingapp.Fragment.ProductFragment;
import com.example.basicshoppingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.basicshoppingapp.Activity.MainActivity.product;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

        List<Category> category;
        LayoutInflater inflater;
        MainActivity mainActivity;



        public CategoryAdapter(MainActivity ctx, List<Category> category){
            this.category= category;
            this.inflater = LayoutInflater.from(ctx);
            this.mainActivity = ctx;
        }

        @NonNull
        @org.jetbrains.annotations.NotNull
        @Override
        public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
            View view= inflater.inflate(R.layout.category_grid_layout,parent,false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CategoryAdapter.ViewHolder holder, int position) {
            holder.category_name.setText(category.get(position).getName());
            holder.category_description.setText(category.get(position).getDescription());
            Picasso.get().load(category.get(position).getLogo()).into(holder.category_image);

            List<Product> chosenProducts = new ArrayList<>();

            holder.btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chosenProducts.clear();
                    for (Product prod : product.getItem()) {

                        if(prod.getCategory().equals(category.get(position).getName())) {
                            chosenProducts.add(prod);
                        }
                    }

                    ProductFragment.products = chosenProducts;
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, new ProductFragment()).addToBackStack("Category Fragment")
                            .commit();
                }
            });

        }

            @Override
            public int getItemCount() {

                return category.size();
            }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView category_name;
            TextView category_description;
            ImageView category_image;
            Button btn_go;

            public ViewHolder(@NonNull View itemView){
                super(itemView);

                category_name = itemView.findViewById(R.id.txt_category_name);
                category_description = itemView.findViewById(R.id.txt_category_description);
                category_image = itemView.findViewById(R.id.img_category_image);
                btn_go = itemView.findViewById(R.id.btn_go);

            }
        }
    }

