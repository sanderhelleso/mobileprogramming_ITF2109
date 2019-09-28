package com.iifym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.iifym.classes.IntentSelector;
import com.iifym.classes.Validator;

public class LoginActivity extends AppCompatActivity {
    final Handler handler = new Handler();

    private FirebaseAuth auth;
    private Context ctx;
    private EditText emailField;
    private EditText passField;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.auth = FirebaseAuth.getInstance();
        this.ctx = getApplicationContext();
        this.emailField =  findViewById(R.id.login_email);
        this.passField = findViewById(R.id.login_password);
        this.loginBtn = findViewById(R.id.login_button);
    }

    public void back(View view) {
        finish();
    }

    public void login(View view) {
        String email = this.emailField.getText().toString();
        String pass = this.passField.getText().toString();

        if (!Validator.validateEmailAndPass(email, pass, ctx)) {
            return;
        }
        
        // do firebase lookup here
        this.signIn(email, pass);
    }

    private void signIn(String email, String pass) {
        this.loginBtn.setEnabled(false);
        this.loginBtn.setText("Authenticating...");
        final Activity activity = this;

        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            IntentSelector.hasSetupProfile(user, auth, new Intent(), activity);
                            return;
                        }

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ctx, "Invalid e-mail or password", Toast.LENGTH_LONG).show();
                                loginBtn.setEnabled(true);
                                loginBtn.setText("Login");
                            }
                        }, 500);
                    }
                });
    }
}
