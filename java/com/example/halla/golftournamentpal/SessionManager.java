package com.example.halla.golftournamentpal;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Halla on 26-Feb-17.
 */

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;

    public static final String PREF_NAME = "sessPrefs";

    private final String SOCIAL = "userSocial";
    private final String PASS = "userPass";

    public SessionManager(Context context) {
        this.mContext = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void startSession(Long userSocial, String userPassword) {
        editor.putLong(SOCIAL, userSocial);
        editor.putString(PASS, userPassword);
        editor.commit();
    }

    public Long getSessionUserSocial() {
        return pref.getLong(SOCIAL, 0);
    }

    public void endSession() {
        editor.clear();
        editor.commit();
    }
}
