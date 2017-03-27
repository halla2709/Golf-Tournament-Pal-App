package com.example.halla.golftournamentpal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Scorecard implements Parcelable {

    private Golfer mPlayer;
    private String mCourse;
    private int mNumberOfRounds;
    private List<Round> mRounds;

    public Scorecard(Golfer player, String course, int numberOfRounds, List<Round> rounds) {
        mPlayer = player;
        mCourse = course;
        mNumberOfRounds = numberOfRounds;
        mRounds = rounds;
    }

    public Golfer getPlayer() {
        return mPlayer;
    }

    public void setPlayer(Golfer player) {
        mPlayer = player;
    }

    public String getCourse() {
        return mCourse;
    }

    public void setCourse(String course) {
        mCourse = course;
    }

    public int getNumberOfRounds() {
        return mNumberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        mNumberOfRounds = numberOfRounds;
    }

    public List<Round> getRounds() {
        return mRounds;
    }

    public void setRounds(List<Round> rounds) {
        mRounds = rounds;
    }

    protected Scorecard(Parcel in) {
        mPlayer = (Golfer) in.readValue(Golfer.class.getClassLoader());
        mCourse = in.readString();
        mNumberOfRounds = in.readInt();
        if (in.readByte() == 0x01) {
            mRounds = new ArrayList<Round>();
            in.readList(mRounds, Round.class.getClassLoader());
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
        dest.writeValue(mPlayer);
        dest.writeString(mCourse);
        dest.writeInt(mNumberOfRounds);
        if (mRounds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mRounds);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Scorecard> CREATOR = new Parcelable.Creator<Scorecard>() {
        @Override
        public Scorecard createFromParcel(Parcel in) {
            return new Scorecard(in);
        }

        @Override
        public Scorecard[] newArray(int size) {
            return new Scorecard[size];
        }
    };
}