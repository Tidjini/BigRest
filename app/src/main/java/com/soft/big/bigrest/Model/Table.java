package com.soft.big.bigrest.Model;


import com.soft.big.bigrest.Behaviors.Utils;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class Table {
    private int id;
    private String orderOpenId;
    private String libelle;
    private Utils.TableState etat;


    public Table() {
    }

    public Table(int id, String orderOpenId, String libelle, Utils.TableState etat) {
        this.id = id;
        this.orderOpenId = orderOpenId;
        this.libelle = libelle;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getOrderOpenId() {
        return orderOpenId;
    }

    public void setOrderOpenId(String orderOpenId) {
        this.orderOpenId = orderOpenId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Utils.TableState getEtat() {
        return etat;
    }

    public void setEtat(Utils.TableState etat) {
        this.etat = etat;
    }
}
