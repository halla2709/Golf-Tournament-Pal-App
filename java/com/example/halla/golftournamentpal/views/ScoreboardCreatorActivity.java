package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.example.halla.golftournamentpal.R;

public class ScoreboardCreatorActivity extends AppCompatActivity {

    private NumberPicker mNumberOfRounds;
    private Button mCreateButton;
    private EditText mTourName;
    private EditText mCourseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard_creator);

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.my_layout);
        myLayout.requestFocus();


        mNumberOfRounds = (NumberPicker) findViewById(R.id.numberOfRoundsPicker);

        mNumberOfRounds.setMinValue(1);
        mNumberOfRounds.setMaxValue(10);
    }

    public void addParticipant(View view){
        mCreateButton = (Button) findViewById(R.id.nextStepScoreBoard);
        mTourName   = (EditText) findViewById(R.id.tournamentNameSB);
        mCourseName = (EditText) findViewById(R.id.courseNameSB);

        Log.v("EditText", mTourName.getText().toString());
        Log.v("EditText", mCourseName.getText().toString());

        Intent intent = new Intent(this, ParticipantAdderScoreboardActivity.class);
        startActivity(intent);
    }
}


