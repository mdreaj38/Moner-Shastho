package com.monershastho.corona;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ShowResource extends AppCompatActivity {
    Bitmap bitmap;
    ImageView imageView;

    TextView textView, btitle, author, date;
    WebView webView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resource);
        String message = getIntent().getStringExtra("ID");


        setTitle("");
        /*back button*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        webView = findViewById(R.id.resourcebodyy);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //editText.setText(Html.fromHtml("<b>"+message+"</b>"));
        try {
            new HttpGetRequest().execute(message).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) ;
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class HttpGetRequest extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        String  res_body;
        String data = "";
        String singleParsed = "";
        String dataParsed = "";
        JSONObject real_data;
        ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ShowResource.this, "", "Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String st = strings[0];
                URL url = new URL(st);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //Set method
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }

                JSONObject jo = new JSONObject(data);
                 res_body= (String) jo.get("bodyHTML");

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return res_body;
        }

        @Override
        protected void onPostExecute(String body) {
            webView.loadDataWithBaseURL(null,"<style>img{display: inline;height: auto;max-width: 100%;}</style>"+body,"text/html","utf-8",null);
            progressDialog.dismiss();
            super.onPostExecute(body);
        }
    }

}
