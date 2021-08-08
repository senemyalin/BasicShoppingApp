package com.example.basicshoppingapp;

import android.os.Bundle;
import android.widget.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryList;
    List<String> categoryName;
    List<Integer> categoryImage;
    List<String> categoryDescription;
    //List<String> categoryGoButton;

    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        categoryList = findViewById(R.id.recyclerView_category);

        categoryName = new ArrayList<>();
        categoryImage = new ArrayList<>();
        categoryDescription = new ArrayList<>();

        categoryName.add("Vegan");
        categoryName.add("Fresh Bakery");
        categoryName.add("Snacks");
        categoryName.add("Baverages");
        categoryName.add("Fruits & Veg");
        categoryName.add("Fit & Form");

        categoryImage.add(R.mipmap.ic_category_vegan_round);
        categoryImage.add(R.mipmap.ic_category_vegan_round);
        categoryImage.add(R.mipmap.ic_category_vegan_round);
        categoryImage.add(R.mipmap.ic_category_vegan_round);
        categoryImage.add(R.mipmap.ic_category_vegan_round);
        categoryImage.add(R.mipmap.ic_category_vegan_round);

        categoryDescription.add("This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");
        categoryDescription.add("This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");
        categoryDescription.add("This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");
        categoryDescription.add("This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");
        categoryDescription.add("This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");
        categoryDescription.add("This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");

        categoryAdapter = new CategoryAdapter(this, categoryName, categoryImage, categoryDescription);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,false);

        categoryList.setLayoutManager(gridLayoutManager);
        categoryList.setAdapter(categoryAdapter);
    }
}
