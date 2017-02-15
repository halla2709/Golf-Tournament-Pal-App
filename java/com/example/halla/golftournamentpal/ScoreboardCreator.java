package com.example.halla.golftournamentpal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

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

    public void addParticipant(View view){
        Intent intent = new Intent(this, AddParticipant.class);
        startActivity(intent);
    }
}
