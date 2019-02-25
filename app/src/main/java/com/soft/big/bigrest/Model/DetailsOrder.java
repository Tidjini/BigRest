package com.soft.big.bigrest.Model;

import java.math.BigDecimal;

/**
 * Created by Tidjini on 10/11/2017.
 */

public class DetailsOrder {


    int NbrLigne, codeProd, numCmd;
    String  libeProd;
    BigDecimal prixProd, qttProd, tvaArt,remArt;
    String typPrd;
    int idMag;

    public DetailsOrder() {
    }

    public int getNbrLigne() {
        return NbrLigne;
    }

    public void setNbrLigne(int nbrLigne) {
        NbrLigne = nbrLigne;
    }

    public int getNumCmd() {
        return numCmd;
    }

    public void setNumCmd(int numCmd) {
        this.numCmd = numCmd;
    }

    public int getCodeProd() {
        return codeProd;
    }

    public void setCodeProd(int codeProd) {
        this.codeProd = codeProd;
    }

    public String getLibeProd() {
        return libeProd;
    }

    public void setLibeProd(String libeProd) {
        this.libeProd = libeProd;
    }

    public BigDecimal getPrixProd() {
        return prixProd;
    }

    public void setPrixProd(BigDecimal prixProd) {
        this.prixProd = prixProd;
    }

    public BigDecimal getQttProd() {
        return qttProd;
    }

    public void setQttProd(BigDecimal qttProd) {
        this.qttProd = qttProd;
    }

    public BigDecimal getTvaArt() {
        return tvaArt;
    }

    public void setTvaArt(BigDecimal tvaArt) {
        this.tvaArt = tvaArt;
    }

    public BigDecimal getMttvaArt() {
        BigDecimal tvaPercent = tvaArt.divide(BigDecimal.valueOf(100));
        return tvaPercent.multiply(getMtnetArt());
    }



    public BigDecimal getRemArt() {
        return remArt;
    }

    public void setRemArt(BigDecimal remArt) {
        this.remArt = remArt;
    }

    public BigDecimal getMtremArt() {
        return remArt.multiply(prixProd.multiply(qttProd));
    }


    public BigDecimal getMtnetArt() {
        return qttProd.multiply(prixProd);
    }


    public String getTypPrd() {
        return typPrd;
    }

    public void setTypPrd(String typPrd) {
        this.typPrd = typPrd;
    }

    public int getIdMag() {
        return idMag;
    }

    public void setIdMag(int idMag) {
        this.idMag = idMag;
    }

    public DetailsOrder(int nbrLigne, int numCmd, int codeProd, String libeProd, BigDecimal prixProd, BigDecimal qttProd, BigDecimal tvaArt,  BigDecimal remArt, int idMag) {
        NbrLigne = nbrLigne;
        this.numCmd = numCmd;
        this.codeProd = codeProd;
        this.libeProd = libeProd;
        this.prixProd = prixProd;
        this.qttProd = qttProd;
        this.tvaArt = tvaArt;
        this.remArt = remArt;

        //this.typPrd = typPrd;
        this.idMag = idMag;
    }
}
