package com.monershastho.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.concurrent.ExecutionException;

public class Read_Diary extends AppCompatActivity {

    private Button calendar;

    public ArrayList<String> mydata = new ArrayList<String>();

    public ArrayList<String> situation = new ArrayList<String>();
    public ArrayList<String> emotions = new ArrayList<String>();
    public ArrayList<String> thoughts = new ArrayList<String>();
    public ArrayList<String> reactions = new ArrayList<String>();
    public ArrayList<String> behaiviour = new ArrayList<String>();
    public ArrayList<String> displayDate = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read__diary);

        setTitle("Your Diary");
        /*back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String Id = pref.getString("id", "");
        try {
            new HttpGetRequest().execute(Id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        ListView listView = findViewById(R.id.diary_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Read_Diary.this, R.layout.sample_view, R.id.textView, mydata);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animation animation1 = new AlphaAnimation(0.7f, 1.0f);
                animation1.setDuration(2000);
                view.startAnimation(animation1);
                // Toast.makeText(resource.this,mydata.get(position),Toast.LENGTH_SHORT).show();
                String temp = mydata.get(position);
                Toast.makeText(Read_Diary.this, temp, Toast.LENGTH_SHORT).show();
                String cur = situation.get(position)+"#"+emotions.get(position)+"#"+thoughts.get(position)+
                        "#"+reactions.get(position)+"#"+behaiviour.get(position);

                Intent intent = new Intent(Read_Diary.this, Show_Diary.class);
                intent.putExtra("body",cur);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
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
            progressDialog = ProgressDialog.show(Read_Diary.this, "", "Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String st = strings[0];
                URL url = new URL("https://monershastho.herokuapp.com/users/profile/get-diary/"+st);
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
                Log.e("Checkk", data.toString());

                JSONObject jo = new JSONObject(data);
               // JSONObject jb = jo.getJSONObject("records");
                JSONArray ja = jo.getJSONArray("records");
                Log.e("dataaa", jo.toString());
                Log.e("dataaa",ja.toString());

                for(int i=0;i<ja.length();++i){
                    JSONObject cur = (JSONObject) ja.get(i);
                    situation.add((String)cur.get("situation"));
                    emotions.add((String)cur.get("emotions"));
                    thoughts.add((String)cur.get("thoughts"));
                    reactions.add((String)cur.get("reactions"));
                    behaiviour.add((String) cur.get("behaiviour"));
                    mydata.add((String) cur.get("displayDate"));
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

}
