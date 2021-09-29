package com.example.improvedhouse;

/**
 * Created by Joanna Ruth on 28-Jun-18.
 */

public class User {
    String Password;

    public User() {
    }

    public User(String password) {
        this.Password = password;

    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password){
            this.Password=password;

    }


}
