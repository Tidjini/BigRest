package com.soft.big.bigrest.Model;

import com.soft.big.bigrest.Adapters.TableAdapter;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class Table {
    private int id, numero, maxNumber;
    private String remarque;

    private String name;
    private TableAdapter.State state;

    public Table() {
    }

    public Table(int id, int numero, int maxNumber, String remarque, String name, TableAdapter.State state) {
        this.id = id;
        this.numero = numero;
        this.maxNumber = maxNumber;
        this.remarque = remarque;
        this.name = name;
        this.state = state;
    }

    public TableAdapter.State getState() {
        return state;
    }

    public void setState(TableAdapter.State state) {
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
