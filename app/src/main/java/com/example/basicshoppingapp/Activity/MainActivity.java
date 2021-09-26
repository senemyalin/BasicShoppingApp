package com.example.basicshoppingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.basicshoppingapp.Class.Category;
import com.example.basicshoppingapp.Class.Market;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Fragment.CategoryFragment;
import com.example.basicshoppingapp.Fragment.ProfileFragment;
import com.example.basicshoppingapp.MainResponse;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Fragment.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    ImageView img_register;
    public static List<Category> category = new ArrayList<>();
    public static List<Product> product = new ArrayList<>();
    public static List<Market> market = new ArrayList<>();


    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getProduct();

    }

    private void showProducts(){
        bottomNavigationView= findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new CategoryFragment()).commit();

        img_register = (ImageView)findViewById(R.id.img_register);
        img_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private BottomNavigationView.OnItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {

                    TextView txtView = (TextView)findViewById(R.id.textView_food);

                    Fragment fragment = null;
                    switch (menuItem.getItemId()){

                        case R.id.food:

                            fragment=new CategoryFragment();
                            txtView.setText("FOODS");
                            break;
                        case R.id.shoppingcart:
                            fragment=new ShoppingCartFragment();
                            txtView.setText("SHOPPING");
                            txtView.setTextSize(30);
                            break;
                        case R.id.profile:
                            fragment=new ProfileFragment();
                            txtView.setText("PROFILE");
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

                    return true;
                }
            };


    protected void getProduct(){
        OkHttpClient client = new OkHttpClient();

        String url="http://172.20.10.5/LoginRegister/getProducts.php";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // For the example, you can show an error dialog or a toast
                                // on the main UI thread
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        String res = null;
                        res = response.body().string();
                        Gson gson = new GsonBuilder().create();
                        MainResponse r = gson.fromJson(res, MainResponse.class);

                        category = r.getCategory();
                        market = r.getMarket();
                        product = r.getProduct();


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showProducts();
                            }
                        });

                        // Do something with the response
                    }
                });

    }


}
