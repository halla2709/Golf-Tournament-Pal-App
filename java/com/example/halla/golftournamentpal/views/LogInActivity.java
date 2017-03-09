package com.example.halla.golftournamentpal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.halla.golftournamentpal.R;
import com.example.halla.golftournamentpal.SessionManager;
import com.example.halla.golftournamentpal.models.User;

public class LogInActivity extends AppCompatActivity {

    private Button mCreateButton;
    private EditText mUserSocial;
    private EditText mUserPassword;
    private SessionManager mSessionManager;

    private User halla = new User(2709942619L, "blabla");


    public void login (View view){
        mCreateButton = (Button) findViewById(R.id.loginbutton);
        mUserSocial = (EditText) findViewById(R.id.loginSSN);
        mUserPassword = (EditText) findViewById(R.id.loginPW);


        if(mUserSocial.getText().length() == 0 || mUserPassword.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "Please enter credentials", Toast.LENGTH_SHORT)
                    .show();
        }

        else if(mSessionManager.startSession(Long.parseLong(mUserSocial.getText().toString()),
                mUserPassword.getText().toString())) {

            if(mSessionManager.getSessionUserSocial() != 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

            else {
                Toast.makeText(getApplicationContext(), "Incorrect credientials, try again", Toast.LENGTH_SHORT)
                        .show();
            }

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

        mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.getSessionUserSocial() != 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
