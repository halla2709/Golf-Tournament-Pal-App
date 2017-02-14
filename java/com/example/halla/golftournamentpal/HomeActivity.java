package com.example.halla.golftournamentpal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void createMatchPlayTournament(View view){
        Intent intent = new Intent(this, MatchPlayCreator.class);
        startActivity(intent);
    }

    public void createScoreboardTournament(View view){
        Intent intent = new Intent(this, ScoreboardCreator.class);
        startActivity(intent);
    }
}
