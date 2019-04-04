package com.soft.big.bigrest.Model;

/**
 * Created by Tidjini on 09/11/2017.
 */

public class User {
    private String userName, password;
    private int id;

    public User() {
    }

    public int getUserId() {
        return id;
    }

    public void setUserId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
