package com.example.kyrsova9;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

public class Using_adapter_tri_dobavleni9 extends BaseAdapter implements SpinnerAdapter {
    private final List<Adapter_tri_dobavleni9> data;
    LayoutInflater lInflater;
    Context ctx;

    public Using_adapter_tri_dobavleni9(Context context, List<Adapter_tri_dobavleni9> data){
        this.ctx = context;
        this.data = data;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * Returns one Element of the ArrayList
     * at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    /**
     * Returns the View that is shown when a element was
     * selected.
     */
    @Override
    public View getView(int position, View recycle, ViewGroup parent) {
        TextView text;
        if (recycle != null){
            // Re-use the recycled view here!
            text = (TextView) recycle;
        } else {
            // No recycled view, inflate the "original" from the platform:
            text = (TextView) lInflater.inflate(android.R.layout.browser_link_context_header, parent, false);
        }
        text.setTextColor(Color.BLACK);
        text.setText(data.get(position).getName());
        return text;
    }
}
