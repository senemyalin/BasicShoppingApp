package com.example.basicshoppingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.basicshoppingapp.Class.Address;
import com.example.basicshoppingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddressesAdapter extends BaseAdapter {

    public List<Address> addresses;
    LayoutInflater inflater;
    Context ctx;

    public AddressesAdapter(Context ctx, List<Address> addresses){
        super();

        this.ctx = ctx;
        this.addresses = addresses;
        this.inflater = LayoutInflater.from(ctx);

    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return addresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(R.layout.fragment_addresses, null);

        TextView name = (TextView) convertView.findViewById(R.id.txt_address_name);
        TextView country = (TextView) convertView.findViewById(R.id.txt_address_country);
        TextView city = (TextView) convertView.findViewById(R.id.txt_address_city);
        TextView district = (TextView) convertView.findViewById(R.id.txt_shoppingcart_district);

        if (!addresses.isEmpty()) {

            name.setText(addresses.get(position).getAddressName());
            country.setText(addresses.get(position).getCountry());
            city.setText(addresses.get(position).getCity());
            district.setText(addresses.get(position).getDistrict());
        }

        return convertView;
    }
}
