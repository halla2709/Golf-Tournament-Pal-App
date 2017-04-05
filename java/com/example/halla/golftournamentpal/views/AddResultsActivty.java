package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.halla.golftournamentpal.Networker;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.Bracket;
import com.example.halla.golftournamentpal.models.Golfer;
import com.example.halla.golftournamentpal.models.Match;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;

public class AddResultsActivty extends AppCompatActivity {

    private SessionManager mSessionManager;
    private Match mMatch;
    private static Bracket sBracket;
    private MatchPlayTournament mMatchPlayTournament;
    private Long mWinner;
    private String mCheckFlag = "notBracket";

    EditText mResults;
    Button mSaveResults;
    RadioButton mPlayer1Won;
    RadioButton mPlayer2Won;

    private static final String MATCH = "match";
    private static final String MATCHPLAY = "matchPlayTournament";
    private static final String BRACKET_ID = "bracketID";
    private static final String MATCH_ID = "matchID";
    private static final String TOURNAMENT_ID = "tournamentID";
    private static final String FLAG = "flag";
    private static final String ROUNDNUMBER = "roundNumber";
    private String mResultText = "";


    public static Intent newIntent(Context packageContext, Match match,
                                   Long tournamentID,
                                   Long bracketID,
                                   String flag) {
        Intent intent = new Intent(packageContext, AddResultsActivty.class);
        intent.putExtra(MATCH, match);
        intent.putExtra(BRACKET_ID, bracketID);
        intent.putExtra(MATCH_ID, match.getID());
        intent.putExtra(TOURNAMENT_ID, tournamentID);
        intent.putExtra(FLAG, flag);
        Log.i("opening results", Long.toString(tournamentID));
        return intent;
    }

    public static Intent newIntent(Context packageContext, Match match,
                                   Long tournamentID,
                                   int roundNumber) {
        Intent intent = new Intent(packageContext, AddResultsActivty.class);
        intent.putExtra(MATCH, match);
        intent.putExtra(TOURNAMENT_ID, tournamentID);
        intent.putExtra(ROUNDNUMBER, roundNumber);
        intent.putExtra(FLAG, "IAmFromPlayOff");
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Check if user is logged in
        mSessionManager = new SessionManager(getApplicationContext());
        if (mSessionManager.getSessionUserSocial() == 0) {
            Intent i = LogInActivity.newIntent(AddResultsActivty.this);
            startActivity(i);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_results);

        mMatch = getIntent().getParcelableExtra(MATCH);
        mMatchPlayTournament = getIntent().getParcelableExtra(MATCHPLAY);

        mResults = (EditText) findViewById(R.id.results);
        mPlayer1Won = (RadioButton) findViewById(R.id.player1won);
        mPlayer2Won = (RadioButton) findViewById(R.id.player2won);

        mPlayer1Won.setText(mMatch.getPlayers().get(0).getName());
        mPlayer2Won.setText(mMatch.getPlayers().get(1).getName());

        mCheckFlag = getIntent().getStringExtra(FLAG);

        mSaveResults = (Button) findViewById(R.id.saveresultsbutton);
        mSaveResults.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(mResults.getText().length() <= 0)
                    Toast.makeText(getApplicationContext(), "Please enter result", Toast.LENGTH_SHORT)
                            .show();
                else{
                    if (mPlayer1Won.isChecked()) {
                        // Player1Won
                        mResultText = mResults.getText().toString();
                        mWinner = mMatch.getPlayers().get(0).getSocial();
                        SaveResultTask task = new SaveResultTask();
                        task.execute();

                    } else if (mPlayer2Won.isChecked()) {
                        // Player2won
                        mResultText = mResults.getText().toString();
                        mWinner = mMatch.getPlayers().get(1).getSocial();
                        SaveResultTask task = new SaveResultTask();
                        task.execute();

                    } else {
                        //No one has been marked as winner
                        Toast.makeText(getApplicationContext(), "Please choose a winner", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });
    }


    private class SaveResultTask extends AsyncTask<Void, Void, MatchPlayTournament> {

        @Override
        protected MatchPlayTournament doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            if(mCheckFlag.equals("IAmFromBracket")) {
                return new Networker().addResultToBracket(getIntent().getLongExtra(TOURNAMENT_ID, 0L),
                        getIntent().getLongExtra(BRACKET_ID, 0L),
                        getIntent().getLongExtra(MATCH_ID, 0L),
                        mWinner,
                        mResultText);
            }
            else {
                return new Networker().addResultToPlayoffs(getIntent().getLongExtra(TOURNAMENT_ID, 0L),
                        getIntent().getIntExtra(ROUNDNUMBER, 0),
                        mWinner,
                        mResultText);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!new Networker().checkConnectivity(getApplicationContext()))
                this.cancel(true);
            Log.i("TAGG", "Going to fetch...");
        }

        @Override
        protected void onPostExecute(MatchPlayTournament tournament) {
            super.onPostExecute(tournament);
            Log.i("TAGG", "Done");
            if(mCheckFlag.equals("IAmFromBracket")) {
                Intent intent = BracketsActivity.newIntent(AddResultsActivty.this, tournament);
                startActivity(intent);
            }
            else {
                Intent intent = PlayOffTreeActivity.newIntent(AddResultsActivty.this, tournament);
                startActivity(intent);
            }
        }
    }

}
