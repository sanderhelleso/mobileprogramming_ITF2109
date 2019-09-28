package com.iifym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iifym.classes.Validator;

public class SignupActivity extends AppCompatActivity {
    final Handler handler = new Handler();

    private FirebaseAuth auth;
    private Context ctx;
    private EditText emailField;
    private EditText passField;
    private EditText confirmPassField;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.auth = FirebaseAuth.getInstance();
        this.ctx = getApplicationContext();
        this.emailField =  findViewById(R.id.signup_email);
        this.passField = findViewById(R.id.signup_password);
        this.confirmPassField = findViewById(R.id.confirm_signup_password);
        this.signupBtn = findViewById(R.id.signup_button);
    }

    public void back(View view) {
        finish();
    }

    public void signup(View view) {
        String email = this.emailField.getText().toString();
        String pass = this.passField.getText().toString();
        String confirmPass = this.confirmPassField.getText().toString();

        if (!Validator.validateEmailAndPass(email, pass, ctx)) {
            return;
        }

        if (!Validator.comparePass(pass, confirmPass, ctx)) {
            return;
        }

        this.createUser(email, pass);
    }

    private void createUser(String email, String pass) {
        this.signupBtn.setEnabled(false);
        this.signupBtn.setText("Creating User...");

        this.auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            return;
                        }

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String msg;

                                try {
                                 msg = task.getException().getMessage();
                                } catch (NullPointerException e) {
                                    msg = "Unable to create user at this time. Please try again";
                                }

                                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
                                signupBtn.setEnabled(true);
                                signupBtn.setText("Sign Up");
                            }
                        }, 500);
                    }
                });
    }

}
