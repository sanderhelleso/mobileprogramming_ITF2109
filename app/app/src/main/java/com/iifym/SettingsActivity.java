package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.iifym.classes.IntentSelector;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        IntentSelector.replaceActivity(new Intent(this, AuthActivity.class), this);
    }
}
