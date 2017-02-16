package com.example.halla.golftournamentpal.models;

/**
 * Created by Halla on 16-Feb-17.
 */

public class Round {

    private int mH1;
    private int mH2;
    private int mH3;
    private int mH4;
    private int mH5;
    private int mH6;
    private int mH7;
    private int mH8;
    private int mH9;
    private int mH10;
    private int mH11;
    private int mH12;
    private int mH13;
    private int mH14;
    private int mH15;
    private int mH16;
    private int mH17;
    private int mH18;
    private int total;
    private int[] myScores;

    public Round(int[] myScores, int total, int h18, int h17, int h16, int h15, int h14, int h13, int h12, int h11, int h10, int h9, int h8, int h7, int h6, int h5, int h4, int h3, int h2, int h1) {
        this.myScores = myScores;
        this.total = total;
        mH18 = h18;
        mH17 = h17;
        mH16 = h16;
        mH15 = h15;
        mH14 = h14;
        mH13 = h13;
        mH12 = h12;
        mH11 = h11;
        mH10 = h10;
        mH9 = h9;
        mH8 = h8;
        mH7 = h7;
        mH6 = h6;
        mH5 = h5;
        mH4 = h4;
        mH3 = h3;
        mH2 = h2;
        mH1 = h1;
    }

    public int[] getMyScores() {
        return myScores;
    }

    public void setMyScores(int[] myScores) {
        this.myScores = myScores;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getH18() {
        return mH18;
    }

    public void setH18(int h18) {
        mH18 = h18;
    }

    public int getH17() {
        return mH17;
    }

    public void setH17(int h17) {
        mH17 = h17;
    }

    public int getH16() {
        return mH16;
    }

    public void setH16(int h16) {
        mH16 = h16;
    }

    public int getH15() {
        return mH15;
    }

    public void setH15(int h15) {
        mH15 = h15;
    }

    public int getH14() {
        return mH14;
    }

    public void setH14(int h14) {
        mH14 = h14;
    }

    public int getH13() {
        return mH13;
    }

    public void setH13(int h13) {
        mH13 = h13;
    }

    public int getH12() {
        return mH12;
    }

    public void setH12(int h12) {
        mH12 = h12;
    }

    public int getH11() {
        return mH11;
    }

    public void setH11(int h11) {
        mH11 = h11;
    }

    public int getH10() {
        return mH10;
    }

    public void setH10(int h10) {
        mH10 = h10;
    }

    public int getH9() {
        return mH9;
    }

    public void setH9(int h9) {
        mH9 = h9;
    }

    public int getH8() {
        return mH8;
    }

    public void setH8(int h8) {
        mH8 = h8;
    }

    public int getH7() {
        return mH7;
    }

    public void setH7(int h7) {
        mH7 = h7;
    }

    public int getH6() {
        return mH6;
    }

    public void setH6(int h6) {
        mH6 = h6;
    }

    public int getH5() {
        return mH5;
    }

    public void setH5(int h5) {
        mH5 = h5;
    }

    public int getH4() {
        return mH4;
    }

    public void setH4(int h4) {
        mH4 = h4;
    }

    public int getH3() {
        return mH3;
    }

    public void setH3(int h3) {
        mH3 = h3;
    }

    public int getH2() {
        return mH2;
    }

    public void setH2(int h2) {
        mH2 = h2;
    }

    public int getH1() {
        return mH1;
    }

    public void setH1(int h1) {
        mH1 = h1;
    }
}
