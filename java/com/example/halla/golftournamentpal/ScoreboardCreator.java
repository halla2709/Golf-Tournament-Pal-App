package com.example.halla.golftournamentpal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class ScoreboardCreator extends AppCompatActivity {

    NumberPicker numberOfRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard_creator);


        numberOfRounds = (NumberPicker) findViewById(R.id.numberOfRoundsPicker);

        numberOfRounds.setMinValue(1);
        numberOfRounds.setMaxValue(10);


    }
}
