package com.example.markfernandez.pinpoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout txtTIL1;
    private TextInputLayout txtTIL2;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignin;
    private TextView txtCreateAccount;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        txtTIL1 = (TextInputLayout) findViewById(R.id.txt_input1);
        txtTIL2 = (TextInputLayout) findViewById(R.id.txt_input2);

        etEmail =(EditText)findViewById(R.id.editTextEmail);
        etPassword =(EditText)findViewById(R.id.editTextPassword);
        btnSignin = (Button)findViewById(R.id.buttonSignIn);
        txtCreateAccount = (TextView) findViewById(R.id.textviewSignUp);

        btnSignin.setOnClickListener(this);
        txtCreateAccount.setOnClickListener(this);

    }

    private void userLogin() {
        boolean isValid = true;
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            etEmail.setError("Full Name required!");
            isValid = false;
        }else{
            txtTIL1.setErrorEnabled(false);
        }

        if(TextUtils.isEmpty(password)){
            etPassword.setError("Password required!");
            isValid = false;
        }else{txtTIL2.setErrorEnabled(false);}

        if(isValid){
            progressDialog.setMessage("Logging In...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                Toast.makeText(MainActivity.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(MainActivity.this, "Could not login, Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnSignin){
            userLogin();
        }
        if(v == txtCreateAccount){
            finish();
            startActivity(new Intent(this,SignupActivity.class));
        }
    }

}
