package com.monershastho.corona;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
import java.util.concurrent.ExecutionException;

public class resource extends AppCompatActivity {
    public ArrayList<String> mydata = new ArrayList<String>();
    public ArrayList<String> blogid = new ArrayList<String>();
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        setTitle("Resource");
        /*back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        listView = findViewById(R.id.listView);
        // mydata.add("first");

        try {
            new HttpGetRequest().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(resource.this, R.layout.sample_view, R.id.textView, mydata);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animation animation1 = new AlphaAnimation(0.7f, 1.0f);
                animation1.setDuration(2000);
                view.startAnimation(animation1);
                // Toast.makeText(resource.this,mydata.get(position),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(resource.this, ShowResource.class);
                String temp = blogid.get(position);
                intent.putExtra("ID", "https://bad-blogger.herokuapp.com/users/view/" + temp + "?device=android");
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(resource.this, "long", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
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
            progressDialog = ProgressDialog.show(resource.this, "Loading...", "");

        }

        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://bad-blogger.herokuapp.com/users/blogs?device=android");
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
