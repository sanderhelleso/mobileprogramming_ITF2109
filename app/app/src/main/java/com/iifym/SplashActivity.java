package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iifym.classes.IntentSelector;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DURATION = 1000;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        this.auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        final FirebaseUser user = auth.getCurrentUser();
        final Intent intent = new Intent();
        final Activity activity = this;
        auth.signOut();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user == null) {
                    intent.setClass(activity, AuthActivity.class);
                    IntentSelector.replaceActivity(intent, activity);
                } else {
                    IntentSelector.hasSetupProfile(user, auth, intent, activity);
                }
            }
        }, SPLASH_DURATION);
    }
}
