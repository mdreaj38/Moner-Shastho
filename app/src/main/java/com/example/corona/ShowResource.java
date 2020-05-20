package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ShowResource extends AppCompatActivity {
    Bitmap bitmap;
    ImageView imageView;

    TextView textView, btitle, author, date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resource);
        String message = getIntent().getStringExtra("ID");


        setTitle("Blog");
        /*back button*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btitle = findViewById(R.id.title);
        textView = findViewById(R.id.blogbody);
        author = findViewById(R.id.author);
        date = findViewById(R.id.date);
        imageView = findViewById(R.id.blogImg);

        //editText.setText(Html.fromHtml("<b>"+message+"</b>"));
        Log.e("this", "a");
        try {
            new HttpGetRequest().execute(message).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.e("this", "a");
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) ;
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class HttpGetRequest extends AsyncTask<String, Void, String> {

        String data = "";
        String singleParsed = "";
        String dataParsed = "";
        JSONObject real_data;
        ProgressDialog progressDialog;
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ShowResource.this, "", "Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e("Check", "ok");
            try {
                String st =  strings[0];
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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
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
            } catch (MalformedURLException e) {
                e.printStackTrace();
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
