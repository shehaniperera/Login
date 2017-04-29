package com.example.shehani.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText edemail;
    EditText edpassword;
    TextView signUp;
    Button btnSignIn;
    private ProgressDialog progressdialog;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edemail = (EditText)findViewById(R.id.editTextEmail);
        edpassword = (EditText)findViewById(R.id.editTextPassword);
        signUp = (TextView)findViewById(R.id.txtsignUp);
        btnSignIn = (Button)findViewById(R.id.btnSignin);
        progressdialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();


        signUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

    }


    private void userLogin() {

        String email = edemail.getText().toString().trim();
        String password = edpassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            showMessage("Error","Please enter your Email Address to Sign Up");
            return; // stop the function from executing it further
        }

        if(TextUtils.isEmpty(password)){
            showMessage("Error","Please enter your Password to Sign Up");
            return;  // stop the function from executing it further
        }

        progressdialog.setMessage(" Authenticating User ......");
        progressdialog.show();

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressdialog.dismiss();

                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(MainActivity.this,Main3Activity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view == btnSignIn){
            userLogin();
        }

        if(view == signUp){
            startActivity(new Intent(MainActivity.this,MainActivity.class));
        }
    }


    public void showMessage(String title , String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

