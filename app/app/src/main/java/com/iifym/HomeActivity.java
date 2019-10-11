package com.iifym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initChart();
    }

    public void gotoSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
        overridePendingTransition(R.anim.enter, R.anim.fade_out);
    }

    private void initChart() {
        PieChart pieChart = findViewById(R.id.pieChart);
        PieDataSet pieDataSet = new PieDataSet(setPieEntries(), "");

        pieChart.setCenterText("Macros");
        pieChart.setCenterTextColor(R.color.lightGrey);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);

        pieDataSet.setColors(setPieColors());
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setSliceSpace(5f);

        pieChart.setData(new PieData(pieDataSet));

    }

    private ArrayList<PieEntry> setPieEntries() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(200, 0));
        pieEntries.add(new PieEntry(180, 1));
        pieEntries.add(new PieEntry(50, 2));

        return pieEntries;
    }

    private ArrayList<Integer> setPieColors() {
        final int[] COLORS = {
                Color.rgb(128,203,196),
                Color.rgb(140,158,255),
                Color.rgb(172,168,255)
        };

        ArrayList<Integer> colors = new ArrayList<>();
        for(int c: COLORS) colors.add(c);

        return colors;
    }
}
