package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.Round;
import com.example.halla.golftournamentpal.models.ScoreboardTournament;
import com.example.halla.golftournamentpal.models.Scorecard;
import com.example.halla.golftournamentpal.models.Tournament;

import java.io.IOException;

public class ScoreInputViewActivity extends AppCompatActivity {

    private Button mCreateButton;
    private SessionManager mSessionManager;
    private ScoreboardTournament mScoreboardTournament;
    private Scorecard scorecard;
    private Round mRound;
    private final static String ID =  "id";
    private final static String SOCIAL = "social";
    private final static String ROUND_NUM = "roundNum";
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
    private int mTotal;
    private Long mId;
    private Long mUserSocial;
    private int mRoundNum;
    private Tournament mTournament;

    public void save (View view){
        mCreateButton = (Button) findViewById(R.id.savebutton);
        setResults();
    }

    public static Intent newIntent(Context packageContext, ScoreboardTournament tournament, Scorecard scorecard, Round round, int roundNum) {
        Intent i = new Intent(packageContext, ScoreInputViewActivity.class);
        i.putExtra(SOCIAL, scorecard.getPlayer().getSocial());
        i.putExtra(ID, tournament.getId());
        i.putExtra(ROUND_NUM, roundNum);

        return i;
    }

    public void setResults(){
        EditText mH1view = (EditText) findViewById(R.id.h1);
        EditText mH2view = (EditText) findViewById(R.id.h2);
        EditText mH3view = (EditText) findViewById(R.id.h3);
        EditText mH4view = (EditText) findViewById(R.id.h4);
        EditText mH5view = (EditText) findViewById(R.id.h5);
        EditText mH6view = (EditText) findViewById(R.id.h6);
        EditText mH7view = (EditText) findViewById(R.id.h7);
        EditText mH8view = (EditText) findViewById(R.id.h8);
        EditText mH9view = (EditText) findViewById(R.id.h9);
        EditText mH10view = (EditText) findViewById(R.id.h10);
        EditText mH11view = (EditText) findViewById(R.id.h11);
        EditText mH12view = (EditText) findViewById(R.id.h12);
        EditText mH13view = (EditText) findViewById(R.id.h13);
        EditText mH14view = (EditText) findViewById(R.id.h14);
        EditText mH15view = (EditText) findViewById(R.id.h15);
        EditText mH16view = (EditText) findViewById(R.id.h16);
        EditText mH17view = (EditText) findViewById(R.id.h17);
        EditText mH18view = (EditText) findViewById(R.id.h18);


        if(mH1view.getText().length() < 1 || Integer.parseInt(mH1view.getText().toString()) < 1){
            makeToast();
        }else if(mH2view.getText().length() < 1 || Integer.parseInt(mH2view.getText().toString()) < 1){
            makeToast();
        }else if(mH3view.getText().length() < 1 || Integer.parseInt(mH3view.getText().toString()) < 1){
            makeToast();
        }else if(mH4view.getText().length() < 1 || Integer.parseInt(mH4view.getText().toString()) < 1){
            makeToast();
        }else if(mH5view.getText().length() < 1 || Integer.parseInt(mH5view.getText().toString()) < 1){
            makeToast();
        }else if(mH6view.getText().length() < 1 || Integer.parseInt(mH6view.getText().toString()) < 1){
            makeToast();
        }else if(mH7view.getText().length() < 1 || Integer.parseInt(mH7view.getText().toString()) < 1){
            makeToast();
        }else if(mH8view.getText().length() < 1 || Integer.parseInt(mH8view.getText().toString()) < 1){
            makeToast();
        }else if(mH9view.getText().length() < 1 || Integer.parseInt(mH9view.getText().toString()) < 1){
            makeToast();
        }else if(mH10view.getText().length() < 1 || Integer.parseInt(mH10view.getText().toString()) < 1){
            makeToast();
        }else if(mH11view.getText().length() < 1 || Integer.parseInt(mH11view.getText().toString()) < 1){
            makeToast();
        }else if(mH12view.getText().length() < 1 || Integer.parseInt(mH12view.getText().toString()) < 1){
            makeToast();
        }else if(mH13view.getText().length() < 1 || Integer.parseInt(mH13view.getText().toString()) < 1){
            makeToast();
        }else if(mH14view.getText().length() < 1 || Integer.parseInt(mH14view.getText().toString()) < 1){
            makeToast();
        }else if(mH15view.getText().length() < 1 || Integer.parseInt(mH15view.getText().toString()) < 1){
            makeToast();
        }else if(mH16view.getText().length() < 1 || Integer.parseInt(mH16view.getText().toString()) < 1){
            makeToast();
        }else if(mH17view.getText().length() < 1 || Integer.parseInt(mH17view.getText().toString()) < 1){
            makeToast();
        }else if(mH18view.getText().length() < 1 || Integer.parseInt(mH18view.getText().toString()) < 1){
            makeToast();
        }
        else {
            mH1 = Integer.parseInt(((EditText) findViewById(R.id.h1)).getText().toString());
            mH2 = Integer.parseInt(((EditText) findViewById(R.id.h2)).getText().toString());
            mH3 = Integer.parseInt(((EditText) findViewById(R.id.h3)).getText().toString());
            mH4 = Integer.parseInt(((EditText) findViewById(R.id.h4)).getText().toString());
            mH5 = Integer.parseInt(((EditText) findViewById(R.id.h5)).getText().toString());
            mH6 = Integer.parseInt(((EditText) findViewById(R.id.h6)).getText().toString());
            mH7 = Integer.parseInt(((EditText) findViewById(R.id.h7)).getText().toString());
            mH8 = Integer.parseInt(((EditText) findViewById(R.id.h8)).getText().toString());
            mH9 = Integer.parseInt(((EditText) findViewById(R.id.h9)).getText().toString());
            mH10 = Integer.parseInt(((EditText) findViewById(R.id.h10)).getText().toString());
            mH11 = Integer.parseInt(((EditText) findViewById(R.id.h11)).getText().toString());
            mH12 = Integer.parseInt(((EditText) findViewById(R.id.h12)).getText().toString());
            mH13 = Integer.parseInt(((EditText) findViewById(R.id.h13)).getText().toString());
            mH14 = Integer.parseInt(((EditText) findViewById(R.id.h14)).getText().toString());
            mH15 = Integer.parseInt(((EditText) findViewById(R.id.h15)).getText().toString());
            mH16 = Integer.parseInt(((EditText) findViewById(R.id.h16)).getText().toString());
            mH17 = Integer.parseInt(((EditText) findViewById(R.id.h17)).getText().toString());
            mH18 = Integer.parseInt(((EditText) findViewById(R.id.h18)).getText().toString());

            if(new Networker().checkConnectivity(getApplicationContext())) {
                ScoreInputViewActivity.setRoundTask task = new ScoreInputViewActivity.setRoundTask();
                task.execute();
            }
        }
    }

