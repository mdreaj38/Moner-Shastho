package com.monershastho.corona;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import java.util.Objects;

public class forget_password extends AppCompatActivity {

    EditText forgetEmail, newPass, confirmPass;
    String forget_email, new_pass, confirm_pass;
    ProgressDialog pgsdialog;
    TextView login;
    RadioGroup radioGroup;
    String url = "https://monershastho.herokuapp.com/admin/forgot_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Forget Password");


        pgsdialog = new ProgressDialog(forget_password.this);
        pgsdialog.setTitle("Please Wait");
        pgsdialog.setMessage("Updating Password..");
        forgetEmail = findViewById(R.id.forget_email);
        radioGroup = findViewById(R.id.radio);
        Button updatePass = findViewById(R.id.update_pass);
        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*update here*/
                int id = radioGroup.getCheckedRadioButtonId();
                if (id == -1) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(forget_password.this);
                    builder1.setCancelable(false);
                    builder1.setMessage(Html.fromHtml("<font color='#FF0000'>Select User Type</font>"));
                    builder1.setPositiveButton(
                            Html.fromHtml("Ok"),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    RadioButton rb = findViewById(id);
                    String text = rb.getText().toString();
                    text = text.toLowerCase();

                    JSONObject res = new JSONObject();
                    try {
                        res.put("email", forgetEmail.getText().toString());
                        res.put("userType", text);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    HttpPostRequest httpPostRequest = new HttpPostRequest();
                    httpPostRequest.execute(url, res.toString());

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


    public class HttpPostRequest extends AsyncTask<String, Void, String> {
        String verdict, message;
        ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(forget_password.this, "A link will be sent soon", "Wait");

        }

        protected String doInBackground(String... strings) {
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
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                JSONObject reader = new JSONObject(sb.toString());
                verdict = reader.getString("status");
                message = reader.getString("msg");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (verdict.equals("true")) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(forget_password.this);
                builder1.setCancelable(false);
                builder1.setTitle(message);

                builder1.setPositiveButton(
                        Html.fromHtml("<font color='#FF0000'>Ok</font>"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                Intent intent = new Intent(forget_password.this, LoginActivity.class);
                startActivity(intent);
                finish();

            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(forget_password.this);
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
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
            super.onPostExecute(s);
        }
    }
}
