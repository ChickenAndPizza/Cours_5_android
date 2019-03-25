package com.example.a533.cours_5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        setListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    private void setListener()
    {
        findViewById(R.id.btnLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                sendUserToSignUpOrLogin();
            }
        });
    }

    private void updateUI(FirebaseUser currentUser)
    {
        if(currentUser == null)
        {
            sendUserToSignUpOrLogin();
        }
    }

    private void sendUserToSignUpOrLogin()
    {
        Intent sendToSignUpOrLogin = new Intent(this, SignUpOrLoginActivity.class);
        startActivity(sendToSignUpOrLogin);
    }

}
