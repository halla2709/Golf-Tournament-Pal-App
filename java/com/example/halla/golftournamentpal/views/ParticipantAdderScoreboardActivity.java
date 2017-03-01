package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;

public class ParticipantAdderScoreboardActivity extends AppCompatActivity {

    private Button mCreateButton;
    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_adder_scoreboard);

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }


    public void gotonext(View view){
        // Button Listener
        mCreateButton = (Button) findViewById(R.id.nextStepParticipantScoreboard);

        Intent intent = new Intent(this, ScoreboardInfoActivity.class);
        startActivity(intent);
    }
}
