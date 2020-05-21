package com.example.corona;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GeneralReg extends AppCompatActivity {

    public String EmailString, PhoneString, PasswordString, NameString, CPasswordString, GenderString;
    String url = "https://bad-blogger.herokuapp.com/admin/register/general";
    RadioButton radioButton;
    RadioGroup radioGroup;

    public static boolean isValidPhoneNumber(String data) {
        if (data.length() == 0) return true;
        return data.length() == 11 && data.charAt(0) == '0';
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("General User Registration");
        /*back button*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button joinus = findViewById(R.id.joinus);
        EditText Name = findViewById(R.id.name);
        EditText Email = findViewById(R.id.email);
        EditText Password = findViewById(R.id.password);
        EditText confirmpass = findViewById(R.id.conpassword);
        EditText Phone = findViewById(R.id.phone);
        radioGroup = (RadioGroup) findViewById(R.id.radioGeneral);


        joinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(GeneralReg.this, "Select gender", Toast.LENGTH_SHORT).show();
                } else {
                    GenderString = (String) radioButton.getText();
                    Toast.makeText(GeneralReg.this, GenderString, Toast.LENGTH_SHORT).show();
                }

                NameString = Name.getText().toString();
                EmailString = Email.getText().toString();
                PhoneString = Phone.getText().toString();
                PasswordString = Password.getText().toString();
                CPasswordString = confirmpass.getText().toString();
                Log.e("Response", PasswordString);
                if (!isValidPhoneNumber(PhoneString.trim())) {
                    Toast.makeText((GeneralReg.this), "Enter Valid Phone number", Toast.LENGTH_SHORT).show();

                } else if (!validateEmail(EmailString.trim())) {
                    Toast.makeText((GeneralReg.this), "Enter Valid mail", Toast.LENGTH_SHORT).show();
                } else if (EmailString.length() == 0 && PhoneString.length() == 0) {
                    Toast.makeText((GeneralReg.this), "Enter Valid mail or phone number or both", Toast.LENGTH_SHORT).show();
                } else if (PasswordString.length() < 6) {
                    Toast.makeText((GeneralReg.this), "Length Must be grater than 6", Toast.LENGTH_SHORT).show();
                } else if (!CPasswordString.equals(PasswordString)) {
                    Toast.makeText((GeneralReg.this), "Password unmatched!!", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject data = new JSONObject();
                    try {
                        data.put("name", NameString);
                        data.put("email", EmailString);
                        data.put("phoneNumber", PhoneString);
                        data.put("password", PasswordString);
                        String res = data.toString();
                        Log.e("Response", data.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    HttpPostRequest httpPostRequest = new HttpPostRequest();
                    httpPostRequest.execute(url, data.toString());
                }
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateEmail(String data) {
        if (data.length() == 0) return true;
        Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher emailMatcher = emailPattern.matcher(data);
        return emailMatcher.matches();
    }

    public class HttpPostRequest extends AsyncTask<String, Void, String> {
        String verdict, message;
        ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(GeneralReg.this, "ProgressDialog", "Wait");

        }

        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection;
            String url = strings[0];
            String data = strings[1];
            String result = null;
            try {

                /*JSONObject res = new JSONObject();
                res.put("name","Reaj");
                res.put("username","Reaj");
                res.put("email","reaj123@gmail.com");
                res.put("password","Reaj123");
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

                Log.e("Res2", data);
                Log.e("Response2", sb.toString());


                //////////////////////test

                JSONObject reader = new JSONObject(sb.toString());
                verdict = reader.getString("status");
                message = reader.getString("msg");
                Log.e("Response2(msg)", message);
                //////////////test
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (verdict.equals("true")) {
                Intent intent = new Intent(GeneralReg.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                Toast.makeText((GeneralReg.this), "Successfully Regstered", Toast.LENGTH_SHORT).show();

            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(GeneralReg.this);
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




