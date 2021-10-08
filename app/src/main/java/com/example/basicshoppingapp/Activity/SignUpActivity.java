package com.example.basicshoppingapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basicshoppingapp.Class.ProductCount;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.ShoppingCartResponse;
import com.example.basicshoppingapp.Response.SignUpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignUpActivity extends AppCompatActivity {

    Intent intent;

    EditText edt_fullname;
    EditText edt_email;
    EditText edt_password;
    EditText edt_phonenumber;

    ProgressBar progressBar;
    Button signup;
    TextView have_acc;

    final String url_Register= "http://192.168.1.104/LoginRegister/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_sign_up);

        edt_fullname = findViewById(R.id.txt_signup_fullname);
        edt_email = findViewById(R.id.txt_signup_email);
        edt_password = findViewById(R.id.txt_signup_password);
        edt_phonenumber = findViewById(R.id.txt_signup_phonenumber);

        progressBar = findViewById(R.id.progressBar);
        signup = findViewById(R.id.btn_signup_signup);
        have_acc = findViewById(R.id.txt_have_acc);


        signup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                String fullname, email, password, phonenumber;

                fullname = String.valueOf(edt_fullname.getText());
                email = String.valueOf(edt_email.getText());
                password = String.valueOf(edt_password.getText());
                phonenumber = String.valueOf(edt_phonenumber.getText());

                if (!fullname.equals("") && !email.equals("") && !password.equals("") && !phonenumber.equals("")) {
                    progressBar.setVisibility(View.GONE);

                    new Thread(()->{
                        HashMap<String, String> map=new HashMap<>();
                        map.put("fullname",fullname);
                        map.put("email",email);
                        map.put("password", password);
                        map.put("phonenumber", phonenumber);

                        SignUpResponse res= Helper.httpPost(SignUpResponse.class,url_Register,map);
                        List<String> message = res.getMessage();

                        if(res ==null){
                            // give the user an error
                            return;
                        }

                        if(message.get(0).equals("User registered successfully")){
                            showToast("Registered Successfully");
                            Intent intent= new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        else if (message.get(0).equals("User already exists")){
                            showToast("User Already Exist");

                        }
                        else{
                            showToast("Oops! please try again!");
                        }


                    }).start();



                }else {
                        Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                }

            }
        });

        have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SignUpActivity.this, Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
