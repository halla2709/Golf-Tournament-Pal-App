package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.halla.golftournamentpal.R;

public class LogInActivity extends AppCompatActivity {

    private Button mCreateButton;

    public void login (View view){
        mCreateButton = (Button) findViewById(R.id.loginbutton);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void register (View view){
        mCreateButton = (Button) findViewById(R.id.registerbutton);

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }
}
