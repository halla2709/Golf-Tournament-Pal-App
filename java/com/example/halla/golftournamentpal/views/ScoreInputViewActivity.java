package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;

public class ScoreInputViewActivity extends AppCompatActivity {

    private Button mCreateButton;

    public void save (View view){
        mCreateButton = (Button) findViewById(R.id.savebutton);

        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_input_view);
    }
}