    private void makeToast() {
        Toast.makeText(ScoreInputViewActivity.this, "Please make sure to fill out all scores correctly!", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_input_view);

        // Check if user is logged in
        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(ScoreInputViewActivity.this);
            startActivity(i);
        }
        mId = getIntent().getLongExtra(ID, 0);
        mUserSocial = getIntent().getLongExtra(SOCIAL, 0);
        mRoundNum = getIntent().getIntExtra(ROUND_NUM, 0);
    }

    private class setRoundTask extends AsyncTask<Void, Void, Round> {

        @Override
        protected Round doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            try {
                mTournament = new Networker().setRound(mId,
                        mUserSocial,
                        mRoundNum,
                        mH1, mH2, mH3, mH4, mH5,
                        mH6, mH7, mH8, mH9, mH10,
                        mH11, mH12, mH13, mH14, mH15,
                        mH16, mH17, mH18);
                Log.i("TOURNAMENT", mTournament.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return mRound;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAGG", "Going to fetch...");
        }

        @Override
        protected void onPostExecute(Round round) {
            super.onPostExecute(round);
            Intent i = ScoreboardActivity.newIntent(getApplicationContext(), (ScoreboardTournament) mTournament);
            startActivity(i);
            Log.i("TAGG", "Done");

        }
    }
}
