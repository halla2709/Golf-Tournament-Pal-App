package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.example.halla.golftournamentpal.R;


public class RegisterActivity extends AppCompatActivity {

    private Button mCreateButton;

    public void register(View view) {
        mCreateButton = (Button) findViewById(R.id.registerbutton);

        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
