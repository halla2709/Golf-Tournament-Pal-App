package com.example.halla.golftournamentpal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Bracket implements Parcelable {

    private List<Match> mMatch;
    private List<Golfer> mPlayers;
    private String mName;

    public Bracket(List<Match> match, List<Golfer> players, String name) {
        mMatch = match;
        mPlayers = players;
        mName = name;
    }

    public List<Match> getMatch() {
        return mMatch;
    }

    public void setMatch(List<Match> match) {
        mMatch = match;
    }

    public List<Golfer> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<Golfer> players) {
        mPlayers = players;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    protected Bracket(Parcel in) {
        if (in.readByte() == 0x01) {
            mMatch = new ArrayList<Match>();
            in.readList(mMatch, Match.class.getClassLoader());
        } else {
            mMatch = null;
        }
        if (in.readByte() == 0x01) {
            mPlayers = new ArrayList<Golfer>();
            in.readList(mPlayers, Golfer.class.getClassLoader());
        } else {
            mPlayers = null;
        }
        mName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mMatch == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mMatch);
        }
        if (mPlayers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mPlayers);
        }
        dest.writeString(mName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Bracket> CREATOR = new Parcelable.Creator<Bracket>() {
        @Override
        public Bracket createFromParcel(Parcel in) {
            return new Bracket(in);
        }

        @Override
        public Bracket[] newArray(int size) {
            return new Bracket[size];
        }
    };
}