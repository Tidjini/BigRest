package com.soft.big.bigrest.Model;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class Category {

    private int id;
    private String name;
    private int impCuis;
    private int tFamille;

    public Category() {
    }

    public Category(int id, String name, int impCuis, int tFamille) {
        this.id = id;
        this.name = name;
        this.impCuis = impCuis;
        this.tFamille = tFamille;
    }

    public String getIdName(){
        return id+" "+name;
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

    public int getImpCuis() {
        return impCuis;
    }

    public void setImpCuis(int impCuis) {
        this.impCuis = impCuis;
    }

    public int gettFamille() {
        return tFamille;
    }

    public void settFamille(int tFamille) {
        this.tFamille = tFamille;
    }
}
