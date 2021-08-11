package com.example.basicshoppingapp.Enum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicshoppingapp.R;

import java.util.List;

public enum Category {
    //okeyhttp
    Vegan ("Vegan"),
    Fresh_Bakery ("Fresh Bakery"),
    Snacks ("Snacks") ,
    Beverages ("Beverages"),
    Fruits_Veg ("Fruits & Veg"),
    Fit_Form ("Fit & Form");

    public String category_string;

    Category(String category_string){
        this.category_string = category_string;
    }

    public boolean equalsName(String stringName) {
        return category_string.equals(stringName);
    }

    public String toString() {
        return this.category_string;
    }

    public static class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{


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
        public Category.CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
            View view= inflater.inflate(R.layout.category_grid_layout,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull Category.CategoryAdapter.ViewHolder holder, int position) {
            holder.category_name.setText(values()[position].toString());
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
}
