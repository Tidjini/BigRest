package com.soft.big.bigrest.Model;

/**
 * Created by Tidjini on 10/11/2017.
 */

public class DetailsOrder {

    String platName, platDescription;
    double price;
    int imageId, total;

    public DetailsOrder(String platName, String platDescription, double price, int imageId, int total) {
        this.platName = platName;
        this.platDescription = platDescription;
        this.price = price;
        this.imageId = imageId;
        this.total = total;
    }

    public DetailsOrder() {
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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
