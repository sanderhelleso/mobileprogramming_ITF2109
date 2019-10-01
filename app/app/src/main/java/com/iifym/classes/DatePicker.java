package com.iifym.classes;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePicker implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {
    private Context ctx;
    private EditText editText;
    private Calendar calendar = Calendar.getInstance();

    public DatePicker(EditText editText, Context ctx) {
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.ctx = ctx;
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        String format = "MMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);


        Date date = calendar.getTime();
        editText.setText(simpleDateFormat.format(date));
        User.setBirthday(date);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            new DatePickerDialog(ctx, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        }

        view.clearFocus();
    }
}
