package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;

public class MatchPlayInfoActivity extends AppCompatActivity {

    private Button mCreateButton;
    private Button mCreateButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_play_info);
    }

    public void goToPlayOfftree (View view){
        // Button Listener
        mCreateButton = (Button) findViewById(R.id.viewbPlayOffTree);

        Intent intent = new Intent(this, PlayOffTreeActivity.class);
        startActivity(intent);
    }

    public void goToBracket(View view){
        // Button Listener
        mCreateButton2 = (Button) findViewById(R.id.viewBracket);

        Intent intent = new Intent(this, BracketsActivity.class);
        startActivity(intent);
    }
}
