package com.example.corona;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class chart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        BarChart chart = findViewById(R.id.barchart);

        ArrayList<BarEntry> mental_health = new ArrayList<BarEntry>();

        mental_health.add(new BarEntry(1f, 0));
        mental_health.add(new BarEntry(3f, 1));
        mental_health.add(new BarEntry(5f, 2));
        mental_health.add(new BarEntry(7f, 3));
        mental_health.add(new BarEntry(5f, 4));
        mental_health.add(new BarEntry(3f, 5));
        mental_health.add(new BarEntry(1f, 6));


        ArrayList<String> day = new ArrayList<>();

        for (int i = 1; i <= 7; i++)
            day.add(Integer.toString(i));

        BarDataSet bardataset = new BarDataSet(mental_health, "Day");
        chart.animateY(1000);
        BarData data = new BarData(day, bardataset);
        bardataset.setColors(ColorTemplate.JOYFUL_COLORS);

        chart.setData(data);
    }
}
