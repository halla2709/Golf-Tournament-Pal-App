package com.example.halla.golftournamentpal.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.Match;

public class AddResultsActivty extends AppCompatActivity {

    private SessionManager mSessionManager;
    private static Match sMatch;

    TextView mPlayer1;
    TextView mPlayer2;
    EditText mResults;
    Button mSaveResults;


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, AddResultsActivty.class);
        return intent;
    }

    public static Intent newIntent(Context packageContext, Match match) {
        Intent i = new Intent(packageContext, BracketsActivity.class);
        i.putExtra("friend", match);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_results);
        mPlayer1 = (TextView) findViewById(R.id.player1);
        mPlayer2 = (TextView) findViewById(R.id.player2);
        mResults = (EditText) findViewById(R.id.results);
        mSaveResults = (Button) findViewById(R.id.saveresultsbutton);

        // Displaying player-info
        mPlayer1.setText(sMatch.getPlayers().get(0).getName());
        mPlayer2.setText(sMatch.getPlayers().get(1).getName());

    }
}
