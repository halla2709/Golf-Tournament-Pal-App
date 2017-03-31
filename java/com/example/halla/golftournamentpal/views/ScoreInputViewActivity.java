package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.ScoreboardTournament;

public class ScoreInputViewActivity extends AppCompatActivity {

    private Button mCreateButton;
    private SessionManager mSessionManager;
    private ScoreboardTournament mScoreboardTournament;

    public void save (View view){
        mCreateButton = (Button) findViewById(R.id.savebutton);

        Intent i = ScoreboardActivity.newIntent(getApplicationContext(), mScoreboardTournament);
        startActivity(i);
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
    }
}
