package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.iifym.classes.DatePicker;
import com.iifym.classes.HintAdapter;
import com.iifym.classes.IntentSelector;
import com.iifym.classes.SeekBarWithMin;
import com.iifym.classes.User;

public class ProfileSetupActivity extends AppCompatActivity {
    private String[] dropdownOptions = { "Male", "Female", "Select an gender" };
    private String selectedGender;
    private SeekBarWithMin heightSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        initDropdown();
        initDatePicker();
        initHeightSeekBar();
    }

    private void initDropdown() {
        final int COLOR_INDIGO = getResources().getColor(R.color.indigo);
        final int COLOR_HINT = getResources().getColor(R.color.hint);

        Spinner spinner = findViewById(R.id.gender_dropdown);
        HintAdapter adapter = new HintAdapter(this, R.layout.support_simple_spinner_dropdown_item, dropdownOptions);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
        spinner.getBackground().setColorFilter(COLOR_INDIGO, PorterDuff.Mode.SRC_ATOP);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView view = (TextView) parentView.getChildAt(0);
                view.setTextColor(position == dropdownOptions.length - 1 ? COLOR_HINT : COLOR_INDIGO);
                view.setTypeface(view.getTypeface(), Typeface.BOLD);
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
        birthdayInput.setInputType(InputType.TYPE_NULL); // hide keyboard
        new DatePicker(birthdayInput, this);
    }

    private void initHeightSeekBar() {
        TextView textView = findViewById(R.id.height_text);
        SeekBar _heightSeekBar = findViewById(R.id.height_seekBar);
        heightSeekBar = new SeekBarWithMin(_heightSeekBar, 130, 230, textView);
    }

    private boolean canNextStage() {
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
        if (canNextStage()) {
            User.setHeight(heightSeekBar.getValue());
            User.saveProfile();
            IntentSelector.replaceActivity(new Intent(this, GoalStatsActivity.class), this);
            Toast.makeText(this, "Profile successfully created!", Toast.LENGTH_LONG).show();
        }
    }
}
