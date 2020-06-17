package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class qus_ans_slider extends AppCompatActivity {
    public String progress_value;
    public int i = 0;
    public ArrayList<String> Question  = new ArrayList<String>();
    public ArrayList<String> range  = new ArrayList<String>();
    public ArrayList<String> Progress  = new ArrayList<String>();
    public String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qus_ans_slider);

        String ID = getIntent().getStringExtra("q_id");
        try {
            new HttpGetRequest().execute(ID).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        TextView value = findViewById(R.id.range);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        value.setText("Range: " + seekBar.getProgress() + "/" + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Toast.makeText(getApplicationContext(), progress + "", Toast.LENGTH_SHORT).show();

                value.setText("Range: " + progress + "/" + seekBar.getMax());
                progress_value = Integer.toString(progress);
                //store progress value in db
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                temp = progress_value;

            }
        });

        String[] s = new String[100];
        for(int i=0;i<Question.size();++i){
            s[i] = Question.get(i);
        }
        List<String> stringArrayList = new ArrayList<>(Arrays.asList(s));
        TextView question = findViewById(R.id.questionSlider);
        question.setText(stringArrayList.get(0));
        i = 1;
        int len = Question.size();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i<len){
                    //Intent intent = new Intent(qus_ans_slider.this, qus_ans_slider.class);
                    question.setText(stringArrayList.get(i));
                    seekBar.setProgress(0);
                    i += 1;
                }
                else {
                    Intent intent = new Intent(qus_ans_slider.this, MainScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(qus_ans_slider.this, "Last progress  " + temp, Toast.LENGTH_SHORT).show();

                    // startActivity(intent);
                    //finish();
                }

            }
        });

    }
    public class HttpGetRequest extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        String data = "";
        String singleParsed = "";
        String dataParsed = "";
        JSONObject real_data;
        ProgressDialog progressDialog;

        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Read_Diary.this, "", "Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String st = strings[0];
                URL url = new URL("https://bad-blogger.herokuapp.com/app-admin/single-test/"+strings[0]+"?device=android");
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
                Log.e("dataaa1", data.toString());

                JSONObject jo = new JSONObject(data);
                jo = jo.getJSONObject("data");
                JSONArray JA = jo.getJSONArray("questionSet");

                Log.e("dataaa2", jo.toString());
                Log.e("dataaa2", String.valueOf(JA.length()));

                for(int i=0;i<JA.length();++i){
                    JSONObject obj = (JSONObject) JA.get(i);
                    String type = (String) obj.get("inputType");
                    if(type.equals("ranger")){
                        JSONObject cur = (JSONObject) JA.get(i);
                        JSONObject mx = cur.getJSONObject("ranger");
                        range.add((String) mx.get("max"));
                        Question.add((String) obj.get("question"));
                    }
                }


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            //  progressDialog.cancel();
            super.onPostExecute(aVoid);
        }
    }

}


