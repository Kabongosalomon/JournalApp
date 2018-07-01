package com.salomon.tehilah.journalapp.noteslist.entities;

/**
 * Created by Salomon KABONGO on 27-Jun-18.
 */
public class User {
    private String email, password;


    public  User(){
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }




    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}

