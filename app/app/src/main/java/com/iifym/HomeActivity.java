package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void gotoSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
        overridePendingTransition(R.anim.enter, R.anim.fade_out);
    }
}
