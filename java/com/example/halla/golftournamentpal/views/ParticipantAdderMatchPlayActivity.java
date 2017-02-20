package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;

public class ParticipantAdderMatchPlayActivity extends AppCompatActivity {

    private Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_adder_match_play);
    }



    public void gotonext(View view){
        // Button Listener
        mCreateButton = (Button) findViewById(R.id.nextStepParticipant);

        Intent intent = new Intent(this, MatchPlayInfoActivity.class);
        startActivity(intent);
    }

}


