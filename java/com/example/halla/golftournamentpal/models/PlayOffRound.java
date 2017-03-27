package com.example.halla.golftournamentpal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class PlayOffRound implements Parcelable {

    private int mRound;
    private List<Match> mMatches;

    public PlayOffRound(int round, List<Match> matches) {
        mRound = round;
        mMatches = matches;
    }

    public List<Match> getMatches() {
        return mMatches;
    }

    public void setMatches(List<Match> matches) {
        mMatches = matches;
    }

    public int getRound() {
        return mRound;
    }

    public void setRound(int round) {
        mRound = round;
    }

    protected PlayOffRound(Parcel in) {
        mRound = in.readInt();
        if (in.readByte() == 0x01) {
            mMatches = new ArrayList<Match>();
            in.readList(mMatches, Match.class.getClassLoader());
        } else {
            mMatches = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mRound);
        if (mMatches == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mMatches);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PlayOffRound> CREATOR = new Parcelable.Creator<PlayOffRound>() {
        @Override
        public PlayOffRound createFromParcel(Parcel in) {
            return new PlayOffRound(in);
        }

        @Override
        public PlayOffRound[] newArray(int size) {
            return new PlayOffRound[size];
        }
    };
}