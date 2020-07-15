package com.example.corona;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class chart_mental_stress extends AppCompatActivity {
    private LineChart mChart, mChart1;
    SharedPreferences mypref;
    SharedPreferences.Editor editor;
    String UserId;
    public ArrayList<String> test_score = new ArrayList<String>(); // graph - 1
    public ArrayList<String> task_score = new ArrayList<String>(); // graph - 1

    String User_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
    //shared preference
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        User_id = pref.getString("id", null);
        setTitle("Track Record");
        /*back button*/
        mypref = getApplicationContext().getSharedPreferences("MyPref",0);
        UserId = mypref.getString("id",null);

        try {
            new HttpGetRequest().execute(User_id).get(); // graph 1
            new HttpGetRequest2().execute(User_id).get();// graph 2
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mChart = findViewById(R.id.chart);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);
        MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);
        mv.setChartView(mChart);
        mChart.setMarker(mv);

        mChart1 = findViewById(R.id.chart1);
        mChart1.setTouchEnabled(true);
        mChart1.setPinchZoom(true);
        MyMarkerView mv1 = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);
        mv.setChartView(mChart1);
        mChart1.setMarker(mv1);


       renderData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.diary,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.Diary   || id == R.id.Diary_name)
        {
            Toast.makeText(chart_mental_stress.this, "HERE", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(chart_mental_stress.this, Diary.class);
            startActivity(intent);
            return true;
        }
        else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void renderData() {
        // for graph 1
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);
        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 5f, 0f);
        xAxis.setDrawLimitLinesBehindData(true);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 5f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);
        mChart.getAxisRight().setEnabled(false);
        setData();


        // for graph 2
        LimitLine llXAxis1 = new LimitLine(10f, "Index 10");
        llXAxis1.setLineWidth(4f);
        llXAxis1.enableDashedLine(10f, 10f, 0f);
        llXAxis1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis1.setTextSize(10f);
        XAxis xAxis1 = mChart1.getXAxis();
        xAxis1.enableGridDashedLine(10f, 5f, 0f);
        xAxis1.setDrawLimitLinesBehindData(true);
        YAxis leftAxis1 = mChart1.getAxisLeft();
        leftAxis1.removeAllLimitLines();
        leftAxis1.setAxisMaximum(100f);
        leftAxis1.setAxisMinimum(-99f);
        leftAxis1.enableGridDashedLine(10f, 5f, 0f);
        leftAxis1.setDrawZeroLine(false);
        leftAxis1.setDrawLimitLinesBehindData(false);
        mChart1.getAxisRight().setEnabled(false);
        setData1();
      }


    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();
        for(int i=0;i<test_score.size();++i){
            String cur = test_score.get(i);
            values.add(new Entry(i+1, (float) Double.parseDouble(cur)));
        }
        for(int i = task_score.size();i<7;++i){
            values.add(new Entry(i+1,0));
        }

       /* values.add(new Entry(1, 50));*/


        LineDataSet set1;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Sample Data");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart.setData(data);
        }
    }


    //for graph 2
    private void setData1() {

        ArrayList<Entry> values = new ArrayList<>();
        for(int i=0;i<task_score.size();++i){
            String cur = task_score.get(i);
            values.add(new Entry(i+1, (float) Double.parseDouble(cur)));
        }
        for(int i = task_score.size();i<7;++i){
            values.add(new Entry(i+1,0));
        }

       /* values.add(new Entry(1, 50));
        values.add(new Entry(2, 60));
        values.add(new Entry(3, -70));
        values.add(new Entry(4, 0));
        values.add(new Entry(5, 0));
        values.add(new Entry(7, 0));*/


        LineDataSet set1;
        if (mChart1.getData() != null &&
                mChart1.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart1.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart1.getData().notifyDataChanged();
            mChart1.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Sample Data");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart1.setData(data);
        }
    }




    //graph 1
    public class HttpGetRequest extends AsyncTask<String, Void, String> {


        String data = "";
        String singleParsed = "";
        String dataParsed = "";
        JSONObject real_data;
        ProgressDialog progressDialog;

        protected void onPreExecute() {
             progressDialog = ProgressDialog.show(chart_mental_stress.this, "", "Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL("https://bad-blogger.herokuapp.com/app-admin/tests/getScores/"+strings[0]);
                Log.e("Check", url.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //Set method
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                Log.e("Check", url.toString());

                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                JSONObject jo = new JSONObject(data);
                JSONArray JA = jo.getJSONArray("scores");
                for(int i=0;i<JA.length();++i){
                    //test_score.add((String) JA.get(i));
                    String cur = String.valueOf(JA.get(i));
                    test_score.add(cur);
                }
                Log.e("dataaa1", String.valueOf(test_score.size()));

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String aVoid) {
             progressDialog.cancel();
            super.onPostExecute(aVoid);
        }
    }

    //graph 2
    public class HttpGetRequest2 extends AsyncTask<String, Void, String> {

        String data = "";
        String singleParsed = "";
        String dataParsed = "";
        JSONObject real_data;
        ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(chart_mental_stress.this, "", "Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://bad-blogger.herokuapp.com/users/materials/getScores/"+strings[0]);
                Log.e("Check", url.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //Set method
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                Log.e("Check", url.toString());

                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                JSONObject jo = new JSONObject(data);
                JSONArray JA = jo.getJSONArray("scores");
                for(int i=0;i<JA.length();++i){
                    //test_score.add((String) JA.get(i));
                    String cur = String.valueOf(JA.get(i));
                    task_score.add(cur);
                }
                Log.e("dataaa111", String.valueOf(test_score.size()));

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            progressDialog.cancel();
            super.onPostExecute(aVoid);
        }
    }



}
