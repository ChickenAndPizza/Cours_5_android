package com.example.a533.cours_5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpOrLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_or_login);
        setListener();
    }

    private void setListener()
    {
        findViewById(R.id.btnGoToSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToSignUp();
            }
        });

        findViewById(R.id.btnGoToLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToLogin();
            }
        });
    }

    private void sendToSignUp()
    {
        Intent sendToSignUp = new Intent(this, SignUpActivity.class);
        startActivity(sendToSignUp);
    }

    private void sendToLogin()
    {
        Intent sendToLogin = new Intent(this, LoginActivity.class);
        startActivity(sendToLogin);
    }
}
