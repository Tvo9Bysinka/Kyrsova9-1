package com.example.kyrsova9;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

public class Using_adapter_dvapol9_dobavleni9 implements Parcelable {
    String name;
    Integer id;
    Integer propyski;
    public Using_adapter_dvapol9_dobavleni9(Integer id, String name,Integer propyski) {
        this.id = id;
        this.name = name;
        this.propyski=propyski;
    }

    public Using_adapter_dvapol9_dobavleni9(Parcel in) {
        id = in.readInt();
        name = in.readString();
        propyski=in.readInt();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.name);
        out.writeInt(this.propyski);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPropyski() {
        return propyski;
    }

    public void setPropyski(Integer propyski) {
        this.propyski = propyski;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Using_adapter_dvapol9_dobavleni9> CREATOR = new Parcelable.Creator<Using_adapter_dvapol9_dobavleni9>() {
        @Override
        public Using_adapter_dvapol9_dobavleni9 createFromParcel(Parcel source) {
            return new Using_adapter_dvapol9_dobavleni9(source);
        }

        @Override
        public Using_adapter_dvapol9_dobavleni9[] newArray(int size) {
            return new Using_adapter_dvapol9_dobavleni9[size];
        }
    };
}