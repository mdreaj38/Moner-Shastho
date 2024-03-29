package com.monershastho.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ques_ans_select_box extends AppCompatActivity {
    public int i, flag = 0;

    public ArrayList<String> Radio_Question = new ArrayList<String>();
    public ArrayList<String> option1  = new ArrayList<String>();
    public ArrayList<String> option2  = new ArrayList<String>();
    public ArrayList<String> option3  = new ArrayList<String>();
    public ArrayList<String> option4  = new ArrayList<String>();
    public ArrayList<String> option5  = new ArrayList<String>();
    public ArrayList<String> option6  = new ArrayList<String>();
    public ArrayList<String> option7  = new ArrayList<String>();

    public ArrayList<Integer> scale  = new ArrayList<Integer>();

    int[][] score = new int[150][150];
    int cur_score=0,select=-1;


    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_ans_select_box);

        /*back button*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView question = findViewById(R.id.textQuestion);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        RadioButton button1 = findViewById(R.id.radio_button1);
        RadioButton button2 = findViewById(R.id.radio_button2);
        RadioButton button3 = findViewById(R.id.radio_button3);
        RadioButton button4 = findViewById(R.id.radio_button4);
        RadioButton button5 = findViewById(R.id.radio_button5);
        RadioButton button6 = findViewById(R.id.radio_button6);
        RadioButton button7 = findViewById(R.id.radio_button7);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);


        String ID = getIntent().getStringExtra("ID");
        try {
            new HttpGetRequest().execute(ID).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        //set question
        String[] s = new String[500];
        int Limit = Radio_Question.size();
        for(int i=0;i<Limit;++i){
            s[i] = "("+String.valueOf(i+1)+"/"+String.valueOf(Limit)+") "+Radio_Question.get(i);
        }
        List<String> stringArrayList = new ArrayList<>(Arrays.asList(s));

        //set radiobutton options
        button1.setText(option1.get(0));
        button2.setText(option2.get(0));
        button3.setText(option3.get(0));
        button4.setText(option4.get(0));
        button5.setText(option5.get(0));
        button6.setText(option6.get(0));
        button7.setText(option7.get(0));

        question.setText(s[0]);
        i = 0;
       int len = Radio_Question.size();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    Toast toast = Toast.makeText(ques_ans_select_box.this, "Choose an answer!", Toast.LENGTH_SHORT);
                    View view1 = toast.getView();
                    view1.setBackgroundResource(R.color.red);
                    toast.show();
                }
                else if(i<len-1){
                    cur_score+= (scale.get(i) *score[i][select]);
                    i += 1;
                    question.setText(stringArrayList.get(i));
                    radioGroup.clearCheck();
                    flag = 0;

                    //set radiobutton options
                    button1.setText(option1.get(i));
                    button2.setText(option2.get(i));
                    button3.setText(option3.get(i));
                    button4.setText(option4.get(i));
                    button5.setText(option5.get(i));
                    button6.setText(option6.get(i));
                    button7.setText(option7.get(i));

                }
                else if(i==len-1){
                    cur_score+= (scale.get(i) *score[i][select]);
                    pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    editor = Objects.requireNonNull(pref).edit();
                    editor.putString("ques_score", String.valueOf(cur_score));
                    editor.apply();

                    Intent intent = new Intent(ques_ans_select_box.this, qus_ans_slider.class);
                    intent.putExtra("t_id",ID);
                    startActivity(intent);
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
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
                select = 0;
            }
        } else if (id == R.id.radio_button2) {
            if (checked) {
                str = "2";
                flag = 1;
                select = 1;
            }
        } else if (id == R.id.radio_button3) {
            if (checked) {
                str = "3";
                flag = 1;
                select = 2;
            }
        }else if (id == R.id.radio_button4) {
            if (checked) {
                str = "4";
                flag = 1;
                select = 3;
            }
        }else if (id == R.id.radio_button5) {
            if (checked) {
                str = "5";
                flag = 1;
                select = 4;
            }
        }else if (id == R.id.radio_button6) {
            if (checked) {
                str = "6";
                flag = 1;
                select = 5;
            }
        }else if (id == R.id.radio_button7) {
            if (checked) {
                str = "7";
                flag = 1;
                select = 6;
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
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://monershastho.herokuapp.com/app-admin/single-test/"+strings[0]+"?device=android");
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
                jo = jo.getJSONObject("data");
                JSONArray JA = jo.getJSONArray("questionSet");
                int ll = JA.length();
                int c = 0;
                for(int i=0;i<JA.length();++i){
                    JSONObject obj = (JSONObject) JA.get(i);
                    String type = (String) obj.get("inputType");
                    if(type.equals("radio")){
                        c++;

                        Radio_Question.add((String) obj.get("question"));
                        scale.add(Integer.parseInt((String) obj.get("scale")));

                        JSONArray op = obj.getJSONArray("Options");


                        JSONObject cur = (JSONObject) op.get(0);
                        option1.add((String) cur.get("option"));
                        score[i][0] = Integer.parseInt((String) cur.get("scale"));

                        cur = (JSONObject) op.get(1);
                        option2.add((String) cur.get("option"));
                        score[i][1] = Integer.parseInt((String) cur.get("scale"));

                        cur = (JSONObject) op.get(2);
                        option3.add((String) cur.get("option"));
                        score[i][2] = Integer.parseInt((String) cur.get("scale"));


                        cur = (JSONObject) op.get(3);
                        option4.add((String) cur.get("option"));
                        score[i][3] = Integer.parseInt((String) cur.get("scale"));

                        cur = (JSONObject) op.get(4);
                        option5.add((String) cur.get("option"));
                        score[i][4] = Integer.parseInt((String) cur.get("scale"));

                        cur = (JSONObject) op.get(5);
                        option6.add((String) cur.get("option"));
                        score[i][5] = Integer.parseInt((String) cur.get("scale"));

                        cur = (JSONObject) op.get(6);
                        option7.add((String) cur.get("option"));
                        score[i][6] = Integer.parseInt((String) cur.get("scale"));

                    }
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}

