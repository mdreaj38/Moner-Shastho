package com.monershastho.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.concurrent.ExecutionException;

public class Write_diary extends AppCompatActivity {

    private EditText situation_Edittext,emotions_EditText , reactions_Edittext,thoughts_editText, behaviour_Edittext;
    private Button done_id;
    String situation,emotion,reaction,thought,behaviour;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);


        setTitle("Write your thought");
        /*back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        AlertDialog.Builder builder1 = new AlertDialog.Builder(Write_diary.this);
        builder1.setMessage("Situation:Whats your current situtation of your mental health now?"+"\n\n"+"Emotions:Write down how you feel now"
        +"\n\n"+"Thoughts:What are you thinking now?"+"\n\n"+"Reaction:How do you like to react now?"+"\n\n"+"Behaviour:What kind of behaviour do you have now?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Got it",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


        situation_Edittext = (EditText) findViewById(R.id.situation_id);
        emotions_EditText = (EditText) findViewById(R.id.emotions_id);
        thoughts_editText=(EditText) findViewById(R.id.thoughts_id);
        reactions_Edittext=(EditText) findViewById(R.id.reacitons_id);
        behaviour_Edittext=(EditText) findViewById(R.id.behaviour_ID);




        done_id = (Button) findViewById(R.id.done_id);

        done_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                situation = situation_Edittext.getText().toString();
                emotion = emotions_EditText.getText().toString();
                thought = thoughts_editText.getText().toString();
                reaction = reactions_Edittext.getText().toString();
                behaviour = behaviour_Edittext.getText().toString();
                int l = situation.length()+emotion.length()+thought.length()+reaction.length()+behaviour.length();
                //Toast.makeText(Write_diary.this, Integer.toString(l), Toast.LENGTH_SHORT).show();

                if(situation.length()==0) situation = "NA";
                if(emotion.length()==0) emotion = "NA";
                if(thought.length()==0) thought = "NA";
                if(reaction.length()==0) reaction = "NA";
                if(behaviour.length()==0) behaviour = "NA";
                if(l==0){
                    finish();
                }
                else{
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                String Id = pref.getString("id", "");
                Toast.makeText(Write_diary.this, "Done", Toast.LENGTH_SHORT).show();
                JSONObject res = new JSONObject();
                try {

                    res.put("user_id", Id);
                    res.put("situation", situation);
                    res.put("emotions", emotion);
                    res.put("thoughts", thought);
                    res.put("reactions", reaction);
                    res.put("behaiviour", behaviour);

                    new HttpPostRequest().execute("https://bad-blogger.herokuapp.com/users/profile/add-record/android",res.toString()).get();

                    String res1 = res.toString();
                    Log.e("Response", res1.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                }
            }
        });

        situation_Edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(situation_Edittext.hasFocus()){
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }

                return false;
            }
        });


        emotions_EditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(emotions_EditText.hasFocus()){
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }

                return false;
            }
        });


        thoughts_editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(thoughts_editText.hasFocus()){
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }

                return false;
            }
        });

        reactions_Edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(reactions_Edittext.hasFocus()){
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }

                return false;
            }
        });

        behaviour_Edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(behaviour_Edittext.hasFocus()){
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }

                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public class HttpPostRequest extends AsyncTask<String, Void, Void> {
        String verdict, message;
        ProgressDialog progressDialog;
        int statusCode;
        StringBuilder sb = new StringBuilder();

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Write_diary.this, "Wait...", "");

        }

        protected Void doInBackground(String... strings) {
            HttpURLConnection urlConnection;
            String url = strings[0];
            String data = strings[1];
            Log.e("dataaa",data);
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
                Log.e("code", Integer.toString(statusCode));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                Log.e("dataaa",sb.toString());

                bufferedReader.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(Void code) {
            progressDialog.dismiss();
            /*Intent intent = new Intent(Write_diary.this, Diary.class);
            startActivity(intent);*/
            finish();
            super.onPostExecute(code);
        }
    }

}
