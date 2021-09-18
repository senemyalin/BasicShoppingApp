package com.example.basicshoppingapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basicshoppingapp.Class.DatabaseStuff;
import com.example.basicshoppingapp.R;

import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Intent intent;

    EditText edt_email;
    EditText edt_password;
    Button login;
    TextView register;
    TextView forget_password;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        edt_email = findViewById(R.id.login_email);
        edt_password = findViewById(R.id.login_password);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.txt_register);
        forget_password = findViewById(R.id.txt_forget_password);

        progressBar = findViewById(R.id.progressBar_login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        Activity activity=this;
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // UI thread
                new Thread(()->{
                    // secondary thread
                    try(Connection con=DatabaseStuff.sql2o.open()){
                        // secondary thread
                        String email, password;
                        email = String.valueOf(edt_email.getText());
                        password = String.valueOf(edt_password.getText());

                        if(!email.equals("") && !password.equals("")) {

                            activity.runOnUiThread(()->{progressBar.setVisibility(View.VISIBLE);});
                            String passwordFromDb = con.createQuery("SELECT password from users where email=:user")
                                    .addParameter("user", email).executeAndFetchFirst(String.class);

                            if(passwordFromDb != null && passwordFromDb.equals(password)){
                                intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                activity.runOnUiThread(()->{
                                Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_SHORT).show();
                                });
                            }
                        }
                        else{
                                activity.runOnUiThread(()->{
                                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                                });
                        }

                    }

                }).start();

            }
        });




    }


}
