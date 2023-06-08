package com.example.kyrsova9;

public class Adapter_tri_dobavleni9 {
    private final String name;
    private final int id;
    private final int id_1;
    private final int id_2;
    private final int id_3;
    private final int id_4;

    public Adapter_tri_dobavleni9(int id,int id_1,int id_2,int id_3, int id_4, String name){
        this.name = name;
        this.id = id;
        this.id_1 = id_1;
        this.id_2 = id_2;
        this.id_3 = id_3;
        this.id_4 = id_4;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getId_1() {
        return id_1;
    }

    public int getId_2() {
        return id_2;
    }

    public int getId_3() {
        return id_3;
    }

    public int getId_4() {
        return id_4;
    }
}
