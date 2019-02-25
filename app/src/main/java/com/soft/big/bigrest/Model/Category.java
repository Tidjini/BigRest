package com.soft.big.bigrest.Model;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class Category {

    private int id;
    private String name, imprimante;
    private int impCuis;
    private int tFamille;

    public Category() {
    }

    public Category(int id, String name, int impCuis, int tFamille, String imprimante) {
        this.id = id;
        this.name = name;
        this.impCuis = impCuis;
        this.tFamille = tFamille;
        this.imprimante = imprimante;
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

    public String getImprimante() {
        return imprimante;
    }

    public void setImprimante(String imprimante) {
        this.imprimante = imprimante;
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
