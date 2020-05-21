package com.example.corona;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    String mmessage;

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
                String temp = "https://bad-blogger.herokuapp.com/users/single-material/" + blogid.get(position) + "?device=android";
                Log.e("now", temp);
                intent.putExtra("url", temp);
                startActivity(intent);
                Toast.makeText(LockdownResource.this, Integer.toString(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.getItemId();
        {
            finish();
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
                URL url = new URL("https://bad-blogger.herokuapp.com/users/materials?category=" + mmessage + "&device=android");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //Set methods and timeouts
                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.connect();
                ;
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
                for (int i = 0; i < JA.length(); i++) {
                    JSONObject obj = null;
                    obj = (JSONObject) JA.get(i);
                    idstring += obj.get("thumbnail") + " ,";
                    mydata.add((String) obj.get("title"));
                    blogid.add((String) obj.get("_id"));
                    //mydata.add("111");
                    // Log.e("checkk2", (String) obj.get("title"));
                }
                //  Log.e("check22", idstring.toString());

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
