package com.iifym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iifym.classes.IntentSelector;

public class SettingsActivity extends AppCompatActivity {
    private final Activity self = this;

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

    public void deleteAccount(final View view) {
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credentials = EmailAuthProvider.getCredential(currentUser.getEmail(), null);
        currentUser.reauthenticate(credentials).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                currentUser.delete();
                logout(view);
            }
        });
    }
}
