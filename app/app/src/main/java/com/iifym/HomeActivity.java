package com.iifym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.iifym.classes.Log;
import com.iifym.classes.Macros;
import com.iifym.classes.User;
import com.iifym.classes.WeightLogs;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initPieChart();
        initLineChart();
    }

    public void gotoSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
        overridePendingTransition(R.anim.enter, R.anim.fade_out);
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

    private void initLineChart() {
        LineChart lineChart = findViewById(R.id.lineChart);
        ArrayList<Entry> entries = setLineEntries();

        if (entries.size() == 0) {
            lineChart.setVisibility(View.GONE);

            TextView placeholder = findViewById(R.id.linechart_placeholder);
            placeholder.setVisibility(View.VISIBLE);

            return;
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "");

        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.getDescription().setEnabled(false);

        lineDataSet.setDrawValues(false);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(Color.rgb(159,168,218));

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.line_chart);
        lineDataSet.setFillDrawable(drawable);
        lineDataSet.setDrawFilled(true);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setEnabled(false);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        lineChart.setData(new LineData(lineDataSet));
    }

    private ArrayList<Entry> setLineEntries() {
        ArrayList<Entry> lineEntries = new ArrayList<>();
        WeightLogs weightLogs = User.getWeightLogs();

        int i = 1;
        for (double weight : weightLogs.getAverageWeights()) {
            lineEntries.add(new Entry(i++, (int)weight));
        }

        return lineEntries;
    }
 }
