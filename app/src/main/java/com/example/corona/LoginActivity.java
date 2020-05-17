package com.example.corona;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mongodb.stitch.android.core.StitchAppClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    String[] user_type;
    /*ProgressBar pgsBar;*/
    ProgressDialog pgsdialog;
    TextView forgetPass;
    public String email1, pass1;
    String string_email_login, string_email_pass;

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        pgsdialog = new ProgressDialog(LoginActivity.this);
        pgsdialog.setTitle("Please Wait");
        pgsdialog.setMessage("Logging In..");
        // pgsBar = (ProgressBar) findViewById(R.id.pBar);


        Button signup = findViewById(R.id.signup);
        Handler handler = new Handler();
        signup.setOnClickListener(handler);
        Button logIn = findViewById(R.id.login);
        logIn.setOnClickListener(handler);

        //forget password section
        forgetPass = findViewById(R.id.forget_password);
        forgetPass.setOnClickListener(handler);

        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
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
                            finish();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, ExpertReg.class);
                            startActivity(intent);
                            finish();
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

                    res.put("device","android");
                    res.put("handle","email");
                    res.put("email",email1);
                    res.put("password",pass1);

                    new HttpPostRequest().execute("https://bad-blogger.herokuapp.com/admin/login",res.toString()).get();


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
                intent.putExtra("email",email1);
                startActivity(intent);*/

                pgsdialog.dismiss();
            }


            if (v.getId() == R.id.forget_password) {
                Intent intent = new Intent(LoginActivity.this, forget_password.class);
                startActivity(intent);
            }
        }
    }

    public class HttpPostRequest extends AsyncTask<String, Void, String> {
        String verdict, message;
        ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this, "ProgressDialog", "Wait");

        }

        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection;
            String url = strings[0];
            String data = strings[1];
            String result = null;
            try {

                /*JSONObject res = new JSONObject();
                res.put("name","Reaj");
                data = res.toString();*/

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
                writer.flush();
                writer.close();
                outputStream.close();

                int responseCode = urlConnection.getResponseCode();
                Log.e("Response Code : ", "" + responseCode);

                //Read
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                Log.e("Responsedata", data);
                Log.e("Responsesb", sb.toString());

                //////////////////////test

               /* JSONObject reader = new JSONObject(sb.toString());
                verdict = reader.getString("status");
                message = reader.getString("msg");
                Log.e("Response2(msg)", message);*/

                //////////////test

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
           /* if (verdict.equals("true")) {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText((LoginActivity.this), "Successfully Regstered", Toast.LENGTH_SHORT).show();

            } else {
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(LoginActivity.this);
                builder1.setCancelable(false);
                builder1.setTitle(message);
                builder1.setMessage("Try again");

                builder1.setPositiveButton(
                        Html.fromHtml("<font color='#FF0000'>Ok</font>"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                android.app.AlertDialog alert11 = builder1.create();
                alert11.show();
            }*/
            super.onPostExecute(s);
        }
    }

}

