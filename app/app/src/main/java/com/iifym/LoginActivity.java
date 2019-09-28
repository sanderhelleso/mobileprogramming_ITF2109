package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.iifym.classes.Validator;

public class LoginActivity extends AppCompatActivity {
    private Context ctx;
    private EditText emailField;
    private EditText passField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.ctx = getApplicationContext();
        this.emailField =  findViewById(R.id.login_email);
        this.passField = findViewById(R.id.login_password);
    }

    public void back(View view) {
        finish();
    }

    public void login(View view) {
        String email = this.emailField.getText().toString();
        String pass = this.passField.getText().toString();

        if (!Validator.validateEmail(email)) {
            Toast.makeText(ctx, "The e-mail provided is invalid", Toast.LENGTH_LONG).show();
            return;
        }

        if (!Validator.validatePassword(pass)) {
            Toast.makeText(ctx, "Password must be between 8 - 40 characters and contain at least 1 digit", Toast.LENGTH_LONG).show();
            return;
        }
        
        // do firebase lookup here

    }
}
