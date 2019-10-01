package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.iifym.classes.SeekBarWithMin;

public class ProfileSetup_Stage2activity extends AppCompatActivity {
    private SeekBar myHeightSeekBar;
    private SeekBar currentWeightSeekBar;
    private SeekBar goalWeightSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup__stage2);

        initCurrentWeightSeekBar();
    }

    private void initCurrentWeightSeekBar() {
        TextView textView = findViewById(R.id.currentWeight_text);
        currentWeightSeekBar = findViewById(R.id.currentWeight_seekBar);
        new SeekBarWithMin(currentWeightSeekBar, 40, 180, textView);
    }
}
