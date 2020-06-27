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
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpertReg extends AppCompatActivity {
    EditText country;
    String[] countryNames = {"abc", "bcd"};
    RadioButton radioButton;
    RadioGroup radioGroup;
    Button joinUs;
    EditText name, email, mobile, password, cpassword, affiliation, city, designation, licence, hiDegree, institute, field;
    String stringName, stringEmail, stringMobile, stringPass, stringCpassword, stringAffiliation, stringCountry, stringCity, stringDesignation, stringLicence, stringHDegree, stringInstitute, stringfield, GenderString;
    String url = "https://bad-blogger.herokuapp.com/admin/register/expert";

    public static boolean isValidPhoneNumber(String data) {
        return data.length() == 11 && data.charAt(0) == '0';
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expertreg);


        setTitle("Expert Registration");
        /*back button*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        joinUs = findViewById(R.id.ex_joinus);
        name = findViewById(R.id.ex_name);
        email = findViewById(R.id.ex_email);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.ex_pass);
        cpassword = findViewById(R.id.ex_cpass);
        affiliation = findViewById(R.id.affiliation);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        designation = findViewById(R.id.designation);
        licence = findViewById(R.id.licence);
        hiDegree = findViewById(R.id.hdegree);
        institute = findViewById(R.id.institute);
        field = findViewById(R.id.field);
        radioGroup = (RadioGroup) findViewById(R.id.radioExpert);

        joinUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 1;
                stringName = name.getText().toString();
                flag *= stringName.length();

                stringEmail = email.getText().toString();
                flag *= stringEmail.length();

                stringMobile = mobile.getText().toString();
                flag *= stringMobile.length();

                stringPass = password.getText().toString();
                flag *= stringPass.length();

                stringCpassword = cpassword.getText().toString();
                flag *= stringCpassword.length();

                stringAffiliation = affiliation.getText().toString();
                flag *= stringAffiliation.length();

                stringDesignation = designation.getText().toString();
                flag *= stringDesignation.length();

                stringCountry = country.getText().toString();
                flag *= stringCountry.length();

                stringCity = city.getText().toString();
                flag *= stringCity.length();

                stringLicence = licence.getText().toString();
                flag *= stringLicence.length();

                stringHDegree = hiDegree.getText().toString();
                flag *= stringHDegree.length();

                stringInstitute = institute.getText().toString();
                flag *= stringInstitute.length();

                stringfield = field.getText().toString();
                flag *= stringfield.length();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                if (selectedId != -1) {
                    GenderString = (String) radioButton.getText();
                 }

                if (flag == 0 || selectedId == -1) {
                    Toast.makeText((ExpertReg.this), "You have to enter all the field", Toast.LENGTH_SHORT).show();
                } else if (!isValidPhoneNumber(stringMobile.trim())) {
                    Toast.makeText((ExpertReg.this), "Enter Valid Phone number", Toast.LENGTH_SHORT).show();
                } else if (!validateEmail(stringEmail.trim())) {
                    Toast.makeText((ExpertReg.this), "Enter Valid mail", Toast.LENGTH_SHORT).show();
                } else if (stringPass.length() < 6) {
                    Toast.makeText((ExpertReg.this), "Length Must be grater than 6", Toast.LENGTH_SHORT).show();
                } else if (!stringCpassword.equals(stringPass)) {
                    Toast.makeText((ExpertReg.this), "Password unmatched!!", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject res = new JSONObject();
                    try {
                        res.put("name", stringName);
                        res.put("email", stringEmail);
                        res.put("phoneNumber", stringMobile);
                        res.put("password", stringPass);
                        res.put("currentAffiliation", stringAffiliation);
                        res.put("designation", stringDesignation);
                        res.put("city", stringCity);
                        res.put("country", stringCountry);
                        res.put("license", stringLicence);
                        res.put("hDegree", stringHDegree);
                        res.put("institute", stringInstitute);
                        res.put("field", stringfield);
                        HttpPostRequest httpPostRequest = new HttpPostRequest();
                        String verdict = httpPostRequest.execute(url, res.toString()).get();
                    } catch (JSONException | InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

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
        Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher emailMatcher = emailPattern.matcher(data);
        return emailMatcher.matches();
    }

    public class HttpPostRequest extends AsyncTask<String, Void, String> {
        String verdict, message;
        ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ExpertReg.this, "ProgressDialog", "Wait");

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

                JSONObject reader = new JSONObject(sb.toString());
                verdict = reader.getString("status");
                message = reader.getString("msg");

                bufferedReader.close();
                result = sb.toString();

                Log.e("Res2", data);
                Log.e("Response2", sb.toString());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            /*Intent intent = new Intent(GeneralReg.this,LoginActivity.class);
            startActivity(intent);*/
            if (verdict.equals("true")) {
                Intent intent = new Intent(ExpertReg.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                Toast.makeText((ExpertReg.this), "Successfully Regstered", Toast.LENGTH_SHORT).show();

            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ExpertReg.this);
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

