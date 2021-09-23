package com.monershastho.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class write_stressor extends AppCompatActivity {

     EditText s1,s2,s3,s4;
     String s_1,s_2,s_3,s_4;
     Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_stressor);
        s1 = findViewById(R.id.s1_id);
        s2 = findViewById(R.id.s2_id);
        s3 = findViewById(R.id.s3_id);
        s4 = findViewById(R.id.s4_id);
         button = findViewById(R.id.s_done);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_1 = s1.getText().toString();
                s_2 = s2.getText().toString();
                s_3 = s3.getText().toString();
                s_4 = s4.getText().toString();


                if(s_1.length()==0)  s_1="N/A";
                if(s_2.length()==0)  s_2="N/A";
                if(s_3.length()==0)  s_3="N/A";
                if(s_4.length()==0)  s_4="N/A";

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                String U_id = pref.getString("id", null);

                JSONObject params = new JSONObject();
                try {

                    params.put("user_id", U_id);
                    params.put("situation",s_1);
                    params.put("level", s_2);
                    params.put("response", s_3);
                    params.put("postResponse", s_4);

                    String res1 = params.toString();
                    Log.e("Response2", res1.toString());
                    HttpPostRequest httpPostRequest = new HttpPostRequest();

                    new HttpPostRequest().execute("https://monershastho.herokuapp.com/users/profile/add-stress/android",params.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }

   public class HttpPostRequest extends AsyncTask<String, Void, Void> {
       String verdict, message;
       ProgressDialog progressDialog;
       int statusCode;
       StringBuilder sb = new StringBuilder();

       protected void onPreExecute() {
           progressDialog = ProgressDialog.show(write_stressor.this, "", "Wait");

       }

       protected Void doInBackground(String... strings) {


           HttpURLConnection urlConnection;
           String url = strings[0];
           String data = strings[1];
           String result = null;
           try {

               //Connect
               urlConnection = (HttpURLConnection) ((new URL(url).openConnection()));
               urlConnection.setDoOutput(true);
               urlConnection.setRequestProperty("Content-Type", "application/json");
               urlConnection.setRequestProperty("Accept", "application/json");
               urlConnection.setRequestMethod("POST");
               urlConnection.connect();

               //Write
               OutputStream outputStream = urlConnection.getOutputStream();
               BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
               writer.write(data);
               writer.close();
               outputStream.close();

               //Read
               statusCode = urlConnection.getResponseCode();
               Log.e("Response222", Integer.toString(statusCode));
               Log.e("Response222", data);
               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

               String line = null;
               while ((line = bufferedReader.readLine()) != null) {
                   sb.append(line);
               }

               bufferedReader.close();

               Log.e("Response222", data);
               Log.e("Response222", sb.toString());

               //////////////////////test
           } catch (IOException e) {
               e.printStackTrace();
           }
           return null;
       }



       protected void onPostExecute(Void code) {
           progressDialog.dismiss();
           Intent intent = new Intent(write_stressor.this,Diary.class);
           //kill all the previous activity
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

           startActivity(intent);
           super.onPostExecute(code);
       }
   }

}