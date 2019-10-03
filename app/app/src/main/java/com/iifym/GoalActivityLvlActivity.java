package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iifym.classes.User;

import java.util.HashMap;
import java.util.Map;

public class GoalActivityLvlActivity extends AppCompatActivity {
    private int selectedLvlID;
    private Map<String, Integer> activityLvls = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_lvl);
        fillActivityLvlMap();
        selectedLvlID = getResources().getIdentifier("sedentary", "id", getPackageName());
    }

    private void fillActivityLvlMap() {
        activityLvls.put("sedentary", 1);
        activityLvls.put("light_active", 2);
        activityLvls.put("active", 3);
        activityLvls.put("very_active", 4);
    }

    public void setActivityLvl(View view) {
        findViewById(selectedLvlID).setEnabled(true);
        view.setEnabled(false);
        selectedLvlID = view.getId();
    }

    public void nextStage(View view) {
        String selectedBtnStrId = getResources().getResourceName(selectedLvlID);
        User.setActivityLvl(activityLvls.get(selectedBtnStrId));
    }
}
