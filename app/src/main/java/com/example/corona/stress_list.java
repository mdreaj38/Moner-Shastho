package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class stress_list extends AppCompatActivity {


    public ArrayList<String> mydata = new ArrayList<String>();

    public ArrayList<String> situation = new ArrayList<String>();
    public ArrayList<String> stress = new ArrayList<String>();
    public ArrayList<String> response = new ArrayList<String>();
    public ArrayList<String> stress_after_response = new ArrayList<String>();

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_list);
         /*back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Stress List");

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String Id = pref.getString("id", "");
        String url = "https://bad-blogger.herokuapp.com/users/profile/get-stress/"+Id;
        ProgressDialog progressDialog = ProgressDialog.show(stress_list.this, "Loading...", "");

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("ch--eck--",response.toString());
                try {
                    parse_json_data(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
    public void parse_json_data(String data) throws JSONException {
        JSONObject jb = new JSONObject(data);
        JSONArray ja = jb.getJSONArray("records");
        Log.e("ch--eck--", String.valueOf(ja.length()));

        for(int i=0;i<ja.length();++i){
            JSONObject temp = (JSONObject) ja.get(i);
            mydata.add((String) temp.get("displayDate"));
            situation.add((String) temp.get("situation"));
            stress.add((String) temp.get("level"));
            response.add((String) temp.get("response"));
            stress_after_response.add((String) temp.get("postResponse"));
        }



        ListView listView = findViewById(R.id.stress_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(stress_list.this, R.layout.sample_view, R.id.textView, mydata);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animation animation1 = new AlphaAnimation(0.7f, 1.0f);
                animation1.setDuration(2000);
                view.startAnimation(animation1);
                String temp = mydata.get(position);
                String cur = situation.get(position)+"#"+stress.get(position)+"#"+response.get(position)+
                        "#"+stress_after_response.get(position)+'#'+temp;
                Intent intent = new Intent(stress_list.this, show_stress.class);
                intent.putExtra("stress_body",cur);
                startActivity(intent);
            }
        });
    }
}