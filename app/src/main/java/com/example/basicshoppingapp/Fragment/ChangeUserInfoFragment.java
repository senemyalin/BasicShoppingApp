package com.example.basicshoppingapp.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Class.User;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.GetUserInfoResponse;
import com.example.basicshoppingapp.Response.SignUpResponse;

import java.util.HashMap;
import java.util.List;

import static com.example.basicshoppingapp.Fragment.ProfileFragment.url_getUserInfo;

public class ChangeUserInfoFragment extends Fragment {

    EditText fullname;
    EditText email;
    EditText phonenumber;
    Button save;

    static final String url_changeUserInfo = "http://192.168.1.104/LoginRegister/changeUserInfo.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_user_info, container, false);

        fullname = view.findViewById(R.id.txt_c_username);
        email = view.findViewById(R.id.txt_c_email);
        phonenumber = view.findViewById(R.id.txt_c_phonenumber);
        save = view.findViewById(R.id.btn_c_save);

        getUserInfo(getActivity());



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_email = String.valueOf(email.getText());
                String s_phonenumber = String.valueOf(phonenumber.getText());
                String s_fullname = String.valueOf(fullname.getText());

                System.out.println(s_email);
                System.out.println(s_phonenumber);
                System.out.println(s_fullname);

                changeUserInfo(getActivity(),s_email, s_phonenumber, s_fullname, v);
            }
        });

        return view;
    }

    void getUserInfo(Activity activity){
        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            GetUserInfoResponse res = Helper.httpPost(GetUserInfoResponse.class, url_getUserInfo, map);
            if (res == null) {
                // give the user an error

                return;
            }
            List<Boolean> message = res.getMessage();
            List<User> user_list = res.getUser();
            if (message.get(0)) {
                User user = user_list.get(0);
                activity.runOnUiThread(() -> {
                    fullname.setText(user.getFullname());
                    email.setText(user.getEmail());
                    phonenumber.setText(String.valueOf(user.getPhonenumber()));
                });
            }



        }).start();
    }

    void changeUserInfo(Activity activity, String email, String phonenumber, String fullname, View v){
        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            map.put("email", email);
            map.put("phone_number", phonenumber);
            map.put("full_name", fullname);

            SignUpResponse res = Helper.httpPost(SignUpResponse.class, url_changeUserInfo, map);
            if (res == null) {
                // give the user an error

                return;
            }
            List<String> message = res.getMessage();

            for (String a: message) {
                System.out.println(a);
                if(a.equals("Your email is changed.") || a.equals("Your phone number is changed.") || a.equals("Your full name is changed.")){
                    activity.runOnUiThread(() -> {
                        ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new ProfileFragment())
                                .commit();
                    });
                    return;
                    }

                }

        }).start();
    }
}