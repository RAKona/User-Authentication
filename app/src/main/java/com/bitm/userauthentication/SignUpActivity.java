package com.bitm.userauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailET, passwordET, confirmpasswordET, firstnameET, lastnameET;
    private Button signupBTN;
    private String email, password,confirmpassword,firstname, lastname;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        init();

        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                confirmpassword = confirmpasswordET.getText().toString();
                firstname = firstnameET.getText().toString();
                lastname = lastnameET.getText().toString();

                if(email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()){
                    if (email.isEmpty()){
                        Toast.makeText(SignUpActivity.this, "Input Email", Toast.LENGTH_SHORT).show();

                    }else if (password.isEmpty()){
                        Toast.makeText(SignUpActivity.this, "Input Password", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(SignUpActivity.this, "Input Confirm Password", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    if (!password.equals(confirmpassword)){
                        Toast.makeText(SignUpActivity.this, "Password Doesn't match", Toast.LENGTH_SHORT).show();
                    }else{
                        signup(email, password, firstname, lastname);
                    }

                }



            }
        });
    }

    private void signup(final String email, String password,  final String firstname, final String lastname) {


        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    String id = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference dataref = databaseReference.child("user").child(id);

                    HashMap<String, Object> userInfo = new HashMap<>();

                    userInfo.put("email", email);
                    userInfo.put("firstname", firstname);
                    userInfo.put("lastname", lastname);


                    dataref.setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                            } else {
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }


                    });

                } else {

                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private void init() {

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        confirmpasswordET = findViewById(R.id.confirmpasswordET);
        firstnameET = findViewById(R.id.firstnameET);
        lastnameET = findViewById(R.id.lastnameET);
        signupBTN = findViewById(R.id.signupBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }
}