package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.halla.golftournamentpal.R;


public class MainActivity extends AppCompatActivity {

    private Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void createtournament(View view){
        // Button Listener
        mCreateButton = (Button) findViewById(R.id.createtournamentbutton);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void results(View view){
        // Button Listener
        mCreateButton = (Button) findViewById(R.id.resultsactivity);

        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

    public void mypage(View view){
        // Button Listener
        //mCreateButton = (Button) findViewById(R.id.mypage);

        //Intent intent = new Intent(this, MyPageActivity.class);
       // startActivity(intent);
    }
}