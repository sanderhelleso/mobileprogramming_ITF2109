package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.iifym.classes.DatePicker;
import com.iifym.classes.User;

public class ProfileSetupActivity extends AppCompatActivity {
    private String[] dropdownOptions = { "Male", "Female" };
    private String selectedGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        initDropdown();
        initDatePicker();
    }

    private void initDropdown() {
        String gender = User.getGender();
        selectedGender = gender != null && !gender.equals("") ? gender : dropdownOptions[0];

        Spinner spinner = findViewById(R.id.gender_dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, dropdownOptions
        );

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.mainRed), PorterDuff.Mode.SRC_ATOP);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView view = (TextView) parentView.getChildAt(0);
                view.setTextColor(getResources().getColor(R.color.mainRed));
                view.setTextSize(22);
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
        new DatePicker(birthdayInput, this);
    }
}
