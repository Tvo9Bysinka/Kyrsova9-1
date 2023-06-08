package com.example.kyrsova9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_dobavleni9_jurnala extends BaseAdapter {
    ArrayList<Using_adapter_dobavleni9_jurnala> uadj = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    Adapter_dobavleni9_jurnala(Context context, ArrayList<Using_adapter_dobavleni9_jurnala> uadj) {
        this.ctx = context;
        //this.subjects = subjects;
        this.uadj.addAll(uadj);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return uadj.size();
    }

    @Override
    public Object getItem(int position) {
        return uadj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.otobrajenie_izmenenie_jurnala_list, parent, false);
        if (uadj.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.jlist_id)).setText(uadj.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.jlist_id_prepoda)).setText(uadj.get(position).getNaz_id_prepoda_doljn());
        ((TextView) view.findViewById(R.id.jlist_id_predmeta)).setText(uadj.get(position).getNaz_id_predmeta_fk());
        ((TextView) view.findViewById(R.id.jlist_fng)).setText(uadj.get(position).getNaz_FNG());
        ((TextView) view.findViewById(R.id.jlist_id_studenta)).setText(uadj.get(position).getNaz_id_studenta());
        return view;
    }

    public void setDropDownViewResource(int simple_spinner_dropdown_item) {

    }
}