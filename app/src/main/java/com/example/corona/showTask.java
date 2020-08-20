package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
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

import static android.text.TextUtils.replace;
import static java.lang.Integer.parseInt;

public class showTask extends AppCompatActivity {

    SharedPreferences pref;
   // TextView textView;

   WebView textView;
    public String CurStress,body,_Id;
    SharedPreferences.Editor editor;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    public String screen_title ="";
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        textView = (WebView )findViewById(R.id.taskbodyy);
        setTitle("Follow This Step");

        textView.getSettings().setJavaScriptEnabled(true);
        textView.getSettings().setDomStorageEnabled(true);
        textView.getSettings().setAppCacheEnabled(true);
        textView.getSettings().setLoadsImagesAutomatically(true);
         textView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        _Id = getIntent().getStringExtra("curbody");

        sendAndRequestResponse("https://bad-blogger.herokuapp.com/users/materials/task/"+_Id);

    }

    public void onBackPressed() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Select your stress level now");
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
                pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                String current_stress = pref.getString("CurStress","0");
                String pre_stress  = pref.getString("PreStress","0");
                String cnt = pref.getString("count","0");
              //  int avg = Integer.parseInt(avgstress);
                int c = Integer.parseInt(cnt);
                int now_stress = (Integer.parseInt(CurStress)*100)/120;
                int p_stress  = Integer.parseInt(pre_stress);
                int diff  = now_stress-p_stress;

                now_stress = (diff+(Integer.parseInt(current_stress)))/2;

                pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                editor = Objects.requireNonNull(pref).edit();
                editor.putString("count",cnt);
                editor.putString("CurStress", Integer.toString(now_stress));
                editor.apply();
               // Toast.makeText(getApplicationContext(),Integer.toString(avg),Toast.LENGTH_LONG).show();
                finish();
            }
        });

        alert.show();
    }
    private void sendAndRequestResponse(String url) {

        ProgressDialog progressDialog = ProgressDialog.show(showTask.this, "Loading...", "");

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("ch--eck--",response.toString());
                parse_json_data(response.toString());
                progressDialog.cancel();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MY_error","++++");
            }
        });

        mRequestQueue.add(mStringRequest);
    }

     public void parse_json_data(String data){
        try {
            JSONObject jo = new JSONObject(data);
            JSONArray JA = jo.getJSONArray("data");

            jo = (JSONObject) JA.get(0);
            body = (String) jo.get("body");
            screen_title = (String) jo.get("title");
            setTitle(screen_title);
            textView.loadDataWithBaseURL(null,"<style>img{display: inline;height: auto;max-width: 100%;}</style>"+body,"text/html","utf-8",null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}