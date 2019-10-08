package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.iifym.classes.WindowHelper;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowHelper.setTransparentNav(getWindow());
        setContentView(R.layout.activity_auth);
    }

    public void gotoLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.enter, R.anim.fade_out);
    }

    public void gotoSignup(View view) {
        startActivity(new Intent(this, SignupActivity.class));
        overridePendingTransition(R.anim.enter, R.anim.fade_out);
    }
}
