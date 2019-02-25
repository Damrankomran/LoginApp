package com.example.makina.loginapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    EditText et_email;
    EditText et_password;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FirebaseAuth sınıfının referans olduğu nesneleri kullanmak için
        //getInstancce metodunu kullanıyoruz.
        mAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);

        //Buttonlarımız için bir listener tanımlıyoruz
        findViewById(R.id.rlt).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
    }

    public void onClick(View v) {

        if(v.getId() == R.id.rlt){
            //Toast.makeText(getApplicationContext(),"Login button click!",Toast.LENGTH_SHORT).show();
            LoginController();
        }
        if(v.getId() == R.id.btn_signup){
            //Toast.makeText(getApplicationContext(),"SignUp button click!",Toast.LENGTH_SHORT).show();
            registerUser();
        }
    }

    public void LoginController(){

        //editTextController();

        userInfo();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        //Parola girmemiş kullanıcıları uyarıyoruz
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(getApplicationContext(),"Minimum lenght of password should be 6!",Toast.LENGTH_SHORT).show();
            return;
        }

        //Firebase üzerinde kullanıcı doğrulaması başlatıyoruz
        //Eğer başarılı olursa task.isSuccessful true dönecek ve EmptyActivity'e geçiş yapıcak
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Kullanıcı girişi başarılı oldu;
                    Toast.makeText(getApplicationContext(),"Login succesfull!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Login failed!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void registerUser(){
        //editTextController();

        userInfo();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        //Parola girmemiş kullanıcıları uyarıyoruz
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(getApplicationContext(),"Minimum lenght of password should be 6!",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(getApplicationContext(), "Sign in success!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Sign in failed!!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                });

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void userInfo(){
        email = et_email.getText().toString();
        password = et_password.getText().toString();
    }

    public void editTextController(){


    }

}
