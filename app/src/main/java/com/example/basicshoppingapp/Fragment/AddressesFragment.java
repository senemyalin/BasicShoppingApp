package com.example.basicshoppingapp.Fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.security.identity.NoAuthenticationKeyAvailableException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.basicshoppingapp.Activity.LoginActivity;
import com.example.basicshoppingapp.Activity.MainActivity;
import com.example.basicshoppingapp.Adapter.AddressesAdapter;
import com.example.basicshoppingapp.Class.Address;
import com.example.basicshoppingapp.Class.Product;
import com.example.basicshoppingapp.Helper;
import com.example.basicshoppingapp.R;
import com.example.basicshoppingapp.Response.FavouriteProductResponse;
import com.example.basicshoppingapp.Response.GetAddressResponse;
import com.example.basicshoppingapp.Response.GetFavouriteProductResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddressesFragment extends Fragment {

    AddressesAdapter addressesAdapter;
    List<Address> addresses = new ArrayList<>();

    public static final String url_getAddresses = "http://192.168.1.104/LoginRegister/getAddress.php";
    public static final String url_removeAddress = "http://192.168.1.104/LoginRegister/removeAddress.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.address_listview_layout, container, false);

        SwipeMenuListView addressList = (SwipeMenuListView) view.findViewById(R.id.address_listview);
        addressesAdapter = new AddressesAdapter(getActivity(), addresses);
        addressList.setAdapter(addressesAdapter);

        Button create_new_address = view.findViewById(R.id.btn_add_an_address);

        create_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new NewAddressFragment()).addToBackStack("Favourite Products Fragment")
                        .commit();
            }
        });

        addressList.setMenuCreator(creator);

        addressList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //make chosen address;
                        NewAddressFragment.makeChosenAddress(addresses.get(position).getId(), getActivity());
                        break;
                    case 1:
                        // delete
                        removeAddress(addresses, position, getActivity(), addressesAdapter);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        if (LoginActivity.user_ID != 0) {
            updateAddress(getActivity(), addresses, addressesAdapter);
        }

        return view;
    }

    public static void updateAddress(Activity activity, List<Address> list, BaseAdapter adapter) {
        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            GetAddressResponse res = Helper.httpPost(GetAddressResponse.class, url_getAddresses, map);


            if (res == null) {
                System.out.println("burada hata varrrr");

                // give the user an error
                return;
            }

            List<Boolean> message = res.getMessage();
            List<Address> newList = res.getAddress();


            activity.runOnUiThread(list::clear);
            if (message.get(0)) {
                boolean chosen = false;
                if (newList.size() > 0) {
                    for (Address ad : newList)
                        if (ad.getChosen().equals("True")) {
                            chosen = true;
                            MainActivity.addressState.setItem(ad);
                            break;
                        }
                    if (!chosen) {
                        NewAddressFragment.makeChosenAddress(newList.get(0).getId(), activity);
                        newList.get(0).setChosen("True");
                        MainActivity.addressState.setItem(newList.get(0));
                    }
                }
                activity.runOnUiThread(() -> {
                    list.addAll(newList);
                });
            }
            activity.runOnUiThread(() -> {
                if (list.size() == 0) {
                    MainActivity.addressState.setItem(null);
                    ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewAddressFragment()).commit();
                }
                adapter.notifyDataSetInvalidated();
                adapter.notifyDataSetChanged();
            });

        }).start();
    }

    void removeAddress(List<Address> list, int position, Activity activity, BaseAdapter adapter) {
        String addressName = list.get(position).getAddressName();
        new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(LoginActivity.user_ID));
            map.put("address_name", addressName);
            FavouriteProductResponse res = Helper.httpPost(FavouriteProductResponse.class, url_removeAddress, map);


            if (res == null) {
                // give the user an error
                return;
            }

            String message = res.getMessage();


            activity.runOnUiThread(() -> {
                updateAddress(getActivity(), list, adapter);
                Toast.makeText(getContext(), "Your address is deleted", Toast.LENGTH_LONG).show();
            });

        }).start();
    }


    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {

            // create "open" item
            SwipeMenuItem chooseAddress = new SwipeMenuItem(
                    getContext());
            // set item background
            chooseAddress.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                    0xCE)));
            // set item width
            chooseAddress.setWidth(180);
            // set item title
            chooseAddress.setTitle("Choose");
            // set item title fontsize
            chooseAddress.setTitleSize(18);
            // set item title font color
            chooseAddress.setTitleColor(Color.WHITE);
            // add to menu
            menu.addMenuItem(chooseAddress);

            // create "delete" item
            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                    0x3F, 0x25)));
            // set item width
            deleteItem.setWidth(180);
            // set a icon
            deleteItem.setIcon(R.drawable.ic_delete);
            // add to menu
            menu.addMenuItem(deleteItem);

        }
    };
}