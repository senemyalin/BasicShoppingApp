package com.example.basicshoppingapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basicshoppingapp.Class.Address;
import com.example.basicshoppingapp.Fragment.AddressesFragment;
import com.example.basicshoppingapp.Fragment.NewAddressFragment;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.GetAddressResponse;
import com.example.basicshoppingapp.Response.IsChosenAddressResponse;
import com.example.basicshoppingapp.Response.LoginResponse;
import com.example.basicshoppingapp.Response.SignUpResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    Intent intent;

    EditText edt_email;
    EditText edt_password;
    Button login;
    TextView register;
    TextView forget_password;
    public static int user_ID;

    ProgressBar progressBar;

    final String url_Login = "http://192.168.1.104/LoginRegister/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        edt_email = findViewById(R.id.login_email);
        edt_password = findViewById(R.id.login_password);

        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.txt_register);
        progressBar = findViewById(R.id.progressBar_login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String email, password;
                email = String.valueOf(edt_email.getText());
                password = String.valueOf(edt_password.getText());

                if (!email.equals("") && !password.equals("")) {

                    progressBar.setVisibility(View.GONE);
                    RegisterUser(email, password, LoginActivity.this);

                } else {

                    Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    private void RegisterUser(String email, String password, Activity activity) {
        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);

            LoginResponse res = Helper.httpPost(LoginResponse.class, url_Login, map);

            if (res == null) {
                // give the user an error
                return;
            }

            String message = res.getMessage();
            String user_id = res.getUserid();

            if (message.equals("Full name, Email, Password or Phone number can not be empty")) {
                activity.runOnUiThread(() -> {
                    showToast("Full name, Email, Password or Phone number can not be empty");
                });
            } else if (message.equals("Logged in Successfully")) {
                showToast("Logged in Successfully");
                user_ID = Integer.valueOf(user_id);
                getChosen(activity);

            } else {
                showToast("Email or Password is wrong. Try again.");
            }


        }).start();

    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, Text, Toast.LENGTH_LONG).show();
            }
        });
    }

    void getChosen(Activity activity) {
        String url_isChosenAddress = "http://192.168.1.104/LoginRegister/isChosenAddress.php";

        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            IsChosenAddressResponse res = Helper.httpPost(IsChosenAddressResponse.class, url_isChosenAddress, map);


            if (res == null) {
                // give the user an error
                return;
            }

            List<Boolean> message = res.getMessage();
            List<Address> newList = res.getAddress();


            activity.runOnUiThread(() -> {
                if (message.get(0)) {
                    MainActivity.addressState.setItem(newList.size() ==0 ? null :newList.get(0));
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });


        }).start();
    }
}






