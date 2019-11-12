package com.bitm.userauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText emailET,passwordET;
    private Button loginBtn;
    private FirebaseAuth firebaseAuth;
    private  String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setTitle("Sign In");


        init();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=emailET.getText().toString();
                password=passwordET.getText().toString();
                
                signIn(email,password);

            }
        });
    }

    private void signIn(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    startActivity(new Intent(SignInActivity.this,MainActivity.class));
                }

                else {

                    Toast.makeText(SignInActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void init() {

        emailET=findViewById(R.id.emailET);
        passwordET=findViewById(R.id.passwordET);
        loginBtn=findViewById(R.id.loginBtn);
        firebaseAuth=FirebaseAuth.getInstance();

    }

    public void signup(View view) {

        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }
}
