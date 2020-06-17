package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ques_ans_select_box extends AppCompatActivity {
    public int i, flag = 0;

    public ArrayList<String> Radio_Question = new ArrayList<String>();
    public ArrayList<String> option1  = new ArrayList<String>();
    public ArrayList<String> option2  = new ArrayList<String>();
    public ArrayList<String> option3  = new ArrayList<String>();
    public ArrayList<String> option4  = new ArrayList<String>();
    int[][] score = new int[150][150];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_ans_select_box);

        TextView question = findViewById(R.id.textQuestion);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        RadioButton button1 = findViewById(R.id.radio_button1);
        RadioButton button2 = findViewById(R.id.radio_button2);
        RadioButton button3 = findViewById(R.id.radio_button3);
        RadioButton button4 = findViewById(R.id.radio_button4);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);


        String ID = "5ee8d2ea2c44dd0017edd2b3";
        try {
            new HttpGetRequest().execute(ID).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //set radiobutton options
        button1.setText(option1.get(0));
        button2.setText(option2.get(0));
        button3.setText(option3.get(0));
        button4.setText(option4.get(0));


        //set question
        String[] s = new String[100];
        for(int i=0;i<Radio_Question.size();++i){
            s[i] = Radio_Question.get(i);
        }
        List<String> stringArrayList = new ArrayList<>(Arrays.asList(s));
        question.setText(s[0]);
        i = 1;
        int len = Radio_Question.size();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    Toast toast = Toast.makeText(ques_ans_select_box.this, "Choose an answer!", Toast.LENGTH_SHORT);
                    View view1 = toast.getView();
                    view1.setBackgroundResource(R.color.colorAccent);
                    toast.show();
                } else if(i<len){
                    question.setText(stringArrayList.get(i));
                    radioGroup.clearCheck();
                    flag = 0;

                    //set radiobutton options
                    button1.setText(option1.get(i));
                    button2.setText(option2.get(i));
                    button3.setText(option3.get(i));
                    button4.setText(option4.get(i));
                    Toast.makeText(ques_ans_select_box.this, "length" + stringArrayList.get(i%3), Toast.LENGTH_SHORT).show();
                    i += 1;
                    //startActivity(intent);
                }else{
                    Intent intent = new Intent(ques_ans_select_box.this, qus_ans_slider.class);
                    intent.putExtra("q_id",ID);
                    startActivity(intent);
                }
            }
        });
    }

    //get data from here
    public void onRadioButtonClicked(View view) {
        flag = 0;
        boolean checked = ((RadioButton) view).isChecked();
        String str = "";
        // Check which radio button was clicked
        int id = view.getId();
        if (id == R.id.radio_button1) {
            if (checked) {
                str = "1";
                flag = 1;
                Toast.makeText(ques_ans_select_box.this, "checked 1!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.radio_button2) {
            if (checked) {
                str = "2";
                flag = 1;
            }
        } else if (id == R.id.radio_button3) {
            if (checked) {
                str = "3";
                flag = 1;
            }
        }
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
           // progressDialog = ProgressDialog.show(Read_Diary.this, "", "Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String st = strings[0];
                URL url = new URL("https://bad-blogger.herokuapp.com/app-admin/single-test/"+strings[0]+"?device=android");
                Log.e("Check", url.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //Set method
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                Log.e("Check", url.toString());

                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                Log.e("dataaa1", data.toString());

                JSONObject jo = new JSONObject(data);
                jo = jo.getJSONObject("data");
                JSONArray JA = jo.getJSONArray("questionSet");

                Log.e("dataaa2", jo.toString());
                Log.e("dataaa2", String.valueOf(JA.length()));

                for(int i=0;i<JA.length();++i){
                    JSONObject obj = (JSONObject) JA.get(i);
                    String type = (String) obj.get("inputType");
                    if(type.equals("radio")){
                        Log.e("dataaa2", "Radio");

                        Radio_Question.add((String) obj.get("question"));

                        JSONArray op = obj.getJSONArray("Options");
                        Log.e("dataaa2", String.valueOf(op.length()));



                        JSONObject cur = (JSONObject) op.get(0);
                        option1.add((String) cur.get("option"));
                        score[i][0] = Integer.parseInt((String) cur.get("scale"));
                        Log.e("dataaa21", (String) cur.get("option"));

                        cur = (JSONObject) op.get(1);
                        option2.add((String) cur.get("option"));
                        score[i][1] = Integer.parseInt((String) cur.get("scale"));
                        Log.e("dataaa22", (String) cur.get("option"));

                        cur = (JSONObject) op.get(2);
                        option3.add((String) cur.get("option"));
                        score[i][2] = Integer.parseInt((String) cur.get("scale"));
                        Log.e("dataaa23", (String) cur.get("option"));


                        cur = (JSONObject) op.get(3);
                        option4.add((String) cur.get("option"));
                        score[i][3] = Integer.parseInt((String) cur.get("scale"));
                        Log.e("dataaa24", (String) cur.get("option"));

                    }
                    else {
                        Log.e("dataaa2", "Ranger");

                    }

                }


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String aVoid) {
          //  progressDialog.cancel();
            super.onPostExecute(aVoid);
        }
    }

}

