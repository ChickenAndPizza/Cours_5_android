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
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        setListener();
    }

    private void setListener()
    {
        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void signUpUser()
    {
        EditText userEmail           = findViewById(R.id.txtSignUpEmail);
        EditText userPassword        = findViewById(R.id.txtSignUpPassword);
        EditText userPasswordConfirm = findViewById(R.id.txtSignUpPasswordConfirm);

        if (!userPassword.getText().toString().equals(userPasswordConfirm.getText().toString()))
        {
            Toast.makeText(this, "Les mots de passe ne sont pas équivalents", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SendUserMainActivity();
                }
                else {
                    Toast.makeText(getApplicationContext(), "La création du compte a échouée", Toast.LENGTH_SHORT).show();
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
