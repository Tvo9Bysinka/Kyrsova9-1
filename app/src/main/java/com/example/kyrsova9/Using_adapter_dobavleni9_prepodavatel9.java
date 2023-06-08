package com.example.kyrsova9;

import android.os.Parcel;
import android.os.Parcelable;

public class Using_adapter_dobavleni9_prepodavatel9 implements Parcelable {
    String last_name,first_name, middle_name,naz_id_doljnost;
    Integer id,id_doljnost;

    public Using_adapter_dobavleni9_prepodavatel9(Integer id, String last_name, String first_name, String middle_name,
                                             String naz_id_doljnost, Integer id_doljnost) {
        this.id = id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.naz_id_doljnost=naz_id_doljnost;
    }

    public Using_adapter_dobavleni9_prepodavatel9(Parcel in) {
        id = in.readInt();
        last_name= in.readString();
        first_name= in.readString();
        middle_name= in.readString();
        naz_id_doljnost=in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.last_name);
        out.writeString(this.first_name);
        out.writeString(this.middle_name);
        out.writeString(this.naz_id_doljnost);
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getNaz_id_doljnost() {
        return naz_id_doljnost;
    }

    public void setNaz_id_doljnost(String naz_id_doljnost) {
        this.naz_id_doljnost = naz_id_doljnost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_doljnost() {
        return id_doljnost;
    }

    public void setId_doljnost(Integer id_doljnost) {
        this.id_doljnost = id_doljnost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Using_adapter_dobavleni9_prepodavatel9> CREATOR = new Parcelable.Creator<Using_adapter_dobavleni9_prepodavatel9>() {
        @Override
        public Using_adapter_dobavleni9_prepodavatel9 createFromParcel(Parcel source) {
            return new Using_adapter_dobavleni9_prepodavatel9(source);
        }

        @Override
        public Using_adapter_dobavleni9_prepodavatel9[] newArray(int size) {
            return new Using_adapter_dobavleni9_prepodavatel9[size];
        }
    };
}
