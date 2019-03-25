package com.example.a533.cours_5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        setListener();
    }

    private void setListener()
    {
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser()
    {
        EditText userEmail           = findViewById(R.id.txtLoginEmail);
        EditText userPassword        = findViewById(R.id.txtLoginPassword);

        auth.signInWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SendUserMainActivity();
                }
                else {
                    Toast.makeText(getApplicationContext(), "La connexion a échouée", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void SendUserMainActivity()
    {
        Intent sendToMain = new Intent(this, MainActivity.class);
        startActivity(sendToMain);
    }
}
