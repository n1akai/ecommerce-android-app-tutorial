package com.n1akai.e_commercetutorial.models;

import java.util.List;

public class Make {
    private String id, label;
    private List<String> listCars;

    public Make() {
    }

    public Make(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getListCars() {
        return listCars;
    }
}
