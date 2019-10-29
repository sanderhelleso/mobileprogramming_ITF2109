package com.iifym;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iifym.classes.DecimalDigitsInputFilter;
import com.iifym.classes.Log;
import com.iifym.classes.User;
import com.iifym.classes.WeightLogs;

import java.util.Date;

public class LogWeightActivity extends AppCompatActivity {
    private double currentWeight = User.getCurrentWeight();
    private double goalWeight = User.getGoalWeight();
    private EditText weightInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_weight);

        initLogWeightInput();
    }

    public void back(View view) {
        finish();
    }

    private void initLogWeightInput() {
        weightInput = findViewById(R.id.logWeightInput);

        final double INC = 0.15;
        double hint = currentWeight > goalWeight ? currentWeight - INC : currentWeight + INC;

        weightInput.setHint(String.format("%.2f", hint));
        weightInput.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3, 2)});
    }

    private boolean validateInput() {
        String inputVal = weightInput.getText().toString();

        if (inputVal.equals("")) {
            Toast.makeText(this, "Please enter your weight for the day", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void logWeight(View view) {
        if (validateInput()) addLog();
    }

    private void addLog() {
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setEnabled(false);
        saveButton.setText("Saving...");

        double weight = Double.valueOf(weightInput.getText().toString());
        Log log = new Log(new Date(), weight);
        setResult(RESULT_OK);
        WeightLogs.addLog(log, this);
    }
}
