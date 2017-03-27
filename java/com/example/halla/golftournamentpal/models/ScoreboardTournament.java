package com.example.halla.golftournamentpal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Halla on 16-Feb-17.
 */

public class ScoreboardTournament extends Tournament implements Parcelable {

    private int[][] mScores;
    private int mNumberOfRounds;
    private List<Scorecard> mScorecards;

    public ScoreboardTournament(Long id, String course, String name, List<Golfer> players, Date startDate, int[][] scores, int numberOfRounds, List<Scorecard> scorecards) {
        super(id, course, name, players, startDate);
        mScores = scores;
        mNumberOfRounds = numberOfRounds;
        mScorecards = scorecards;
    }

    public ScoreboardTournament() {
        super();
        mScorecards = new ArrayList<>();
    }

    public int[][] getScores() {
        return mScores;
    }

    public void setScores(int[][] scores) {
        mScores = scores;
    }

    public int getNumberOfRounds() {
        return mNumberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        mNumberOfRounds = numberOfRounds;
    }

    public List<Scorecard> getScorecards() {
        return mScorecards;
    }

    public void setScorecards(List<Scorecard> scorecards) {
        mScorecards = scorecards;
    }

    protected ScoreboardTournament(Parcel in) {
        super(in);
        mNumberOfRounds = in.readInt();
        if (in.readByte() == 0x01) {
            mScorecards = new ArrayList<Scorecard>();
            in.readList(mScorecards, Scorecard.class.getClassLoader());
        } else {
            mScorecards = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mNumberOfRounds);
        if (mScorecards == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mScorecards);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ScoreboardTournament> CREATOR = new Parcelable.Creator<ScoreboardTournament>() {
        @Override
        public ScoreboardTournament createFromParcel(Parcel in) {
            return new ScoreboardTournament(in);
        }

        @Override
        public ScoreboardTournament[] newArray(int size) {
            return new ScoreboardTournament[size];
        }
    };
}