package com.example.basicshoppingapp.Activity;

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

import com.example.basicshoppingapp.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, email, password, phonenumber;

                fullname = String.valueOf(edt_fullname.getText());
                email = String.valueOf(edt_email.getText());
                password = String.valueOf(edt_password.getText());
                phonenumber = String.valueOf(edt_phonenumber.getText());

                if(!fullname.equals("") && !email.equals("") && !password.equals("") && !phonenumber.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "phonenumber";
                            field[2] = "email";
                            field[3] = "password";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = email;
                            data[2] = password;
                            data[3] = phonenumber;

                            PutData putData = new PutData("http://192.168.1.35/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        if(intent==null){
                                        intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);}
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                    //End ProgressBar (Set visibility to GONE)
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent==null){
                    intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);}
                finish();
            }
        });

    }

}