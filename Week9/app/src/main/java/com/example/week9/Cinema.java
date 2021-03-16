package com.example.week9;

public class Cinema {
    private final int id;
    private final String name;

    public Cinema(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
