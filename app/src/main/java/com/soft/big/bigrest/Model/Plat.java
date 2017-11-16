package com.soft.big.bigrest.Model;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class Plat {
    private int id, idClass;
    private Double price;
    private String Name, Remarque;

    //TODO change the image str
    int fake_ImageResource;
    public Plat() {
    }

    public Plat(int id, int idClass, Double price, String name, String remarque, int fake_ImageResource) {
        this.id = id;
        this.idClass = idClass;
        this.price = price;
        Name = name;
        Remarque = remarque;
        this.fake_ImageResource = fake_ImageResource;
    }


    public  int  getFake_ImageResource() {
        return fake_ImageResource;
    }public void setFake_ImageResource(int fake_ImageResource) {
        this.fake_ImageResource = fake_ImageResource;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRemarque() {
        return Remarque;
    }

    public void setRemarque(String remarque) {
        Remarque = remarque;
    }
}
