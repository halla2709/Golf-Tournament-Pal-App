package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.MatchPlayTournament;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MatchPlayCreatorActivity extends AppCompatActivity {

    private NumberPicker mBracketPart;
    private NumberPicker mBracketPartExit;
    private Button mCreateButton;
    private EditText mTourName;
    private EditText mCourseName;
    private DatePicker mDatePicker;
    private SessionManager mSessionManager;
    private CheckBox areBrackets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_play_creator);

        // NumberPickers
        mBracketPart = (NumberPicker) findViewById(R.id.bracketParticipants);
        mBracketPartExit = (NumberPicker) findViewById(R.id.bracketParticipantsExit);
        areBrackets = (CheckBox) findViewById(R.id.bracketCheckBox);

        mBracketPart.setMinValue(1);
        mBracketPart.setMaxValue(2);

        mBracketPartExit.setMinValue(2);
        mBracketPartExit.setMaxValue(6);

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }

    }

    public void displayOptions(View view) {

        TextView textView = (TextView) findViewById(R.id.bracketPickerText);
        TextView textViewer = (TextView) findViewById(R.id.bracketPickerText2);

        if (areBrackets.isChecked()) {
            mBracketPart.setVisibility(VISIBLE);
            mBracketPartExit.setVisibility(VISIBLE);
            textView.setVisibility(VISIBLE);
            textViewer.setVisibility(VISIBLE);
        } else {
            mBracketPart.setVisibility(GONE);
            mBracketPartExit.setVisibility(GONE);
            textView.setVisibility(GONE);
            textViewer.setVisibility(GONE);
        }
    }

    public void addParticipant(View view){

        // Button Listener
        // Getting values from input
        mCreateButton = (Button) findViewById(R.id.nextStepMatchPlay);
        mTourName   = (EditText) findViewById(R.id.tournamentNameMP);
        mCourseName = (EditText) findViewById(R.id.courseNameMP);
        mDatePicker = (DatePicker) findViewById(R.id.datePickerMatchPlay);

        Log.i("LOGGEr", "loglolgo");

        int mBracketPartNum = 0;
        int mBracketPartExitNum = 0;

        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth() + 1;
        int year = mDatePicker.getYear();

        Calendar cal = Calendar.getInstance();
        cal.set(year+1900, month, day);
        Date tournamentDate = cal.getTime();
        String tournamentDateString = "" + day + " " + month + " " + year;

        if(areBrackets.isChecked()) {
            mBracketPartNum = mBracketPart.getValue();
            mBracketPartExitNum = mBracketPartExit.getValue();
        }

        // Testing!
        Log.v("EditText", mTourName.getText().toString());
        Log.v("EditText", mCourseName.getText().toString());
        Log.d("Dagur", "value = " + day);
        Log.d("Bracket Part", ""+mBracketPartNum);
        Log.d("Bracket Exit", ""+mBracketPartExitNum);
        Log.d("Date String", tournamentDateString);

        MatchPlayTournament tour = new MatchPlayTournament(mCourseName.getText().toString(),
                mTourName.getText().toString(),
                null,
                tournamentDate,
                areBrackets.isChecked(),
                null, null);

        Intent i = ParticipantAdderMainMatchPlayActivity.newIntent(MatchPlayCreatorActivity.this,
                tour, mBracketPartNum, mBracketPartExitNum, tournamentDateString);
        startActivity(i);
    }
}
