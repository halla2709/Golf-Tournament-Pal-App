package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.halla.golftournamentpal.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void createMatchPlayTournament(View view){
        Intent intent = new Intent(this, MatchPlayCreatorActivity.class);
        startActivity(intent);
    }

    public void createScoreboardTournament(View view){
        Intent intent = new Intent(this, ScoreboardCreatorActivity.class);
        startActivity(intent);
    }
}
