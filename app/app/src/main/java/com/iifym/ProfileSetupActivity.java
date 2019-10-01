package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.iifym.classes.DatePicker;
import com.iifym.classes.HintAdapter;
import com.iifym.classes.User;

public class ProfileSetupActivity extends AppCompatActivity {
    private String[] dropdownOptions = { "Male", "Female", "Select an gender" };
    private String selectedGender;
    private Button nextStageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.nextStageBtn = findViewById(R.id.next_stage_button);
        setContentView(R.layout.activity_profile_setup);

        initDropdown();
        initDatePicker();
    }

    private void initDropdown() {
        final int COLOR_MAIN_RED = getResources().getColor(R.color.mainRed);
        final int COLOR_HINT = getResources().getColor(R.color.hint);

        String gender = User.getGender();
        if (gender != null && !gender.equals("")) {
            selectedGender = gender;
        }

        Spinner spinner = findViewById(R.id.gender_dropdown);
        HintAdapter adapter = new HintAdapter(this, R.layout.support_simple_spinner_dropdown_item, dropdownOptions);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
        spinner.getBackground().setColorFilter(COLOR_MAIN_RED, PorterDuff.Mode.SRC_ATOP);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView view = (TextView) parentView.getChildAt(0);
                view.setTextColor(position == dropdownOptions.length - 1 ? COLOR_HINT : COLOR_MAIN_RED);
                view.setTextSize(18);

                selectedGender = dropdownOptions[position];
                User.setGender(selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    private void initDatePicker() {
        EditText birthdayInput = findViewById(R.id.birthdayInput);
        birthdayInput.setInputType(InputType.TYPE_NULL);

        if (User.getBirthday() != null) {
           birthdayInput.setText(User.getBirthdayFormated());
        }

        new DatePicker(birthdayInput, this);
    }

    private boolean checkIfCanNext() {
        if (selectedGender.equals(dropdownOptions[dropdownOptions.length - 1])) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_LONG).show();
            return false;
        }

        if (User.getBirthday() == null) {
            Toast.makeText(this, "Please select your date of birth", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void nextStage(View view) {
        if (!checkIfCanNext()) {
            return;
        }

        // replace with next stage
        startActivity(new Intent(this, LoginActivity.class));
    }
}
