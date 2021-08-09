package com.example.basicshoppingapp;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    List<String> category_names;
    List<Integer> category_images;
    List<String> category_descriptions;
    LayoutInflater inflater;


    public CategoryAdapter(Context ctx, List<String> category_names, List<Integer> category_images, List<String> category_descriptions){
        this.category_names = category_names;
        this.category_images = category_images;
        this.category_descriptions = category_descriptions;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.category_grid_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CategoryAdapter.ViewHolder holder, int position) {
        holder.category_name.setText(Category.values()[position].toString());
        holder.category_description.setText(category_descriptions.get(position));
        holder.category_image.setImageResource(category_images.get(position));
    }

    @Override
    public int getItemCount() {

        return category_names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView category_name;
        TextView category_description;
        ImageView category_image;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            category_name = itemView.findViewById(R.id.txt_category_name);
            category_description = itemView.findViewById(R.id.txt_category_description);
            category_image = itemView.findViewById(R.id.img_category_image);



        }
    }
}
