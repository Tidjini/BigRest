package com.soft.big.bigrest.Model;

import com.soft.big.bigrest.Adapters.TableAdapter;
import com.soft.big.bigrest.Behaviors.Constants;
import com.soft.big.bigrest.Behaviors.Utils;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class Table {
    private int id, numero, maxNumber;
    private String remarque;

    private int orderOpenId;



    private String name;
    private Utils.TableState state;

    public Table() {
    }

    public Table(int id, int numero, int maxNumber, String remarque, String name, Utils.TableState  state) {
        this.id = id;
        this.numero = numero;
        this.maxNumber = maxNumber;
        this.remarque = remarque;
        this.name = name;
        this.state = state;
    }

    public Table(int id, int numero, int maxNumber, String remarque, int orderOpenId, String name, Utils.TableState  state) {
        this.id = id;
        this.numero = numero;
        this.maxNumber = maxNumber;
        this.remarque = remarque;
        this.orderOpenId = orderOpenId;
        this.name = name;
        this.state = state;
    }

    public int getOrderOpenId() {
        return orderOpenId;
    }

    public void setOrderOpenId(int orderOpenId) {
        this.orderOpenId = orderOpenId;
    }
    public Utils.TableState getState() {
        return state;
    }

    public void setState(Utils.TableState  state) {
        this.state = state;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
