package com.soft.big.bigrest.Model;

import android.graphics.Bitmap;

import java.math.BigDecimal;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class Plat {
    String désignProf, refProd;

    int idProd, famProd, typeProd;
    BigDecimal tva, prixProdVente;
    Bitmap imageProd;

    public Plat() {
    }

    public Plat(int idProd, String désignProf, String refProd, int famProd, int typeProd, BigDecimal tva, BigDecimal prixProdVente, Bitmap imageProd) {
        this.idProd = idProd;
        this.désignProf = désignProf;
        this.refProd = refProd;
        this.famProd = famProd;
        this.typeProd = typeProd;
        this.tva = tva;
        this.prixProdVente = prixProdVente;
        this.imageProd = imageProd;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getDésignProf() {
        return désignProf;
    }

    public void setDésignProf(String désignProf) {
        this.désignProf = désignProf;
    }

    public String getRefProd() {
        return refProd;
    }

    public void setRefProd(String refProd) {
        this.refProd = refProd;
    }

    public int getFamProd() {
        return famProd;
    }

    public void setFamProd(int famProd) {
        this.famProd = famProd;
    }

    public int getTypeProd() {
        return typeProd;
    }

    public void setTypeProd(int typeProd) {
        this.typeProd = typeProd;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public BigDecimal getPrixProdVente() {
        return prixProdVente;
    }

    public void setPrixProdVente(BigDecimal prixProdVente) {
        this.prixProdVente = prixProdVente;
    }

    public Bitmap getImageProd() {
        return imageProd;
    }

    public void setImageProd(Bitmap imageProd) {
        this.imageProd = imageProd;
    }
}
