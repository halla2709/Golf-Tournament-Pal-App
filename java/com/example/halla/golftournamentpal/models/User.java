package com.example.halla.golftournamentpal.models;

/**
 * Created by Halla on 21-Feb-17.
 */

public class User {
    private long mSocial;
    private String mPassword;

    public User(long social, String password) {
        mSocial = social;
        mPassword = password;
    }

    public long getSocial() {
        return mSocial;
    }

    public void setSocial(long social) {
        mSocial = social;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
