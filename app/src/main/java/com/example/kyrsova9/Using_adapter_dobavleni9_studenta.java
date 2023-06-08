package com.example.kyrsova9;

import android.os.Parcel;
import android.os.Parcelable;

public class Using_adapter_dobavleni9_studenta implements Parcelable {
    String last_name,first_name,  middle_name,naz_id_facultet, naz_id_napravlenie,naz_id_groupa;
    Integer id, id_facultet, id_napravlenie,id_groupa;

    public Using_adapter_dobavleni9_studenta(Integer id, String last_name, String first_name, String middle_name,
                                             String naz_id_facultet, Integer id_facultet,
                                             String naz_id_napravlenie,Integer id_napravlenie,
                                             String naz_id_groupa,Integer id_groupa) {
        this.id = id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.naz_id_facultet=naz_id_facultet;
        this.id_facultet = id_facultet;
        this.naz_id_napravlenie=naz_id_napravlenie;
        this.id_napravlenie = id_napravlenie;
        this.naz_id_groupa=naz_id_groupa;
        this.id_groupa = id_groupa;

    }

    public Using_adapter_dobavleni9_studenta(Parcel in) {
        id = in.readInt();
        last_name= in.readString();
        first_name= in.readString();
        middle_name= in.readString();
        naz_id_facultet=in.readString();
        id_facultet= in.readInt();
        naz_id_napravlenie=in.readString();
        id_napravlenie= in.readInt();
        naz_id_groupa=in.readString();
        id_groupa= in.readInt();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.last_name);
        out.writeString(this.first_name);
        out.writeString(this.middle_name);
        out.writeString(this.naz_id_facultet);
        out.writeInt(this.id_facultet);
        out.writeString(this.naz_id_napravlenie);
        out.writeInt(this.id_napravlenie);
        out.writeString(this.naz_id_groupa);
        out.writeInt(this.id_groupa);

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

    public String getNaz_id_facultet() {
        return naz_id_facultet;
    }

    public void setNaz_id_facultet(String naz_id_facultet) {
        this.naz_id_facultet = naz_id_facultet;
    }

    public String getNaz_id_napravlenie() {
        return naz_id_napravlenie;
    }

    public void setNaz_id_napravlenie(String naz_id_napravlenie) {
        this.naz_id_napravlenie = naz_id_napravlenie;
    }

    public String getNaz_id_groupa() {
        return naz_id_groupa;
    }

    public void setNaz_id_groupa(String naz_id_groupa) {
        this.naz_id_groupa = naz_id_groupa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_facultet() {
        return id_facultet;
    }

    public void setId_facultet(Integer id_facultet) {
        this.id_facultet = id_facultet;
    }

    public Integer getId_napravlenie() {
        return id_napravlenie;
    }

    public void setId_napravlenie(Integer id_napravlenie) {
        this.id_napravlenie = id_napravlenie;
    }

    public Integer getId_groupa() {
        return id_groupa;
    }

    public void setId_groupa(Integer id_groupa) {
        this.id_groupa = id_groupa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Using_adapter_dobavleni9_studenta> CREATOR = new Parcelable.Creator<Using_adapter_dobavleni9_studenta>() {
        @Override
        public Using_adapter_dobavleni9_studenta createFromParcel(Parcel source) {
            return new Using_adapter_dobavleni9_studenta(source);
        }

        @Override
        public Using_adapter_dobavleni9_studenta[] newArray(int size) {
            return new Using_adapter_dobavleni9_studenta[size];
        }
    };
}
