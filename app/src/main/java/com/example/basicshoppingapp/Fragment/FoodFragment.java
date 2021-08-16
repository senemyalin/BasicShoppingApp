package com.example.basicshoppingapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicshoppingapp.Activity.MainActivity;
import com.example.basicshoppingapp.Adapter.CategoryAdapter;
import com.example.basicshoppingapp.Class.Category;
import com.example.basicshoppingapp.R;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment {

    RecyclerView categoryList;
    CategoryAdapter categoryAdapter;
    List<Category> categories = MainActivity.category;


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        categoryList = view.findViewById(R.id.recyclerView_category);

        categoryAdapter = new CategoryAdapter((MainActivity) getActivity(), categories);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL,false);

        categoryList.setLayoutManager(gridLayoutManager);
        categoryList.setAdapter(categoryAdapter);


        return view;
    }

}

