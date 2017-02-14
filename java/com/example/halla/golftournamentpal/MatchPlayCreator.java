package com.example.halla.golftournamentpal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

public class MatchPlayCreator extends AppCompatActivity {

    Spinner bracketS;
    NumberPicker bracketP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_play_creator);

        bracketS = (Spinner) findViewById(R.id.bracketSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bracketNumbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bracketS.setAdapter(adapter);

        bracketP = (NumberPicker) findViewById(R.id.bracketPicker);

        bracketP.setMinValue(2);
        bracketP.setMaxValue(6);
    }

    public void displayOptions(View view) {
        CheckBox areBrackets = (CheckBox) findViewById(R.id.bracketCheckBox);
        TextView textView = (TextView) findViewById(R.id.bracketPickerText);

        if (areBrackets.isChecked()) {
            bracketP.setVisibility(View.VISIBLE);
            bracketS.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        } else {
            bracketP.setVisibility(View.GONE);
            bracketS.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }

    public void addParticipant(View view){
        Intent intent = new Intent(this, addParticipant.class);
        startActivity(intent);
    }
}
