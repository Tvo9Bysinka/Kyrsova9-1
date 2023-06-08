package com.example.kyrsova9;

import android.os.Parcel;
import android.os.Parcelable;

public class Using_adapter_dobavleni9_predmeta implements Parcelable {
    String name_premeta,naz_id_forma_control9;
    Integer id,id_forma_control9;

    public Using_adapter_dobavleni9_predmeta(Integer id, String name_premeta,
                                                  String naz_id_forma_control9, Integer id_forma_control9) {
        this.id = id;
        this.name_premeta = name_premeta;
        this.naz_id_forma_control9=naz_id_forma_control9;
    }

    public Using_adapter_dobavleni9_predmeta(Parcel in) {
        id = in.readInt();
        name_premeta= in.readString();
        naz_id_forma_control9=in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.name_premeta);
        out.writeString(this.naz_id_forma_control9);
    }

    public String getName_premeta() {
        return name_premeta;
    }

    public void setName_premeta(String name_premeta) {
        this.name_premeta = name_premeta;
    }

    public String getNaz_id_forma_control9() {
        return naz_id_forma_control9;
    }

    public void setNaz_id_forma_control9(String naz_id_forma_control9) {
        this.naz_id_forma_control9 = naz_id_forma_control9;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_forma_control9() {
        return id_forma_control9;
    }

    public void setId_forma_control9(Integer id_forma_control9) {
        this.id_forma_control9 = id_forma_control9;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Using_adapter_dobavleni9_predmeta> CREATOR = new Parcelable.Creator<Using_adapter_dobavleni9_predmeta>() {
        @Override
        public Using_adapter_dobavleni9_predmeta createFromParcel(Parcel source) {
            return new Using_adapter_dobavleni9_predmeta(source);
        }

        @Override
        public Using_adapter_dobavleni9_predmeta[] newArray(int size) {
            return new Using_adapter_dobavleni9_predmeta[size];
        }
    };
}
