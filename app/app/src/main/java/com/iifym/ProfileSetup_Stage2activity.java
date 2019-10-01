package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class ProfileSetup_Stage2activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup__stage2);

        initCurrentWeightSeekBar();
    }

    private void initCurrentWeightSeekBar() {
        final int MIN = 40;
        final int MAX = 180;

        final TextView textView = findViewById(R.id.currentWeight_text);
        SeekBar seekBar = findViewById(R.id.currentWeight_seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress <= MIN) {
                    progress = MIN + progress;
                }

                StringBuilder sb = new StringBuilder(String.valueOf(progress));
                textView.setText(sb.append(progress == MAX ? "+" : "").toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
