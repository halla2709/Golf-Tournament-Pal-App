package com.example.halla.golftournamentpal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.halla.golftournamentpal.models.Golfer;

import java.util.concurrent.ExecutionException;

/**
 * Created by Halla on 26-Feb-17.
 */

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;

    public static final String PREF_NAME = "sessPrefs";

    private final String SOCIAL = "userSocial";
    private final String NAME = "userName";
    private final String HANDICAP = "userHandicap";
    private final String EMAIL = "userEmail";

    Long mUserSocial;
    String mUserPassword;
    Golfer mGolfer;
    boolean mFinished = false;

    public SessionManager(Context context) {
        this.mContext = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public boolean startSession(Long userSocial, String userPassword){
        mUserSocial = userSocial;
        mUserPassword = userPassword;

        LogInTask task = new LogInTask();
        task.execute();
        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(mGolfer != null){
            editor.putLong(SOCIAL, mGolfer.getSocial());
            editor.putString(NAME, mGolfer.getName());
            editor.putFloat(HANDICAP, (float) mGolfer.getHandicap());
            editor.putString(EMAIL, mGolfer.getEmail());
            editor.commit();
            return true;
        }

        return false;
    }

    public void startRegisterSession(Golfer golfer){

        editor.putLong(SOCIAL, golfer.getSocial());
        editor.putString(NAME, golfer.getName());
        editor.putFloat(HANDICAP, (float) golfer.getHandicap());
        editor.putString(EMAIL, golfer.getEmail());
        editor.commit();
    }


    public Long getSessionUserSocial() {
        return pref.getLong(SOCIAL, 0);
    }

    public String getSessionUserName() {
        return pref.getString(NAME, "");
    }

    public double getSessionUserHandicap() {
        double toreturn = JsonParser.doubleRounder((double) pref.getFloat(HANDICAP, 0));

        return toreturn;
    }

    public String getSessionUserEmail() {
        return pref.getString(EMAIL, "");
    }

    public void endSession() {
        editor.clear();
        editor.commit();
    }


    private class LogInTask extends AsyncTask<Void, Void, Golfer> {

        @Override
        protected Golfer doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");

            Golfer golfer = new Networker().logInGolfer(mUserSocial, mUserPassword);
            mGolfer = golfer;
            return golfer;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mFinished = false;
            Log.i("TAGG", "Going to fetch...");
        }

        @Override
        protected void onPostExecute(Golfer golfer) {
            super.onPostExecute(golfer);
            Log.i("TAGG", "Done");

        }
    }
}
