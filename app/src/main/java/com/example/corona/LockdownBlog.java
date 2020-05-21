package com.example.corona;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LockdownBlog extends AppCompatActivity {
    Bitmap bitmap;
    ImageView imageView;

    TextView textView, btitle, author, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockdown_blog);
        String message = getIntent().getStringExtra("url");


        setTitle("Blog");
        /*back button*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btitle = findViewById(R.id.ltitle);
        textView = findViewById(R.id.lblogbody);
        author = findViewById(R.id.lauthor);
        date = findViewById(R.id.ldate);
        imageView = findViewById(R.id.lblogImg);

        //editText.setText(Html.fromHtml("<b>"+message+"</b>"));
        Log.e("this", "a");
        try {
            new HttpGetRequest().execute(message).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Log.e("this", "a");
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.getItemId();
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
            progressDialog = ProgressDialog.show(LockdownBlog.this, "", "Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e("Check", "ok");
            try {
                String st = strings[0];
                URL url = new URL(st);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //Set method
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

                JSONObject jo = new JSONObject(data);
                real_data = jo.getJSONObject("data");
                Log.e("this2", (String) real_data.toString());
                Log.e("this2", (String) real_data.get("title"));
                Log.e("this2body", (String) real_data.get("body"));
                Log.e("this2", data);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            try {

                new loadImage().execute((String) real_data.get("thumbnail")).get();
                btitle.setText((String) real_data.get("title"));

                textView.setText(Html.fromHtml((String) real_data.get("body")));

                author.setText(Html.fromHtml("<i>Written by</i>  " + (String) real_data.get("author")));
                date.setText((String) real_data.get("date"));

                progressDialog.dismiss();
            } catch (JSONException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }
    }

    public class loadImage extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... param) {

            URL url = null;
            InputStream strm = null;
            try {

                url = new URL(param[0]);
                strm = new BufferedInputStream(url.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeStream(strm);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            imageView.setImageBitmap(bitmap);
        }
    }
}
