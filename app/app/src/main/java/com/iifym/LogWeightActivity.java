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

        final double SAFE_WEEKLY_LOSS = 0.90718474;
        double MAX_OFFSET = 100;//Math.ceil(User.getWeightLogs().getDaysSinceLastLogged() / 7.0) * (SAFE_WEEKLY_LOSS * 3);

        double weightInputValue = Double.parseDouble(inputVal);
        double currToMax = currentWeight + MAX_OFFSET;
        double currToLow = currentWeight - MAX_OFFSET;

        boolean notValidInput = weightInputValue > currToMax || weightInputValue < currToLow;

        if (notValidInput) {
            String maxVal = String.format("%.2f", weightInputValue >  currToMax ? currToMax : currToLow);
            String limitMsg = "\nThe maximum weight since your last tracked log should not be more or less than " +
                    maxVal + "kg to progress in a healthy speed.\n\nDo you want to proceed?\n";

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm add log");
            builder.setMessage(limitMsg)
                    .setPositiveButton("Yes, Add to log", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            addLog();
                        }
                    })
                    .setNegativeButton("No, Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    });

            final AlertDialog alertDialog = builder.create();
            alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    int color = getResources().getColor(R.color.indigo);
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color);
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color);
                }
            });

            alertDialog.show();
        }

        return !notValidInput;
    }

    public void logWeight(View view) {
        if (validateInput()) addLog();
    }

    private void addLog() {
        double weight = Double.valueOf(weightInput.getText().toString());
        Log log = new Log(new Date(), weight);
        WeightLogs.addLog(log);

        finish();
    }
}
