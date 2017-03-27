package com.example.halla.golftournamentpal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class PlayOffTree implements Parcelable {



    private List<PlayOffRound> mRounds;

    public PlayOffTree(List<PlayOffRound> rounds) {
        mRounds = rounds;
    }

    public List<PlayOffRound> getRounds() {
        return mRounds;
    }

    public void setRounds(List<PlayOffRound> rounds) {
        mRounds = rounds;
    }

    protected PlayOffTree(Parcel in) {
        if (in.readByte() == 0x01) {
            mRounds = new ArrayList<PlayOffRound>();
            in.readList(mRounds, PlayOffRound.class.getClassLoader());
        } else {
            mRounds = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mRounds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mRounds);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PlayOffTree> CREATOR = new Parcelable.Creator<PlayOffTree>() {
        @Override
        public PlayOffTree createFromParcel(Parcel in) {
            return new PlayOffTree(in);
        }

        @Override
        public PlayOffTree[] newArray(int size) {
            return new PlayOffTree[size];
        }
    };
}