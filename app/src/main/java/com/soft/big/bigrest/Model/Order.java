package com.soft.big.bigrest.Model;


import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Tidjini on 09/11/2017.
 */

/**
 * Table id == table num why ??  int != varchar
 */
public class Order {

    String  nomClient; //code & nom client = 001, Client diveres,
    Date dateCommande;
    BigDecimal htCmd, tvaCmd, ttcCmd,  restePaie;
    int serverCode, userCreator, userModification,  codeClient, idCmd, etatCmd;
    String  serverName;//user name, user name
    int table;
    Date dateCreation;
    Date dateModification;
    boolean paymentEtat;
    int idCaisse;

    public Order() {
    }

    public Order(int idCmd, int codeClient, String nomClient,
                 Date dateCommande, BigDecimal htCmd, BigDecimal tvaCmd,
                 BigDecimal ttcCmd, boolean paymentEtat, int etatCmd, int serverCode,
                 int table, Date dateCreation, int userCreator, Date dateModification,
                 int userModification)
    {
        this.idCmd = idCmd;
        this.codeClient = codeClient;
        this.nomClient = nomClient;
        this.dateCommande = dateCommande;
        this.htCmd = htCmd;
        this.tvaCmd = tvaCmd;
        this.ttcCmd = ttcCmd;
        this.paymentEtat = paymentEtat;
        this.etatCmd = etatCmd;
        this.serverCode = serverCode;
        this.table = table;
        this.dateCreation = dateCreation;
        this.userCreator = userCreator;
        this.dateModification = dateModification;
        this.userModification = userModification;

    }


    public BigDecimal getRestePaie() {
        return restePaie;
    }

    public void setRestePaie(BigDecimal restePaie) {
        this.restePaie = restePaie;
    }

    public int getIdCaisse() {
        return idCaisse;
    }

    public void setIdCaisse(int idCaisse) {
        this.idCaisse = idCaisse;
    }

    public int getIdCmd() {
        return idCmd;
    }

    public void setIdCmd(int idCmd) {
        this.idCmd = idCmd;
    }

    public int getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(int codeClient) {
        this.codeClient = codeClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public BigDecimal getHtCmd() {
        return htCmd;
    }

    public void setHtCmd(BigDecimal htCmd) {
        this.htCmd = htCmd;
    }

    public BigDecimal getTvaCmd() {
        return tvaCmd;
    }

    public void setTvaCmd(BigDecimal tvaCmd) {
        this.tvaCmd = tvaCmd;
    }

    public BigDecimal getTtcCmd() {
        return ttcCmd;
    }

    public void setTtcCmd(BigDecimal ttcCmd) {
        this.ttcCmd = ttcCmd;
    }

    public boolean getPaymentCmd() {
        return paymentEtat;
    }

    public void setPaymentCmd(boolean paymentEtat) {
        this.paymentEtat = paymentEtat;
    }

    public int getEtatCmd() {
        return etatCmd;
    }

    public void setEtatCmd(int etatCmd) {
        this.etatCmd = etatCmd;
    }

    public int getServerCode() {
        return serverCode;
    }

    public void setServerCode(int serverCode) {
        this.serverCode = serverCode;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(int userCreator) {
        this.userCreator = userCreator;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public int getUserModification() {
        return userModification;
    }

    public void setUserModification(int userModification) {
        this.userModification = userModification;
    }
}
