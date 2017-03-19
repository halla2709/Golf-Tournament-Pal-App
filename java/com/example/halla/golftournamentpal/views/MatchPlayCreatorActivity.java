package com.example.halla.golftournamentpal.views;

import android.content.Context;
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

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, MatchPlayCreatorActivity.class);
        return i;
    }

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

        // If no user is logged in open the log in activity
        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent intent = LogInActivity.newIntent(MatchPlayCreatorActivity.this);
            startActivity(intent);
        }

    }

    /**
     * Displays options if the checkbox is checked, else the options are hidden
     */
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

    // Button listener
    public void addParticipant(View view){

        // Getting values from input
        mCreateButton = (Button) findViewById(R.id.nextStepMatchPlay);
        mTourName   = (EditText) findViewById(R.id.tournamentNameMP);
        mCourseName = (EditText) findViewById(R.id.courseNameMP);
        mDatePicker = (DatePicker) findViewById(R.id.datePickerMatchPlay);

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

        // Create a tournament based on the info given
        MatchPlayTournament tour = new MatchPlayTournament(mCourseName.getText().toString(),
                mTourName.getText().toString(),
                null,
                tournamentDate,
                areBrackets.isChecked(),
                null, null);

        // Start the next activity with the information
        Intent i = ParticipantAdderMainMatchPlayActivity.newIntent(MatchPlayCreatorActivity.this,
                tour, mBracketPartNum, mBracketPartExitNum, tournamentDateString);
        startActivity(i);
    }
}
