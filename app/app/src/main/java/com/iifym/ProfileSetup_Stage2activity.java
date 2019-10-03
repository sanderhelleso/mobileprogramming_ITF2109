package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.iifym.classes.SeekBarWithMin;

public class ProfileSetup_Stage2activity extends AppCompatActivity {
    private SeekBar heightSeekBar;
    private SeekBar currentWeightSeekBar;
    private SeekBar goalWeightSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup__stage2);

        initHeightSeekBar();
        initCurrentWeightSeekBar();
        initGoalWeightSeekBar();
    }

    private void initHeightSeekBar() {
        TextView textView = findViewById(R.id.height_text);
        heightSeekBar = findViewById(R.id.height_seekBar);
        new SeekBarWithMin(heightSeekBar, 130, 230, textView);
    }

    private void initCurrentWeightSeekBar() {
        TextView textView = findViewById(R.id.currentWeight_text);
        currentWeightSeekBar = findViewById(R.id.currentWeight_seekBar);
        new SeekBarWithMin(currentWeightSeekBar, 40, 180, textView);
    }

    private void initGoalWeightSeekBar() {
        TextView textView = findViewById(R.id.goalWeight_text);
        goalWeightSeekBar = findViewById(R.id.goalWeight_seekBar);
        new SeekBarWithMin(goalWeightSeekBar, 40, 180, textView);
    }
}
