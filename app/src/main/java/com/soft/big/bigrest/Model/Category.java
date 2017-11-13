package com.soft.big.bigrest.Model;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class Category {

    private int id;
    private String name;
    private String remarque;

    public Category() {
    }

    public Category(int id, String name, String remarque) {
        this.id = id;
        this.name = name;
        this.remarque = remarque;
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

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }
}
