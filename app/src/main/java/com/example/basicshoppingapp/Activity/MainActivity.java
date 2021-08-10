package com.example.basicshoppingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.basicshoppingapp.FoodFragment;
import com.example.basicshoppingapp.ProfileFragment;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {

    ImageView img_register;

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bottomNavigationView= findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new FoodFragment()).commit();

        img_register = (ImageView)findViewById(R.id.img_register);

        img_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private BottomNavigationView.OnItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {

                    Fragment fragment = null;
                    switch (menuItem.getItemId()){

                        case R.id.food:
                            fragment=new FoodFragment();
                            break;
                        case R.id.shoppingcart:
                            fragment=new ShoppingCartFragment();
                            break;
                        case R.id.profile:
                            fragment=new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

                    return true;
                }
            };


}
