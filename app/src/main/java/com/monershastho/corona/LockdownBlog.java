package com.monershastho.corona;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LockdownBlog extends AppCompatActivity {

    public String CurStress="0";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public String sid = "",stitle = "",sbody = "";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockdown_blog);
        String message = getIntent().getStringExtra("url");
        setTitle("");
        /*back button*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        webView = findViewById(R.id.res_body);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        sendAndRequestResponse(message);
    }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.activity, menu);
      return true;
  }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.act_item1 || id==R.id.act_item2)
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

                } catch (JSONException | ExecutionException | InterruptedException e) {
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

        JSONObject real_data = new JSONObject(data);
        String Body = (String) real_data.get("bodyHTML");
        Body="<style>img{display: inline;height: auto;max-width: 100%;}</style>"+Body+"<p style=\"color:black;font-size:24px;\">You can practice some tasks pressing <b><i>TASK</i></b> on top right corner,which are very much helpful.</p>";
        webView.loadDataWithBaseURL(null,Body,"text/html","utf-8",null);
        Log.e("poo",Body);
        JSONArray Jtask = real_data.getJSONArray("activities");
        for(int j=0;j<Jtask.length();++j){
            JSONObject objj = (JSONObject) Jtask.get(j);

            sid+=(String) objj.get("_id")+'#';
            stitle+= (String) objj.get("title")+'#';
            sbody+= (String) objj.get("body")+'#';
        }

    }
}
