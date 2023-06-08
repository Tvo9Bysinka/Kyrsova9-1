package com.example.kyrsova9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_dobavleni9_studenta extends BaseAdapter {
    ArrayList<Using_adapter_dobavleni9_studenta> uads = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    Adapter_dobavleni9_studenta(Context context, ArrayList<Using_adapter_dobavleni9_studenta> uads) {
        this.ctx = context;
        //this.subjects = subjects;
        this.uads.addAll(uads);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return uads.size();
    }

    @Override
    public Object getItem(int position) {
        return uads.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.otobrajenie_izmenenie_studenta_list, parent, false);
        if (uads.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.stlist_id)).setText(uads.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.stlist_lastname)).setText(uads.get(position).getLast_name());
        ((TextView) view.findViewById(R.id.stlist_firstname)).setText(uads.get(position).getFirst_name());
        ((TextView) view.findViewById(R.id.stlist_middlename)).setText(uads.get(position).getMiddle_name());
        ((TextView) view.findViewById(R.id.stlist_id_facultet)).setText(uads.get(position).getNaz_id_facultet());
        ((TextView) view.findViewById(R.id.stlist_id_napravlenie)).setText(uads.get(position).getNaz_id_napravlenie());
        ((TextView) view.findViewById(R.id.stlist_id_groupa)).setText(uads.get(position).getNaz_id_groupa());
        return view;
    }

    public void setDropDownViewResource(int simple_spinner_dropdown_item) {

    }
}