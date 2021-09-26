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

import com.example.basicshoppingapp.R;

import java.io.IOException;

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

    final String url_Register= "http://172.20.10.5/LoginRegister/signup.php";

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

                    new RegisterUser().execute(fullname, email, password, phonenumber);


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


public class RegisterUser extends AsyncTask<String,Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String fullname= strings[0];
        String email= strings[1];
        String password=strings[2];
        String phonenumber=strings[3];


        RequestBody formBody = new FormBody.Builder()
                .add("fullname", fullname)
                .add("email", email)
                .add("password", password)
                .add("phonenumber", phonenumber)
                .build();


        OkHttpClient okHttpClient=new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_Register)
                .post(formBody)
                .build();


        //checking server response and inserting data

        Response response= null;
        OkHttpClient client = new OkHttpClient();

        try {
            response= client.newCall(request).execute();
            if(response.isSuccessful()){
                String result= response.body().string();
                showToast(result);

                if(result.equals("\nUser registered successfully")){
                    showToast("Registered Successfully");
                    Intent intent= new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                else if (result.equals("\nUser already exists")){
                    showToast("User Already Exist");

                }
                else{
                    showToast("Oops! please try again!");
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
                Toast.makeText(SignUpActivity.this, Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
