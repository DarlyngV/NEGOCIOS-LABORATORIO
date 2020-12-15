package com.hellohasan.sqlite_project.Features.CreatePlanta;

public class Planta {
    private int id;
    private String name;
    private String  State;

    public Planta(int id, String name) {
        this.id = id;
        this.name = name;
        this.State="A";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }


}
