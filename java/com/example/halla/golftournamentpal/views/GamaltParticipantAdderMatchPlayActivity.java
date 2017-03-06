package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;

public class GamaltParticipantAdderMatchPlayActivity extends AppCompatActivity {

    private Button mCreateButton;
    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_adder_match_play);

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }



    public void gotonext(View view){
        // Button Listener
        mCreateButton = (Button) findViewById(R.id.nextStepParticipant);

        Intent intent = new Intent(this, MatchPlayInfoActivity.class);
        startActivity(intent);
    }

}


