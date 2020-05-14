package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;

import org.bson.Document;
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
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpertReg extends AppCompatActivity {
    Button joinUs;
    EditText name, email, mobile,password,cpassword, affiliation, country, city, designation, licence, hiDegree, institute,field;
    String stringName, stringEmail,stringMobile, stringPass, stringCpassword, stringAffiliation, stringCountry,stringCity, stringDesignation, stringLicence, stringHDegree, stringInstitute,stringfield;
    String url = "https://bad-blogger.herokuapp.com/admin/register/expert";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expertreg);
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
        joinUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 1;
                stringName = name.getText().toString();
                flag*=stringName.length();

                stringEmail = email.getText().toString();
                flag*=stringEmail.length();

                stringMobile = mobile.getText().toString();
                flag*=stringMobile.length();

                stringPass = password.getText().toString();
                flag*=stringPass.length();

                stringCpassword = cpassword.getText().toString();
                flag*=stringCpassword.length();

                stringAffiliation = affiliation.getText().toString();
                flag*=stringAffiliation.length();

                stringDesignation = designation.getText().toString();
                flag*=stringDesignation.length();

                stringCountry = country.getText().toString();
                flag*=stringCountry.length();

                stringCity = city.getText().toString();
                flag*=stringCity.length();

                stringLicence = licence.getText().toString();
                flag*=stringLicence.length();

                stringHDegree = hiDegree.getText().toString();
                flag*=stringHDegree.length();

                stringInstitute = institute.getText().toString();
                flag*=stringInstitute.length();

                stringfield = field.getText().toString();
                flag*=stringfield.length();

                if(flag==0){
                    Toast.makeText((ExpertReg.this), "You have to enter all the field", Toast.LENGTH_SHORT).show();
                }
                else if(!isValidPhoneNumber(stringMobile.trim())){
                    Toast.makeText((ExpertReg.this), "Enter Valid Phone number", Toast.LENGTH_SHORT).show();
                }
                else if(!validateEmail(stringEmail.trim())){
                    Toast.makeText((ExpertReg.this), "Enter Valid mail", Toast.LENGTH_SHORT).show();
                }
                else if(stringPass.length()<6){
                    Toast.makeText((ExpertReg.this), "Length Must be grater than 6", Toast.LENGTH_SHORT).show();
                }
                else if(!stringCpassword.equals(stringPass)){
                    Toast.makeText((ExpertReg.this), "Password unmatched!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    JSONObject res = new JSONObject();
                    try {
                        res.put("name",stringName);
                        res.put("email",stringEmail);
                        res.put("phoneNumber",stringMobile);
                        res.put("password",stringPass);
                        res.put("currentAffiliation",stringAffiliation);
                        res.put("designation",stringDesignation);
                        res.put("city",stringCity);
                        res.put("country",stringCountry);
                        res.put("license",stringLicence);
                        res.put("hDegree",stringHDegree);
                        res.put("institute",stringInstitute);
                        res.put("field",stringfield);
                        HttpPostRequest httpPostRequest = new HttpPostRequest();
                        String verdict = httpPostRequest.execute(url,res.toString()).get();
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
    }


    public class HttpPostRequest extends AsyncTask<String,Void,String  > {
        ProgressDialog progressDialog;
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(ExpertReg.this, "ProgressDialog", "Wait");

        }
        protected String  doInBackground(String... strings) {
            HttpURLConnection urlConnection;
            String url=strings[0];
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
                result = sb.toString();
                Log.e("Res2",data);
                Log.e("Response2",sb.toString());

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
            /*Intent intent = new Intent(GeneralReg.this,LoginActivity.class);
            startActivity(intent);*/
            super.onPostExecute(s);
        }
    }
    private boolean validateEmail(String data){
        Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher emailMatcher = emailPattern.matcher(data);
        return emailMatcher.matches();
    }

    public static final boolean isValidPhoneNumber(String data) {
        return data.length()==11 && data.charAt(0)=='0';
    }
}

