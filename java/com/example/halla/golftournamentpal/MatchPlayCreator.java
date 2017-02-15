package com.example.halla.golftournamentpal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import static android.view.View.*;

public class MatchPlayCreator extends AppCompatActivity {

    Spinner mBracketS;
    NumberPicker mBracketP;
    private Button mCreateButton;
    private EditText mTourName;
    private EditText mCourseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_play_creator);

        // Spinner and NumberPicker
        mBracketS = (Spinner) findViewById(R.id.bracketSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bracketNumbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBracketS.setAdapter(adapter);

        mBracketP = (NumberPicker) findViewById(R.id.bracketPicker);

        mBracketP.setMinValue(2);
        mBracketP.setMaxValue(6);

        // Button Listener
        mCreateButton = (Button) findViewById(R.id.nextStepMatchPlay);
        mTourName   = (EditText) findViewById(R.id.tournamentNameMP);
        mCourseName = (EditText) findViewById(R.id.courseNameMP);
        mCreateButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                //Prenta út nafn á móti.
                Log.v("EditText", mTourName.getText().toString());
                Log.v("EditText", mCourseName.getText().toString());
            }
        });
    }

    public void displayOptions(View view) {
        CheckBox areBrackets = (CheckBox) findViewById(R.id.bracketCheckBox);
        TextView textView = (TextView) findViewById(R.id.bracketPickerText);

        if (areBrackets.isChecked()) {
            mBracketP.setVisibility(VISIBLE);
            mBracketS.setVisibility(VISIBLE);
            textView.setVisibility(VISIBLE);
        } else {
            mBracketP.setVisibility(GONE);
            mBracketS.setVisibility(GONE);
            textView.setVisibility(GONE);
        }
    }

    public void addParticipant(View view){
        Intent intent = new Intent(this, ParticipantAdder.class);
        startActivity(intent);
    }
}
