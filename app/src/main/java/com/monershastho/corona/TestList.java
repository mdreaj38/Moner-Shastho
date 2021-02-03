package com.monershastho.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class TestList extends AppCompatActivity {

    public ArrayList<String> mydata = new ArrayList<String>();
    public ArrayList<String> testid = new ArrayList<String>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Assessment");


        listView = findViewById(R.id.testlistView);

        try {
            new HttpGetRequest().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TestList.this, R.layout.sample_view, R.id.textView, mydata);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animation animation1 = new AlphaAnimation(0.7f, 1.0f);
                animation1.setDuration(2000);
                view.startAnimation(animation1);
                Toast.makeText(TestList.this,mydata.get(position),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(TestList.this, ques_ans_select_box.class);
                String temp = testid.get(position);
                intent.putExtra("ID", temp );
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
            progressDialog = ProgressDialog.show(TestList.this, "Loading...", "");

        }

        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://bad-blogger.herokuapp.com/app-admin/all-tests?device=android");
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
                Log.e("checkkk",data.toString());
                JSONObject temp = new JSONObject(data);

                JSONArray JA = temp.getJSONArray("data");
                String idstring = " ";
                for (int i = 0; i < JA.length(); i++) {
                    JSONObject obj = null;
                    obj = (JSONObject) JA.get(i);
                    mydata.add((String) obj.get("title"));
                    testid.add((String) obj.get("_id"));
                    //mydata.add("111");
                    // Log.e("checkk2", (String) obj.get("title"));
                }
                //Log.e("check22", idstring.toString());

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