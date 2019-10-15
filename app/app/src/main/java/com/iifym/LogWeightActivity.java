package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.iifym.classes.DecimalDigitsInputFilter;
import com.iifym.classes.User;

public class LogWeightActivity extends AppCompatActivity {

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
        EditText editText = findViewById(R.id.logWeightInput);
        double currentWeight = User.getCurrentWeight();
        double goalWeight = User.getGoalWeight();

        double inc = 0.15;
        double hint = currentWeight > goalWeight ? currentWeight - inc : currentWeight + inc;
        editText.setHint(String.valueOf(hint));
        editText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3, 2)});
    }
}
