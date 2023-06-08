package com.example.kyrsova9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_dvapol9_dobavleni9 extends BaseAdapter {
    ArrayList<Using_adapter_dvapol9_dobavleni9> fngs = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    Adapter_dvapol9_dobavleni9(Context context, ArrayList<Using_adapter_dvapol9_dobavleni9> fngs ) {
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
        view = lInflater.inflate(R.layout.otobrajenie_propyskov_studentov, parent, false);
        if (fngs.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.propysklist_id)).setText(fngs.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.propyskST_list_id_fulname)).setText(fngs.get(position).getName());
        ((TextView) view.findViewById(R.id.propyskST_list_id)).setText(fngs.get(position).getPropyski().toString());

        return view;
    }
}