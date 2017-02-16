package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.views.MatchPlayInfo;
import com.example.halla.golftournamentpal.views.ScoreboardInfo;

public class ParticipantAdderScoreboard extends AppCompatActivity {

    private Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_adder_scoreboard);
    }


    public void gotonext(View view){
        // Button Listener
        mCreateButton = (Button) findViewById(R.id.nextStepParticipantScoreboard);

        Intent intent = new Intent(this, ScoreboardInfo.class);
        startActivity(intent);
    }
}
