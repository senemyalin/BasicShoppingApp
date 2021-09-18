package com.example.basicshoppingapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.basicshoppingapp.Class.DatabaseStuff;
import com.example.basicshoppingapp.R;

import org.sql2o.Connection;


public class SignUpActivity extends AppCompatActivity {

    Intent intent;

    EditText edt_fullname;
    EditText edt_email;
    EditText edt_password;
    EditText edt_phonenumber;

    ProgressBar progressBar;
    Button signup;
    TextView have_acc;

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


        Activity activity=this;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                new Thread(()->{

                    try(Connection con = DatabaseStuff.sql2o.open()) {

                        System.out.println("this works fine");
                        String fullname, email, password, phonenumber;

                        fullname = String.valueOf(edt_fullname.getText());
                        email = String.valueOf(edt_email.getText());
                        password = String.valueOf(edt_password.getText());
                        phonenumber = String.valueOf(edt_phonenumber.getText());

                          if (!fullname.equals("") && !email.equals("") && !password.equals("") && !phonenumber.equals("")) {

                            con.createQuery("INSERT INTO users (fullname, email, password, phonenumber) VALUES (:edt_fullname, :edt_email, :edt_password, :edt_phonenumber)").addParameter("edt_fullname",fullname).addParameter("edt_email",email).addParameter("edt_password",password).addParameter("edt_phonenumber",phonenumber).executeUpdate();

                              activity.runOnUiThread(()-> {
                                progressBar.setVisibility(View.GONE);

                                intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                              });

                        } else {
                              activity.runOnUiThread(()-> {
                                Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                              });
                          }
                    }
                }).start();


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

}