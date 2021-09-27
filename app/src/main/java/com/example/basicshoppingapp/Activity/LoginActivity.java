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

import com.example.basicshoppingapp.Class.DatabaseStuff;
import com.example.basicshoppingapp.R;

import org.json.JSONObject;
import org.sql2o.Connection;

import java.io.IOException;

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
    static int user_ID;

    ProgressBar progressBar;

    final String url_Login= "http://172.20.10.5/LoginRegister/login.php";

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

                if(!email.equals("") && !password.equals("")) {

                    progressBar.setVisibility(View.GONE);
                    new RegisterUser().execute(email, password);

                }
                else {

                    Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_SHORT).show();

                }


            }
        });




    }


    public class RegisterUser extends AsyncTask<String,Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String email= strings[0];
            String password=strings[1];


            RequestBody formBody = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .build();


            OkHttpClient okHttpClient=new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url_Login)
                    .post(formBody)
                    .build();


            //checking server response and inserting data

            Response response= null;
            OkHttpClient client = new OkHttpClient();

            try {
                response= client.newCall(request).execute();
                if(response.isSuccessful()) {
                    String result = response.body().string();
                 //   showToast(result);

                    JSONObject responseJSON = new JSONObject(result);
                    String message = responseJSON.getString("message");
                //    showToast(message);

                    if (message.equals("Logged in Successfully")) {
                        showToast("Logged in Successfully");
                        user_ID = Integer.valueOf(responseJSON.getString("userid"));

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    else {
                        showToast("Email or Password is wrong. Try again.");

                    }
                }
                else {
                    throw new IOException("Unexpected code " + response);
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }
    }

    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}






