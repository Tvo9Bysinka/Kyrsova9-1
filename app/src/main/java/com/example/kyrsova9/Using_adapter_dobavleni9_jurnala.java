package com.example.kyrsova9;

import android.os.Parcel;
import android.os.Parcelable;

public class Using_adapter_dobavleni9_jurnala implements Parcelable {
    Integer id;
    String naz_id_prepoda_doljn,naz_id_predmeta_fk,naz_id_studenta,naz_FNG;

    public Using_adapter_dobavleni9_jurnala(Integer id,String naz_id_prepoda_doljn,String naz_id_predmeta_fk,String naz_FNG,String naz_id_studenta) {
        this.id = id;
        this.naz_id_prepoda_doljn=naz_id_prepoda_doljn;
        this.naz_id_predmeta_fk=naz_id_predmeta_fk;
        this.naz_FNG=naz_FNG;
        this.naz_id_studenta=naz_id_studenta;
    }

    public Using_adapter_dobavleni9_jurnala(Parcel in) {
        id = in.readInt();
        naz_id_prepoda_doljn = in.readString();
        naz_id_predmeta_fk= in.readString();
        naz_FNG= in.readString();
        naz_id_studenta= in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.naz_id_prepoda_doljn);
        out.writeString(this.naz_id_predmeta_fk);
        out.writeString(this.naz_FNG);
        out.writeString(this.naz_id_studenta);

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaz_id_prepoda_doljn() {
        return naz_id_prepoda_doljn;
    }

    public void setNaz_id_prepoda_doljn(String naz_id_prepoda_doljn) {
        this.naz_id_prepoda_doljn = naz_id_prepoda_doljn;
    }

    public String getNaz_id_predmeta_fk() {
        return naz_id_predmeta_fk;
    }

    public void setNaz_id_predmeta_fk(String naz_id_predmeta_fk) {
        this.naz_id_predmeta_fk = naz_id_predmeta_fk;
    }

    public String getNaz_id_studenta() {
        return naz_id_studenta;
    }

    public void setNaz_id_studenta(String naz_id_studenta) {
        this.naz_id_studenta = naz_id_studenta;
    }

    public String getNaz_FNG() {
        return naz_FNG;
    }

    public void setNaz_FNG(String naz_FNG) {
        this.naz_FNG = naz_FNG;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Using_adapter_dobavleni9_jurnala> CREATOR = new Parcelable.Creator<Using_adapter_dobavleni9_jurnala>() {
        @Override
        public Using_adapter_dobavleni9_jurnala createFromParcel(Parcel source) {
            return new Using_adapter_dobavleni9_jurnala(source);
        }

        @Override
        public Using_adapter_dobavleni9_jurnala[] newArray(int size) {
            return new Using_adapter_dobavleni9_jurnala[size];
        }
    };
}
