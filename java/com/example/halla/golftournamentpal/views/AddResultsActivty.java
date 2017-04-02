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

    EditText mResults;
    Button mSaveResults;
    RadioButton mPlayer1Won;
    RadioButton mPlayer2Won;

    private static final String MATCH = "match";
    private static final String MATCHPLAY = "matchPlayTournament";


    public static Intent newIntent(Context packageContext, Match match, MatchPlayTournament matchPlayTournament) {
        Intent intent = new Intent(packageContext, AddResultsActivty.class);
        intent.putExtra(MATCH, match);
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

        mSaveResults = (Button) findViewById(R.id.saveresultsbutton);
        mSaveResults.setOnClickListener(new View.OnClickListener() {

            Intent intent = getIntent();
            String checkFlag = intent.getStringExtra("flag");

            @Override
            public void onClick(View view) {
            if(checkFlag.contentEquals("IAmFromBracket"))
            {
                //Came from BracketsActivity
                if (mPlayer1Won.isChecked()) {
                    // Player1Won
                    mResults.getText().toString();
                    mWinner = mMatch.getPlayers().get(0).getSocial();

                    Intent i = BracketsActivity.newIntent(AddResultsActivty.this, mMatchPlayTournament);
                    startActivity(i);
                } else if (mPlayer2Won.isChecked()) {
                    // Player2won
                    mResults.getText().toString();
                    mWinner = mMatch.getPlayers().get(1).getSocial();
                    Intent i = BracketsActivity.newIntent(AddResultsActivty.this, mMatchPlayTournament);
                    startActivity(i);
                } else {
                    //No one has been marked as winner
                    Toast.makeText(getApplicationContext(), "Please choose a winner", Toast.LENGTH_SHORT)
                            .show();
                }

            }
            else
            {
                //Came from someplace else
            }

            }
        });
    }

    /*
    private class SaveResults extends AsyncTask<Void, Void, Golfer> {

        @Override
        protected Golfer doInBackground(Void... params) {
            Log.i("TAGG", "Fetching...");
            Golfer golfer = new Networker().sendMatchPlayTournament()
            host = golfer;
            Log.i("TAGG", "Done fetching");
            return golfer;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAGG", "Going to fetch...");
        }

        @Override
        protected void onPostExecute(Golfer golfer) {
            super.onPostExecute(golfer);
            Log.i("TAGG", "Done");

        }
    }*/

}
