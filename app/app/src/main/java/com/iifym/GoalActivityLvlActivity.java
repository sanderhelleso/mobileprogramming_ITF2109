package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iifym.classes.User;

import java.util.HashMap;
import java.util.Map;

public class GoalActivityLvlActivity extends AppCompatActivity {
    private int selectedLvlID;
    private Map<String, Double> activityLvls = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_lvl);
        fillActivityLvlMap();
        selectedLvlID = getResources().getIdentifier("sedentary", "id", getPackageName());
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
    }

    private void fillActivityLvlMap() {
        activityLvls.put("sedentary", 1.3);
        activityLvls.put("light_active", 1.375);
        activityLvls.put("active", 1.55);
        activityLvls.put("very_active", 1.725);
    }

    public void setActivityLvl(View view) {
        findViewById(selectedLvlID).setEnabled(true);
        view.setEnabled(false);
        selectedLvlID = view.getId();
    }

    public void nextStage(View view) {
        String selectedBtnStrId = getResources().getResourceEntryName(selectedLvlID);
        User.setActivityLvl(activityLvls.get(selectedBtnStrId));

        startActivity(new Intent(this, GoalWorkoutFreqActivity.class));
        overridePendingTransition(R.anim.left_ro_right, R.anim.fade_out);
    }
}
