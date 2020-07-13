package com.example.corona;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
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
    public String CurStress="0";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public String sid = "",stitle = "",sbody = "";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

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


        sendAndRequestResponse(message);

        /*try {
            new HttpGetRequest().execute(message).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/

        Log.e("this", "a");
    }

  /*  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.getItemId();
        finish();
        return super.onOptionsItemSelected(item);
    }*/
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.activity, menu);
      return true;
  }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.act_item1)
        {
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Select your current stress level ");
            alert.setMessage("");
            LinearLayout linear=new LinearLayout(this);

            linear.setOrientation(LinearLayout.VERTICAL);
            TextView text=new TextView(this);
            //text.setText("Hello Android");
            text.setPadding(450, 10, 10, 10);

            SeekBar seek=new SeekBar(this);

            seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    String stress = Integer.toString(progress);
                    text.setText(Html.fromHtml("<h3><font color='#008000'><b>"+stress+"/"+seek.getMax()+"</b></font></h3>"));
                    CurStress = stress;

                }
                public void onStartTrackingTouch(SeekBar seekBar) {}
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            linear.addView(seek);
            linear.addView(text);
            alert.setView(linear);

            alert.setPositiveButton("Ok",new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog,int id)
                {
                    pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    editor = Objects.requireNonNull(pref).edit();
                    int cur = (Integer.parseInt(CurStress)*100)/120;
                    editor.putString("PreStress",Integer.toString(cur));
                    editor.apply();

                    finish();
                     Intent intent = new Intent(LockdownBlog.this, Task.class);
                    String AllInfo = sid+"$"+stitle+'$'+sbody;
                    intent.putExtra("all",AllInfo);
                    startActivity(intent);
                }
            });

            alert.show();

            return true;
        }
        else {
            this.finish();
            return super.onOptionsItemSelected(item);

        }
    }

    public class HttpGetRequest extends AsyncTask<String, Void, String> {

        String data="";
        String singleParsed = "";
        String dataParsed = "";
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
                Log.e("cchheecckk", url.toString());

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

                bufferedReader.close();

                ParseJsonData(data);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return data.toString();
        }

        @Override
        protected void onPostExecute(String aVoid) {
            try {
                JSONObject jo = new JSONObject(data);
                JSONObject real_data = jo.getJSONObject("data");
                new loadImage().execute((String) real_data.get("thumbnail")).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }

    private void sendAndRequestResponse(String url) {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

                try {
                    ParseJsonData(response.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MY_error","++++");
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    public void ParseJsonData(String data) throws JSONException, ExecutionException, InterruptedException {

        JSONObject jo = new JSONObject(data);
        JSONObject real_data = jo.getJSONObject("data");
        new loadImage().execute((String) real_data.get("thumbnail"));

        JSONArray Jtask = real_data.getJSONArray("activities");
        for(int j=0;j<Jtask.length();++j){
            JSONObject objj = (JSONObject) Jtask.get(j);

            sid+=(String) objj.get("_id")+'#';
            stitle+= (String) objj.get("title")+'#';
            sbody+= (String) objj.get("body")+'#';
        }
        Log.e("cchheecckk", sbody);

        btitle.setText((String) real_data.get("title"));

        textView.setText(Html.fromHtml((String) real_data.get("body")));

        author.setText(Html.fromHtml("<i>Written by</i>  " + (String) real_data.get("author")));
        date.setText((String) real_data.get("date"));
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
