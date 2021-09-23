package com.monershastho.corona;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class activity_profile_expert_user_2 extends AppCompatActivity {
    SharedPreferences pref;
    String email, password, phoneNo, designation, organization,user_name,user_id;
    EditText institute, license, hdegree, field, country, city,Epassword;
    Button update;
    String string_institute, string_license, string_hdegree, string_field, string_country, string_city,string_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_expert_user_2);
        setTitle("Profile");

        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //fetch data from db
        pref = getApplicationContext().getSharedPreferences("MyPrefExpert", 0); // 0 - for private mode
        email = pref.getString("email", null);
        user_id=pref.getString("id",null);
        user_name = pref.getString("name",null);
        phoneNo = pref.getString("phoneNo", null);
        designation = pref.getString("designation", null);
        organization = pref.getString("organization", null);
        password = pref.getString("password",null);
        string_license = pref.getString("license",null);

        String t_ins = pref.getString("institute",null);
        String t_hdeg =pref.getString("hDegree",null);
        String t_field = pref.getString("field",null);
        String t_city = pref.getString("city",null);
        String t_country = pref.getString("country",null);

        institute = findViewById(R.id.expert_institute);
        license = findViewById(R.id.expert_license);
        hdegree = findViewById(R.id.expert_hDegree);
        field = findViewById(R.id.expert_field);
        country = findViewById(R.id.expert_country);

        city = findViewById(R.id.expert_city);
        Epassword = findViewById(R.id.expert_password);

        institute.setText(t_ins);
        hdegree.setText(t_hdeg);
        field.setText(t_field);
        country.setText(t_country);
        city.setText(t_city);


        update = findViewById(R.id.expertUpdateProfileButton);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string_institute = institute.getText().toString();
                 string_field = field.getText().toString();
                string_hdegree = hdegree.getText().toString();
                string_country = country.getText().toString();
                string_city = city.getText().toString();
                String temp = Epassword.getText().toString();
                /*Log.e("kop","ok");
                Log.e("kop","ok3"+password);

                Log.e("kop",temp+" <> "+password);*/
                if(temp.equals(password)){
                    //update database here
                    //1.make json object
                    //2.json->string
                    //3.class call

                    JSONObject object = new JSONObject();
                    try{
                        object.put("user_id",user_id);
                        object.put("device","android");
                        object.put("name",user_name);
                        object.put("email",email);
                        object.put("phoneNumber",phoneNo);
                        object.put("designation",designation);
                        object.put("organization",organization);
                        object.put("license",license);
                        object.put("institute",string_institute);
                        object.put("hDegree",string_hdegree);
                        object.put("field",string_field);
                        object.put("city",string_city);
                        object.put("country",string_country);

                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                    String url = "https://monershastho.herokuapp.com/users/update-expert-profile";
                    try{
                        new HttpPostRequest().execute(url,object.toString()).get();
                    }
                    catch (ExecutionException | InterruptedException e){
                        e.printStackTrace();
                    }


                }
                else{
                    android.app.AlertDialog.Builder builder1 =
                            new android.app.AlertDialog.Builder(activity_profile_expert_user_2.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Wrong Password!!");
                    builder1.setPositiveButton(
                            Html.fromHtml("<font color='#FF0000'>Ok</font>"),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    android.app.AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
            }
        });
    }
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
            progressDialog = ProgressDialog.show(activity_profile_expert_user_2.this, "", "Wait");

        }

        protected Void doInBackground(String... strings) {
            HttpURLConnection urlConnection;
            String url = strings[0];
            String data = strings[1];
            Log.e("Response23", data);
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
                bufferedReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(Void code) {
            progressDialog.dismiss();
            Log.e("Response23--",sb.toString());

            SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = Objects.requireNonNull(pref).edit();
            editor.putString("name", user_name);
            editor.apply();

            Intent intent = new Intent(activity_profile_expert_user_2.this, MainScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            super.onPostExecute(code);
        }
    }


}


