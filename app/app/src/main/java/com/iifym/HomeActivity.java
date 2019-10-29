package com.iifym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.iifym.classes.Log;
import com.iifym.classes.Macros;
import com.iifym.classes.User;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    Button dailyToggleBtn;
    Button weeklyToggleBtn;
    Boolean dailyChartActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dailyToggleBtn = findViewById(R.id.chart_button_daily);
        weeklyToggleBtn = findViewById(R.id.chart_button_weekly);

        initPieChart();
        initLineChart(setLineEntriesDaily());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            initLineChart(dailyChartActive ? setLineEntriesDaily() : setLineEntriesWeekly());
        }
    }

    public void gotoSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
        overridePendingTransition(R.anim.enter, R.anim.fade_out);
    }

    public void gotoLogWeight(View view) {
        startActivityForResult(new Intent(this, LogWeightActivity.class), 1);
    }

    private void initPieChart() {
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
        pieChart.invalidate();
    }

    private ArrayList<PieEntry> setPieEntries() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        Macros macros = User.getMacros();
        TextView caloriesTxt = findViewById(R.id.calories);
        caloriesTxt.setText(Integer.toString(macros.getCalories()));

        pieEntries.add(new PieEntry(macros.getCarbohydrate(), 0));
        pieEntries.add(new PieEntry(macros.getProtein(), 1));
        pieEntries.add(new PieEntry(macros.getFat(), 2));

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

    private void initLineChart(ArrayList<Entry> entries) {
        LineChart lineChart = findViewById(R.id.lineChart);
        TextView placeholder = findViewById(R.id.linechart_placeholder);

        if (entries.size() < 2) {
            lineChart.setVisibility(View.GONE);
            placeholder.setVisibility(View.VISIBLE);
            return;
        }


        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.getDescription().setEnabled(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setEnabled(false);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawLabels(false);
        lineChart.getXAxis().setDrawAxisLine(false);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        lineChart.setData(new LineData(makeDataSet(entries)));
        lineChart.invalidate();

        lineChart.setVisibility(View.VISIBLE);
        placeholder.setVisibility(View.GONE);
    }

    private LineDataSet makeDataSet(ArrayList<Entry> entries) {
        LineDataSet lineDataSet = new LineDataSet(entries, "");

        lineDataSet.setDrawValues(false);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(Color.rgb(159,168,218));

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.line_chart);
        lineDataSet.setFillDrawable(drawable);
        lineDataSet.setDrawFilled(true);

        return lineDataSet;
    }

    private ArrayList<Entry> setLineEntriesDaily() {
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Log> dailyWeights = User.getWeightLogs().getLogs();

        int i = 1;
        for (Log log : dailyWeights) {
            lineEntries.add(new Entry(i++, (float) log.getWeight()));
        }

        return lineEntries;
    }

    private ArrayList<Entry> setLineEntriesWeekly() {
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Double> avgWeights = User.getWeightLogs().getAverageWeights();

        int i = 1;
        for (double weight : avgWeights) {
            lineEntries.add(new Entry(i++, (float) weight));
        }

        return lineEntries;
    }

    public void toggleLineChartDaily(View view) {
        dailyToggleBtn.setEnabled(false);
        weeklyToggleBtn.setEnabled(true);
        initLineChart(setLineEntriesDaily());
        dailyChartActive = true;
    }

    public void toggleLineChartWeekly(View view) {
        dailyToggleBtn.setEnabled(true);
        weeklyToggleBtn.setEnabled(false);
        initLineChart(setLineEntriesWeekly());
        dailyChartActive = false;
    }
 }
