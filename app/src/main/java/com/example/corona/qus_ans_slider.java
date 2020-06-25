package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class qus_ans_slider extends AppCompatActivity {
    public String progress_value;
    public int i = 0;
    public ArrayList<String> Question  = new ArrayList<String>();
    String title="";
    public ArrayList<String> range  = new ArrayList<String>();
    public ArrayList<Integer> scale  = new ArrayList<Integer>();

    public String temp;

    String User_Id,User_name,ques_ID;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int cur_score=0,last_prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qus_ans_slider);

         ques_ID = getIntent().getStringExtra("t_id");
        Log.e("ccheck", ques_ID);

        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String tt = pref.getString("ques_score", null);
        User_Id = pref.getString("id",null);
        User_name = pref.getString("name",null);
        cur_score =Integer.parseInt(tt);

          try {
            new HttpGetRequest().execute(ques_ID).get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if(Question.size()==0){
            JSONObject res = new JSONObject();
            try {
                res.put("score", String.valueOf(cur_score));
                res.put("id", ques_ID);
                res.put("name", User_name);
                res.put("device", "android");
                res.put("user_id", User_Id);
                new HttpPostRequest().execute(res.toString()).get();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        else {
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        TextView value = findViewById(R.id.range);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        value.setText("Range: " + seekBar.getProgress() + "/" + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Toast.makeText(getApplicationContext(), progress + "", Toast.LENGTH_SHORT).show();

                value.setText( progress + "/" + seekBar.getMax());
                progress_value = Integer.toString(progress);
                //store progress value in db
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                last_prog = Integer.parseInt(progress_value);
            }
        });

        String[] s = new String[100];
        for(int i=0;i<Question.size();++i){
            s[i] = Question.get(i);
        }
        List<String> stringArrayList = new ArrayList<>(Arrays.asList(s));
        TextView question = findViewById(R.id.questionSlider);
        question.setText(stringArrayList.get(0));
        seekBar.setMax(Integer.parseInt(range.get(0)));
        i = 0;
        int len = Question.size();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i<len-1){
                    //Intent intent = new Intent(qus_ans_slider.this, qus_ans_slider.class);
                    cur_score+=(scale.get(i)*last_prog);
                    question.setText(stringArrayList.get(i));
                    seekBar.setProgress(0);
                    seekBar.setMax(Integer.parseInt(range.get(i)));
                    i += 1;
                }
                else if(i==len-1)
                {

                    cur_score+=(scale.get(i)*last_prog);
                    Toast.makeText(getApplicationContext(), String.valueOf( scale.get(i))+" "+String.valueOf(last_prog), Toast.LENGTH_SHORT).show();
                    pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    editor = Objects.requireNonNull(pref).edit();
                    editor.putString("ques_score", String.valueOf(cur_score));
                    editor.apply();


                    JSONObject res = new JSONObject();
                    try {
                        res.put("score", String.valueOf(cur_score));
                        res.put("id", ques_ID);
                        res.put("name", title);
                        res.put("device", "android");
                        res.put("user_id", User_Id);
                        new HttpPostRequest().execute(res.toString()).get();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        }




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
            progressDialog = ProgressDialog.show(qus_ans_slider.this, "", "Loading...");
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

                JSONObject jo = new JSONObject(data);
                jo = jo.getJSONObject("data");
                title = (String) jo.get("title");
                JSONArray JA = jo.getJSONArray("questionSet");

                for(int i=0;i<JA.length();++i){

                    JSONObject obj = (JSONObject) JA.get(i);
                    String type = (String) obj.get("inputType");

                    if(type.equals("ranger")){

                        scale.add(Integer.parseInt((String) obj.get("scale")));
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
             progressDialog.cancel();
            super.onPostExecute(aVoid);
        }
    }
    public class HttpPostRequest extends AsyncTask<String, Void, Void> {
        String verdict, message;
        ProgressDialog progressDialog;
        int statusCode;
        StringBuilder sb = new StringBuilder();

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(qus_ans_slider.this, "Loading...", "");

        }

        protected Void doInBackground(String... strings) {
            HttpURLConnection urlConnection;
            String url =  "https://bad-blogger.herokuapp.com/app-admin/test/new";
            String data = strings[0];
            Log.e("code1",data);
            String result = null;
            try {
                //Connect
                urlConnection = (HttpURLConnection) ((new URL(url).openConnection()));
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("POST");
                urlConnection.connect();

                //Write
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(data);
                writer.close();
                outputStream.close();

                //Read
                statusCode = urlConnection.getResponseCode();
                Log.e("code1", Integer.toString(statusCode));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                Log.e("code1", sb.toString());


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(Void code) {
            progressDialog.dismiss();
            Intent intent = new Intent(qus_ans_slider.this, MainScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            super.onPostExecute(code);
        }
    }


}


