package com.example.halla.golftournamentpal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Golfer implements Parcelable {

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

    protected Golfer(Parcel in) {
        mName = in.readString();
        mSocial = in.readLong();
        mHandicap = in.readDouble();
        mEmail = in.readString();
        if (in.readByte() == 0x01) {
            mFriends = new ArrayList<Golfer>();
            in.readList(mFriends, Golfer.class.getClassLoader());
        } else {
            mFriends = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeLong(mSocial);
        dest.writeDouble(mHandicap);
        dest.writeString(mEmail);
        if (mFriends == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mFriends);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Golfer> CREATOR = new Parcelable.Creator<Golfer>() {
        @Override
        public Golfer createFromParcel(Parcel in) {
            return new Golfer(in);
        }

        @Override
        public Golfer[] newArray(int size) {
            return new Golfer[size];
        }
    };
}