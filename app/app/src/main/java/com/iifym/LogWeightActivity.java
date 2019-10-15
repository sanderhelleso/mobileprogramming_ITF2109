package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        final double TWO_POUNDS = 0.90718474;
        double MAX_OFFSET = Math.ceil(User.getWeightLogs().getDaysSinceLastLogged() / 7.0) * TWO_POUNDS;

        double weightInputValue = Double.parseDouble(weightInput.getText().toString());
        double currToMax = currentWeight + MAX_OFFSET;
        double currToLow = currentWeight - MAX_OFFSET;

        boolean validInput = weightInputValue > currToMax || weightInputValue < currToLow;

        if (!validInput) {
            double maxVal = weightInputValue >  currToMax ? currToMax : currToLow;
            String limitMsg = "You entered a value that is unrealistic to be legit. " +
                    "The maximum weight difference since your last tracked log should not be any more than " + maxVal;
            Toast.makeText(this, limitMsg, Toast.LENGTH_LONG).show();
        }

        return validInput;

    }

    public void logWeight(View view) {
        if (!validateInput()) return;

        
    }
}
