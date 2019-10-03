package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.iifym.classes.SeekBarWithMin;
import com.iifym.classes.User;

public class GoalStatsActivity extends AppCompatActivity {
    private SeekBar heightSeekBar;
    private SeekBar currentWeightSeekBar;
    private SeekBar goalWeightSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_stats);

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

    public void nextStage(View view) {
        User.setHeight(heightSeekBar.getProgress());
        User.setCurrentWeight(currentWeightSeekBar.getProgress());
        User.setGoalWeight(goalWeightSeekBar.getProgress());

        startActivity(new Intent(this, GoalActivityLvlActivity.class));
    }
}