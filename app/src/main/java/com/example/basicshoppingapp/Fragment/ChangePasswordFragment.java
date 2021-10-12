package com.example.basicshoppingapp.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.FavouriteProductResponse;
import com.example.basicshoppingapp.Response.GetFavouriteProductResponse;

import java.util.HashMap;
import java.util.List;


public class ChangePasswordFragment extends Fragment {

    static final String url_change_password = "http://192.168.1.104/LoginRegister/changePassword.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        EditText old_password;
        EditText new_password_first;
        EditText new_password_second;
        Button change;

        old_password = view.findViewById(R.id.txt_old_password);
        new_password_first = view.findViewById(R.id.txt_new_password_first);
        new_password_second = view.findViewById(R.id.txt_new_password_second);
        change = view.findViewById(R.id.btn_change);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_password(getActivity(),String.valueOf(old_password.getText()),
                                              String.valueOf(new_password_first.getText()),
                                            String.valueOf(new_password_second.getText()),v);
            }
        });

        return view;


    }

    void change_password(Activity activity,
                         String old_pw, String new_pw_first, String new_pw_second, View v){
        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            map.put("password", old_pw);
            map.put("new_password", new_pw_first);
            map.put("new_password2", new_pw_second);
            FavouriteProductResponse res = Helper.httpPost(FavouriteProductResponse.class, url_change_password, map);


            if (res == null) {
                // give the user an error
                return;
            }

            String message = res.getMessage();

            if (message.equals("Your password is changed!")) {
                activity.runOnUiThread(() ->{
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new ProfileFragment())
                            .commit();
                });
            }
            else{
                activity.runOnUiThread(() ->{
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                });
            }


        }).start();
    }
}