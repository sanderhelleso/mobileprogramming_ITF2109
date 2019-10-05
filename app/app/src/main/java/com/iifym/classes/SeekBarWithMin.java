package com.iifym.classes;

import android.widget.SeekBar;
import android.widget.TextView;

import javax.annotation.Nullable;

public class SeekBarWithMin {
    private int MIN;
    private int MAX;
    private SeekBar seekBar;
    private TextView textView;
    private int value;

    public SeekBarWithMin(SeekBar seekBar, int MIN, int MAX, @Nullable TextView textView) {
        this.seekBar = seekBar;
        this.MIN = MIN;
        this.MAX = MAX;

        boolean withTextView = textView != null;
        if (withTextView) {
            this.textView = textView;
        }

        initSeekBar(withTextView);
    }

    private void initSeekBar(final boolean withTextView) {
        int calculatedMax = MAX - MIN;
        seekBar.setMax(calculatedMax);
        seekBar.setProgress(calculatedMax / 2);

        textView.setText(String.valueOf(MIN + (calculatedMax / 2)));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = MIN + progress;

                if (withTextView) {
                    StringBuilder sb = new StringBuilder(String.valueOf(progress));
                    textView.setText(sb.append(progress == MAX ? "+" : "").toString());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public int getValue() {
        return MIN + seekBar.getProgress();
    }
}
