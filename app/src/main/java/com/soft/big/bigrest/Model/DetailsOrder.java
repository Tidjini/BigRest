package com.soft.big.bigrest.Model;

/**
 * Created by Tidjini on 10/11/2017.
 */

public class DetailsOrder {


    int id, cmdId, platId;
    String platName, platDescription;
    double price;
    int total;


    public DetailsOrder(int id, int idPlat, String platName, int total, double price) {
        this.platName = platName;
        this.price = price;
        this.total = total;
        this.id = id;
        this.platId = idPlat;
    }



    public DetailsOrder(int cmdId, int platId, int total) {
        this.cmdId = cmdId;
        this.platId = platId;
        this.total = total;
    }


    public DetailsOrder() {
    }


    public int getCmdId() {
        return cmdId;
    }

    public void setCmdId(int cmdId) {
        this.cmdId = cmdId;
    }

    public int getPlatId() {
        return platId;
    }

    public void setPlatId(int platId) {
        this.platId = platId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }


    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getPlatDescription() {
        return platDescription;
    }

    public void setPlatDescription(String platDescription) {
        this.platDescription = platDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getTotalHt(){
        return this.total * this.price;
    }
}
