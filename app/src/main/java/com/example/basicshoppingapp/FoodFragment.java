package com.example.basicshoppingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment {

    RecyclerView categoryList;
    List<String> categoryName;
    List<Integer> categoryImage;
    List<String> categoryDescription;
    // List<String> categoryGoButton;

    Category.CategoryAdapter categoryAdapter;



    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryName = new ArrayList<>();
        categoryImage = new ArrayList<>();
        categoryDescription = new ArrayList<>();
        //   categoryGoButton = new ArrayList<>();



        for(Category category : Category.values()){
            categoryName.add(category.toString());
        }

        categoryImage.add(R.mipmap.ic_category_vegan_round);
        categoryImage.add(R.mipmap.ic_category_vegan_round);
        categoryImage.add(R.mipmap.ic_category_vegan_round);
        categoryImage.add(R.mipmap.ic_category_vegan_round);
        categoryImage.add(R.mipmap.ic_category_vegan_round);
        categoryImage.add(R.mipmap.ic_category_vegan_round);

        categoryDescription.add("This is an category explanation. This is an category explanation. This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");
        categoryDescription.add("This is an category explanation. This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");
        categoryDescription.add("This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");
        categoryDescription.add("This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");
        categoryDescription.add("This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");
        categoryDescription.add("This is an category explanation. It is added because of trying. I wanted to add two more sentences. That is why I kept typing. That is it!");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        categoryList = view.findViewById(R.id.recyclerView_category);

        categoryAdapter = new Category.CategoryAdapter(getActivity(), categoryName, categoryImage, categoryDescription);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL,false);

        categoryList.setLayoutManager(gridLayoutManager);
        categoryList.setAdapter(categoryAdapter);
        return view;
    }

}

