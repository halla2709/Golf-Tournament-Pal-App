package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;

public class ScoreInputViewActivity extends AppCompatActivity {

    private Button mCreateButton;
    private SessionManager mSessionManager;

    public void save (View view){
        mCreateButton = (Button) findViewById(R.id.savebutton);

        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_input_view);

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() == 0) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }
}
