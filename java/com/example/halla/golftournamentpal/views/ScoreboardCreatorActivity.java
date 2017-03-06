package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;

public class ScoreboardCreatorActivity extends AppCompatActivity {

    private NumberPicker mNumberOfRounds;
    private Button mCreateButton;
    private EditText mTourName;
    private EditText mCourseName;
    private DatePicker mDatePicker;
    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard_creator);

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.my_layout);
        myLayout.requestFocus();


        mNumberOfRounds = (NumberPicker) findViewById(R.id.numberOfRoundsPicker);

        mNumberOfRounds.setMinValue(1);
        mNumberOfRounds.setMaxValue(10);

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }

    public void addParticipants(View view){
        // Button Listener
        // Getting values from input
        mCreateButton = (Button) findViewById(R.id.nextStepScoreboard);
        mTourName   = (EditText) findViewById(R.id.tournamentNameSB);
        mCourseName = (EditText) findViewById(R.id.courseNameSB);
        mDatePicker = (DatePicker) findViewById(R.id.datePickerScoreboard);

        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth() + 1;
        int year = mDatePicker.getYear();

        // Testing!
        Log.v("EditText", mTourName.getText().toString());
        Log.v("EditText", mCourseName.getText().toString());
        Log.v("EditText", mCourseName.getText().toString());
        Log.v("EditText", mCourseName.getText().toString());
        Log.d("Dagur", "value = " + day);
        Log.d("Rounds", ""+mNumberOfRounds.getValue());

        Intent intent = new Intent(this, ParticipantAdderMainScoreboardActivity.class);
        startActivity(intent);
    }
}


