package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iifym.classes.IntentSelector;
import com.iifym.classes.SeekBarWithMin;
import com.iifym.classes.User;

import java.util.HashMap;
import java.util.Map;

public class GoalWorkoutFreqActivity extends AppCompatActivity {
    private SeekBar workoutsPerWeekSeekBar;
    private SeekBar minutesPerWorkoutSeekBar;
    private int selectedWorkoutIntensityID;
    private Map<String, Integer> intensityLvls = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_workout_freq);

        fillWorkoutIntensityMap();
        selectedWorkoutIntensityID = getResources().getIdentifier("light", "id", getPackageName());

        initWorkoutsPerWeekSeekBar();
        initMinutesPerWorkoutSeekBar();
    }

    private void fillWorkoutIntensityMap() {
        intensityLvls.put("light", 1);
        intensityLvls.put("moderate", 2);
        intensityLvls.put("difficult", 3);
        intensityLvls.put("intense", 4);
    }

    private void initWorkoutsPerWeekSeekBar() {
        TextView textView = findViewById(R.id.workouts_per_week_text);
        workoutsPerWeekSeekBar = findViewById(R.id.workouts_per_week_seekBar);
        new SeekBarWithMin(workoutsPerWeekSeekBar, 0, 7, textView);
    }

    private void initMinutesPerWorkoutSeekBar() {
        TextView textView = findViewById(R.id.minutes_per_workout_text);
        minutesPerWorkoutSeekBar = findViewById(R.id.minutes_per_workout_seekBar);
        new SeekBarWithMin(minutesPerWorkoutSeekBar, 30, 120, textView);
    }

    public void setIntensityLvl(View view) {
        findViewById(selectedWorkoutIntensityID).setEnabled(true);
        view.setEnabled(false);
        selectedWorkoutIntensityID = view.getId();
    }

    public void save(View view) {
        String selectedBtnStrId = getResources().getResourceEntryName(selectedWorkoutIntensityID);
        User.setIntensityLvl(intensityLvls.get(selectedBtnStrId));
        User.saveGoal();

        IntentSelector.replaceActivity(new Intent(this, HomeActivity.class), this);
    }
}
