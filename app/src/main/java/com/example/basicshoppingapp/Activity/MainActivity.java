package com.example.basicshoppingapp.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.basicshoppingapp.Class.Address;
import com.example.basicshoppingapp.Class.Category;
import com.example.basicshoppingapp.Class.Market;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Fragment.CategoryFragment;
import com.example.basicshoppingapp.Fragment.NewAddressFragment;
import com.example.basicshoppingapp.Fragment.ProfileFragment;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.Response.MainResponse;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Fragment.ShoppingCartFragment;
import com.example.basicshoppingapp.State;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static State<Address> addressState = new State<>(null);
    public static State<List<Market>> marketsState = new State<>(new ArrayList<>());
    public static State<List<Category>> categoryState = new State<>(new ArrayList<>());
    public static State<List<Product>> productState = new State<>(new ArrayList<>());
    public static State<Boolean> shoppingCartState = new State<>(true);

    String url_getProducts = "http://192.168.1.104/LoginRegister/getProducts.php";
    public static TextView address;
    private BottomNavigationView bottomNavigationView;
    private boolean isSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        address = (TextView) findViewById(R.id.textView_chosen_address);
        setAddress();
        addressState.addListener(() -> {
            setAddress();
            if (addressState.getItem() == null) return;
            if (!isSet) {
                isSet = true;
                setUp();
            } else {
                this.getProduct(String.valueOf(addressState.getItem().getId()), MainActivity.this);
            }
        });
        if (addressState.getItem() != null) {
            isSet = true;
            setUp();
        } else {
            bottomNavigationView = findViewById(R.id.bottomNav);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewAddressFragment()).commit();
        }
    }

    private void setUp() {
        getProduct(String.valueOf(addressState.getItem().getId()), MainActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new CategoryFragment()).commit();
    }

    @SuppressLint("SetTextI18n")
    private void setAddress() {
        Address c = addressState.getItem();
        if (c == null)
            address.setText("Address");
        else
            address.setText(c.getAddressName() + ":" + c.getCountry() + "/" + c.getCity() + "/" + c.getDistrict());
    }

    private void showProducts() {
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(bottomNavMethod);
    }

    private BottomNavigationView.OnItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {

                    TextView txtView = (TextView) findViewById(R.id.textView_food);

                    Fragment fragment = null;
                    switch (menuItem.getItemId()) {

                        case R.id.food:

                            fragment = new CategoryFragment();
                            txtView.setText("FOODS");
                            break;
                        case R.id.shoppingcart:
                            fragment = new ShoppingCartFragment();
                            txtView.setText("SHOPPING");
                            txtView.setTextSize(30);
                            break;
                        case R.id.profile:
                            fragment = new ProfileFragment();
                            txtView.setText("PROFILE");
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                    return true;
                }
            };

    private void getProduct(String address_id, Activity activity) {

        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("address_id", address_id);
            MainResponse res = Helper.httpPost(MainResponse.class, url_getProducts, map);
            if (res == null) {
                // give the user an error

                return;
            }


            activity.runOnUiThread(() -> {
                categoryState.setItem(res.getCategory());
                marketsState.setItem(res.getMarket());
                productState.setItem(res.getProduct());
                showProducts();
            });

        }).start();
    }


}
