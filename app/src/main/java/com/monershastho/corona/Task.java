package com.monershastho.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Task extends AppCompatActivity {


    public ArrayList<String> mydata = new ArrayList<String>();
    public ArrayList<String> Id = new ArrayList<String>();
    public ArrayList<String> Title = new ArrayList<String>();
    public ArrayList<String> Body = new ArrayList<String>();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String score,t_id,u_id,t_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        setTitle("Task");
        /*back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String check = getIntent().getStringExtra("all");
        String[] root =check.split("\\$");


        String[] ID = root[0].split("#");
        String[] Title = root[1].split("#");
        String[] Body = root[2].split("#");

        for(String i:Title){
            mydata.add(i);
        }

        ListView listView = findViewById(R.id.task_listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Task.this, R.layout.sample_view, R.id.textView, mydata);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animation animation1 = new AlphaAnimation(0.7f, 1.0f);
                animation1.setDuration(2000);
                view.startAnimation(animation1);
                // Toast.makeText(resource.this,mydata.get(position),Toast.LENGTH_SHORT).show();
                String temp = mydata.get(position);
                Intent intent = new Intent(Task.this, showTask.class);
                intent.putExtra("task_id",ID[position]);
                startActivity(intent);
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            new HttpGetRequest().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        try {
            new HttpGetRequest().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        this.finish();
    }
        public class HttpGetRequest extends AsyncTask<Void, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        String data = "";
        String singleParsed = "";
        String dataParsed = "";
        ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Task.this, "Loading...", "");

        }

        protected String doInBackground(Void... voids) {
            try {
                pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                String score = pref.getString("CurStress","0");
                String t_id  = pref.getString("score_id","0");
                String u_id  = pref.getString("id","0");
                String category  = pref.getString("Tag","0");
                String post_name  = pref.getString("ResourceName","0");

                URL url = new URL("https://bad-blogger.herokuapp.com/users/add/material/?score=" + score + "&id="+t_id+"&name="+post_name
                +"&user_id="+u_id+"&category="+category+"&device=android");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //Set methods and timeouts
                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }

}