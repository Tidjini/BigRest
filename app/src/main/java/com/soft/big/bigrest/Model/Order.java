package com.soft.big.bigrest.Model;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class Order {

    private int id, idTable, idUser;
    private double totalHt;
    private boolean effected;


    private boolean close;
    private String remarque;

    public Order() {
    }

    public Order(int id, int idTable, int idUser, double totalHt, boolean effected, boolean close, String remarque) {

        this.id = id;
        this.idTable = idTable;
        this.idUser = idUser;
        this.totalHt = totalHt;
        this.effected = effected;
        this.close = close;
        this.remarque = remarque;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }


    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public double getTotalHt() {
        return totalHt;
    }

    public void setTotalHt(double totalHt) {
        this.totalHt = totalHt;
    }

    public boolean isEffected() {
        return effected;
    }

    public void setEffected(boolean effected) {
        this.effected = effected;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }
}
