package com.example.corona;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    public String email1, pass1;
    String[] user_type;
    ProgressDialog pgsdialog;
    TextView forgetPass;
    String string_email_login, string_email_pass;
    JSONObject jb,jc;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pgsdialog = new ProgressDialog(LoginActivity.this);
        pgsdialog.setTitle("Please Wait");
        pgsdialog.setMessage("Logging In..");

        Button signup = findViewById(R.id.signup);
        Handler handler = new Handler();
        signup.setOnClickListener(handler);
        Button logIn = findViewById(R.id.login);
        logIn.setOnClickListener(handler);

        //forget password section
        forgetPass = findViewById(R.id.forget_password);
        forgetPass.setOnClickListener(handler);

    }

    class Handler implements View.OnClickListener {
        EditText emailLogin = findViewById(R.id.email_login);
        EditText passLogin = findViewById(R.id.password_login);

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.signup) {
                user_type = new String[]{"General", "Expert"};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(LoginActivity.this);
                mbuilder.setTitle("Choose One");
                mbuilder.setSingleChoiceItems(user_type, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                        if (which == 0) {
                            Intent intent = new Intent(LoginActivity.this, GeneralReg.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, ExpertReg.class);
                            startActivity(intent);
                        }
                        dialog.dismiss();
                    }
                });
                mbuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog mdialog = mbuilder.create();
                mdialog.show();
            }
            if (v.getId() == R.id.login) {

                string_email_login = emailLogin.getText().toString();
                string_email_pass = passLogin.getText().toString();
                email1 = string_email_login;
                pass1 = string_email_pass;
                JSONObject res = new JSONObject();
                try {
                    res.put("device", "android");
                    res.put("handle", "email");
                    res.put("email", email1);
                    res.put("password", pass1);
                    String res1 = res.toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpPostRequest httpPostRequest = new HttpPostRequest();
                try {
                    httpPostRequest.execute("https://bad-blogger.herokuapp.com/admin/login/android", res.toString()).get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                pgsdialog.dismiss();
            }

            if (v.getId() == R.id.forget_password) {
                Intent intent = new Intent(LoginActivity.this, forget_password.class);
                startActivity(intent);
            }
        }
    }


    public class HttpPostRequest extends AsyncTask<String, Void, Void> {
        String verdict, message;
        ProgressDialog progressDialog;
        int statusCode;
        StringBuilder sb = new StringBuilder();

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this, "Login...", "Wait");
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
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                writer.write(data);
                writer.close();
                outputStream.close();

                //Read
                statusCode = urlConnection.getResponseCode();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
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
            if (statusCode == 200) {
                String user_name = "", _gender = "",u_type="",ID="";
                try {
                    jb = new JSONObject(sb.toString());

                    jc = jb.getJSONObject("user");
                    user_name = (String) jc.get("name");
                    ID = (String) jc.get("_id");
                    //_gender = (String) jc.get("gender");
                    _gender = "male";
                    u_type = (String) jc.get("userType");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                editor = Objects.requireNonNull(pref).edit();
                editor.putString("name", user_name);
                editor.putString("email", string_email_login);
                editor.putString("CurStress","0");
                editor.putString("password",pass1);
                editor.putString("gender", _gender);
                editor.putString("id",ID);
                editor.putString("usertype",u_type);
                editor.putString("PreStress","0");
                editor.putString("count","0");
                editor.putString("AvgStress","0");
                editor.apply();
                if(u_type.equals("expert")){
                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(LoginActivity.this);
                    builder1.setCancelable(false);
                    builder1.setMessage(Html.fromHtml("Visit website login to get access as an Expert!<br><br>Do you want to visit?"));
                    builder1.setPositiveButton(
                            Html.fromHtml("<font color='#008000'>Yes</font>"),
                            new DialogInterface.OnClickListener() {
                                //
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bad-blogger.herokuapp.com"));
                                    startActivity(browserIntent);
                                }
                            });
                    builder1.setNegativeButton(
                            Html.fromHtml("<font color='#FF0000'>No</font>"),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    dialog.cancel();
                                }
                            });
                    android.app.AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
                else {
                    Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
                    startActivity(intent);
                }
            } else {
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(LoginActivity.this);
                builder1.setCancelable(false);
                builder1.setTitle("Invalid Mail or Password");
                builder1.setMessage("Try again...");
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
            super.onPostExecute(code);
        }
    }

}
