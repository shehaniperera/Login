package com.example.shehani.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    EditText edemail;
    EditText edpassword;
    TextView signIn;
    Button btnSignUp;
    private ProgressDialog progressdialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        progressdialog = new ProgressDialog(this);
        edemail = (EditText)findViewById(R.id.editTextEmail);
        edpassword = (EditText)findViewById(R.id.editTextPassword);
        signIn = (TextView)findViewById(R.id.txtsignIn);
        btnSignUp = (Button)findViewById(R.id.btnSignup);

        btnSignUp.setOnClickListener(this);
        signIn.setOnClickListener(this);


    }


    private void registerUser() {

        String email = edemail.getText().toString().trim();
        String password = edpassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            showMessage("Error","Please enter your Email Address to Sign In");
            return; // stop the function from executing it further
        }

        if(TextUtils.isEmpty(password)){
            showMessage("Error","Please enter your Password to Sign In");
            return;  // stop the function from executing it further
        }

        progressdialog.setMessage("Registering User......");
        progressdialog.show();

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Main2Activity.this,"User is Registered Successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }

                else{
                    Toast.makeText(Main2Activity.this,"User is  Not Registered , Please Try again!!!! ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {


        if(view == btnSignUp){
            registerUser();
        }

        if(view == signIn){
            startActivity(new Intent(Main2Activity.this,MainActivity.class));
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