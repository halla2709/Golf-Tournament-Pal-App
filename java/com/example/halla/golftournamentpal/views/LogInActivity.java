package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.models.User;

public class LogInActivity extends AppCompatActivity {

    private Button mCreateButton;
    private EditText mUserSocial;
    private EditText mUserPassword;

    private User halla = new User(2709942619L, "blabla");


    public void login (View view){
        mCreateButton = (Button) findViewById(R.id.loginbutton);
        mUserSocial = (EditText) findViewById(R.id.loginSSN);
        mUserPassword = (EditText) findViewById(R.id.loginPW);



        if(Long.parseLong(mUserSocial.getText().toString()) == halla.getSocial()
                && mUserPassword.getText().toString().equals(halla.getPassword())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {

            Toast.makeText(getApplicationContext(), "Not correct credientials", Toast.LENGTH_SHORT)
                    .show();
        }

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
