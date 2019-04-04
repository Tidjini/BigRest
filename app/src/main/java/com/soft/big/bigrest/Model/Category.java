package com.soft.big.bigrest.Model;

/**
 * Created by Tidjini on 09/11/2017.
 */

//Category = Famille, LibFamille, ImprimeCuisine, Imprimante, TFamille
public class Category {

    private int Id;
    private String LibFamille, Imprimante;
    private int ImprimeCuisine;
    private int TFamille;

    public Category() {
    }

    public Category(int id, String libFamille, int imprimeCuisine, int tFamille, String imprimante) {
        this.Id = id;
        this.LibFamille = libFamille;
        this.ImprimeCuisine = imprimeCuisine;
        this.TFamille = tFamille;
        this.Imprimante = imprimante;
    }

    public String getIdName(){
        return Id+" "+LibFamille;
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getName() {
        return LibFamille;
    }

    public void setName(String name) {
        this.LibFamille = name;
    }

    public String getImprimante() {
        return Imprimante;
    }

    public void setImprimante(String imprimante) {
        this.Imprimante = imprimante;
    }

    public int getImprimeCuisine() {
        return ImprimeCuisine;
    }

    public void setImprimeCuisine(int impCuis) {
        this.ImprimeCuisine = impCuis;
    }

    public int getTFamille() {
        return TFamille;
    }

    public void setTFamille(int tFamille) {
        this.TFamille = tFamille;
    }
}
