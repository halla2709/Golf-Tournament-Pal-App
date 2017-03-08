package com.example.halla.golftournamentpal.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Golfer {

    private String mName;
    private long mSocial;
    private double mHandicap;
    private String mEmail;
    private List<Golfer> mFriends;

    public Golfer() {
        mFriends = new ArrayList<>();
    }

    public Golfer(String name, long social, double handicap, String email, List<Golfer> friends) {
        mName = name;
        mSocial = social;
        mHandicap = handicap;
        mEmail = email;
        mFriends = friends;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public long getSocial() {
        return mSocial;
    }

    public void setSocial(long social) {
        mSocial = social;
    }

    public double getHandicap() {
        return mHandicap;
    }

    public void setHandicap(double handicap) {
        mHandicap = handicap;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public List<Golfer> getFriends() {
        return mFriends;
    }

    public void setFriends(List<Golfer> friends) {
        mFriends = friends;
    }

    public void addFriend(Golfer friend){
        mFriends.add(friend);
    }
}
