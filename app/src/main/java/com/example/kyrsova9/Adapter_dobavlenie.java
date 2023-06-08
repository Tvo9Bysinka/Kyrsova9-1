package com.example.kyrsova9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_dobavlenie extends BaseAdapter {
    ArrayList<Using_adater_dobavlenie> fngs = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    Adapter_dobavlenie(Context context, ArrayList<Using_adater_dobavlenie> fngs ) {
        this.ctx = context;
        //this.subjects = subjects;
        this.fngs.addAll(fngs);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fngs.size();
    }

    @Override
    public Object getItem(int position) {
        return fngs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.dobavlenie_list, parent, false);
        if (fngs.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.list_id)).setText(fngs.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.list_fngs)).setText(fngs.get(position).getName());

        return view;
    }
}
