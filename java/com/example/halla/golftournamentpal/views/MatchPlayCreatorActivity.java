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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MatchPlayCreatorActivity extends AppCompatActivity {

    private NumberPicker mBracketPart;
    private NumberPicker mBracketPartExit;
    private Button mCreateButton;
    private EditText mTourName;
    private EditText mCourseName;
    private DatePicker mDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_play_creator);

        // NumberPickers
        mBracketPart = (NumberPicker) findViewById(R.id.bracketParticipants);
        mBracketPartExit = (NumberPicker) findViewById(R.id.bracketParticipantsExit);

        mBracketPart.setMinValue(1);
        mBracketPart.setMaxValue(2);

        mBracketPartExit.setMinValue(2);
        mBracketPartExit.setMaxValue(6);

    }

    public void displayOptions(View view) {
        CheckBox areBrackets = (CheckBox) findViewById(R.id.bracketCheckBox);
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

        int mBracketPartNum = mBracketPart.getValue();
        int mBracketPartExitNum = mBracketPartExit.getValue();

        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth() + 1;
        int year = mDatePicker.getYear();

        // Testing!
        Log.v("EditText", mTourName.getText().toString());
        Log.v("EditText", mCourseName.getText().toString());
        Log.v("EditText", mCourseName.getText().toString());
        Log.v("EditText", mCourseName.getText().toString());
        Log.d("Dagur", "value = " + day);
        Log.d("Bracket Part", ""+mBracketPartNum);
        Log.d("Bracket Exit", ""+mBracketPartExitNum);

        Intent intent = new Intent(this, ParticipantAdderMatchPlayActivity.class);
        startActivity(intent);
    }
}
