package com.iifym.classes;

import android.widget.SeekBar;
import android.widget.TextView;

import javax.annotation.Nullable;

public class SeekBarWithMin {
    private int MIN;
    private int MAX;
    private SeekBar seekBar;
    private TextView textView;

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
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress <= MIN) {
                    progress = MIN + progress;
                }

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
}
