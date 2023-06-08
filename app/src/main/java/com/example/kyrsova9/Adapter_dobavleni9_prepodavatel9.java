package com.example.kyrsova9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_dobavleni9_prepodavatel9 extends BaseAdapter {
    ArrayList<Using_adapter_dobavleni9_prepodavatel9> uadp = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    Adapter_dobavleni9_prepodavatel9(Context context, ArrayList<Using_adapter_dobavleni9_prepodavatel9> uadp) {
        this.ctx = context;
        //this.subjects = subjects;
        this.uadp.addAll(uadp);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return uadp.size();
    }

    @Override
    public Object getItem(int position) {
        return uadp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.otobrajenie_izmenenie_prepodavatel9_list, parent, false);
        if (uadp.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.prepodavatel9list_id)).setText(uadp.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.prlist_lastname)).setText(uadp.get(position).getLast_name());
        ((TextView) view.findViewById(R.id.prlist_firstname)).setText(uadp.get(position).getFirst_name());
        ((TextView) view.findViewById(R.id.prlist_middlename)).setText(uadp.get(position).getMiddle_name());
        ((TextView) view.findViewById(R.id.prlist_id_doljnost)).setText(uadp.get(position).getNaz_id_doljnost());
        return view;
    }

    public void setDropDownViewResource(int simple_spinner_dropdown_item) {

    }
}