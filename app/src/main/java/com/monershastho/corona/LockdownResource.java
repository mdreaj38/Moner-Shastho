package com.monershastho.corona;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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

public class LockdownResource extends AppCompatActivity {

    public ArrayList<String> mydata = new ArrayList<String>();
    public ArrayList<String> blogid = new ArrayList<String>();
    public String CurStress="0";
    String mmessage;
    String AllInfo;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockdown_resource);
        mmessage = getIntent().getStringExtra("CAT");

        setTitle(mmessage);
        /*back button*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ListView listView = findViewById(R.id.lockListView);


        /* fetch data here */
        try {
            new HttpGetRequest().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LockdownResource.this, R.layout.sample_view, R.id.textView, mydata);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LockdownResource.this, LockdownBlog.class);
                String temp = "https://monershastho.herokuapp.com/users/single-material/" + blogid.get(position) + "?device=android";
                pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                editor = Objects.requireNonNull(pref).edit();
                editor.putString("score_id",blogid.get(position));
                editor.putString("ResourceName",mydata.get(position));
                editor.apply();
                 intent.putExtra("url", temp);
                startActivity(intent);
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
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
            progressDialog = ProgressDialog.show(LockdownResource.this, "Loading...", "");

        }

        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://monershastho.herokuapp.com/users/materials?category=" + mmessage + "&device=android");
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
                // Log.e("check",data.toString());
                JSONObject temp = new JSONObject(data);

                JSONArray JA = temp.getJSONArray("data");
                String idstring = " ";
                JSONArray Jtask = null;
                String sid = "",stitle = "",sbody = "";
                for (int i = 0; i < JA.length(); i++) {
                    JSONObject obj   = null;
                    obj = (JSONObject) JA.get(i);
                    idstring += obj.get("thumbnail") + " ,";
                    mydata.add((String) obj.get("title"));
                    blogid.add((String) obj.get("_id"));
                        Jtask = obj.getJSONArray("activities");

                        for(int j=0;j<Jtask.length();++j){
                            JSONObject objj = (JSONObject) Jtask.get(j);

                            sid+=(String) objj.get("_id")+'#';
                            stitle+= (String) objj.get("title")+'#';
                            sbody+= (String) objj.get("body")+'#';
                    }
                }

                AllInfo = sid+"$"+stitle+'$'+sbody;
                Log.e("thissj", AllInfo);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
            //MainActivity.data.setText(this.dataParsed);
        }
    }

}
