package com.soft.big.bigrest.Model;

import android.graphics.Bitmap;

import java.math.BigDecimal;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class Plat {
    String designProf, refProd, impriment;

    int idProd, famProd, typeProd;
    BigDecimal tva, prixProdVente;
    Bitmap imageProd;

    public Plat() {
    }

    public Plat(int idProd, String désignProf, String refProd, int famProd, int typeProd, BigDecimal tva, BigDecimal prixProdVente,
                Bitmap imageProd, String impriment) {
        this.idProd = idProd;
        this.designProf = désignProf;
        this.refProd = refProd;
        this.famProd = famProd;
        this.impriment = impriment;
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

    public String getDesignProf() {
        return designProf;
    }

    public void setDesignProf(String designProf) {
        this.designProf = designProf;
    }
    public String getImpriment() {
        return impriment;
    }

    public void setImpriment(String impriment) {
        this.impriment = impriment;
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
