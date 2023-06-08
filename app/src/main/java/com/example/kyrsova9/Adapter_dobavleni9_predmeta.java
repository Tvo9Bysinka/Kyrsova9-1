package com.example.kyrsova9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_dobavleni9_predmeta extends BaseAdapter {
    ArrayList<Using_adapter_dobavleni9_predmeta> uadp = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    Adapter_dobavleni9_predmeta (Context context, ArrayList<Using_adapter_dobavleni9_predmeta> uadp) {
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
        view = lInflater.inflate(R.layout.otobrajenie_izmenenie_predmeta_list, parent, false);
        if (uadp.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.prlist_id)).setText(uadp.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.prlist_name_premeta)).setText(uadp.get(position).getName_premeta());
        ((TextView) view.findViewById(R.id.prlist_id_forma_control9)).setText(uadp.get(position).getNaz_id_forma_control9());
        return view;
    }

    public void setDropDownViewResource(int simple_spinner_dropdown_item) {

    }
}