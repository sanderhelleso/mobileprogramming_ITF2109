package com.iifym;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.iifym.classes.DecimalDigitsInputFilter;
import com.iifym.classes.User;

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

        weightInput.setHint(String.valueOf(hint));
        weightInput.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3, 2)});
    }

    private boolean validateInput() {
        String inputVal = weightInput.getText().toString();

        if (inputVal.equals("")) {
            Toast.makeText(this, "Please enter your weight for the day", Toast.LENGTH_LONG).show();
            return false;
        }

        final double SAFE_WEEKLY_LOSS = 0.90718474;
        double MAX_OFFSET = Math.ceil(User.getWeightLogs().getDaysSinceLastLogged() / 7.0) * (SAFE_WEEKLY_LOSS * 4);

        double weightInputValue = Double.parseDouble(inputVal);
        double currToMax = currentWeight + MAX_OFFSET;
        double currToLow = currentWeight - MAX_OFFSET;

        boolean notValidInput = weightInputValue > currToMax || weightInputValue < currToLow;

        if (notValidInput) {
            String maxVal = String.format("%.2f", weightInputValue >  currToMax ? currToMax : currToLow);
            String limitMsg = "You entered a value that is unrealistic.\n\n" +
                    "The maximum weight since your last tracked log should not be any more than " + maxVal + "kg";

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();

            alertDialog.setTitle("Confirm weight log value");
            alertDialog.setMessage(limitMsg);
            alertDialog.setIcon(R.drawable.ic_close_black_24dp);
            alertDialog.show();
        }

        return notValidInput;

    }

    public void logWeight(View view) {
        if (!validateInput()) return;


    }
}
