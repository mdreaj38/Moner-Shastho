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
import android.widget.CheckBox;
import android.widget.EditText;

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

public class update_profile_page_4 extends AppCompatActivity {
    SharedPreferences pref;
    String email, u_name,age, gender, maritalStatus,education,occ, mentalIllness,mentalIllness2, stringChildAbuse, stringRelationshipPrb;
    EditText abuseDetails, treatmentDetails,password;
    String stringAbuseDetails, stringTreatmentDetails, stringDisorderDetails,pass;
    String  abuse="", disorder = "",id="";
    String family, area,income,complaints,stress,pattern;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_page_4);

        abuseDetails = findViewById(R.id.abuseg);
        treatmentDetails = findViewById(R.id.treatment);
        password = findViewById(R.id.passg);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        email = pref.getString("email", "");
        u_name = pref.getString("update_name", "");
        age = pref.getString("age", "");
        gender = pref.getString("gender", "");
        maritalStatus = pref.getString("maritalStatus", "");
        education = pref.getString("education", "");
        occ = pref.getString("occupation", "");
        family = pref.getString("familyType", "");
        mentalIllness = pref.getString("mentalIllness", "");
        //  if (mentalIllness.equals("Yes")) {
        mentalIllness2 = pref.getString("mentalIllnessDetails", "");
        mentalIllness+="."+mentalIllness2;
        // }
         area = pref.getString("areaOfLiving", "");
        income  = pref.getString("monthlyIncome", "");
        complaints = pref.getString("complaints", "");
        stress = pref.getString("stressfulLife", "");
        pattern = pref.getString("personalityPattern", "");
        pass = pref.getString("password", "");
        id = pref.getString("id","");
        stringRelationshipPrb = pref.getString("relationshipPrb", "");
        assert stringRelationshipPrb != null;
        stringRelationshipPrb += pref.getString("relationPrbDetails", "");

        stringChildAbuse = pref.getString("childAbuse", "");
        assert stringChildAbuse != null;
        stringChildAbuse+=" "+pref.getString("childAbuseDetails", "");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button update = findViewById(R.id.updateProfileButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                {
                    String temp_pass = password.getText().toString();
                    if (temp_pass.equals(pass)) {

                        //update database
                        stringAbuseDetails = "N/A";
                        stringDisorderDetails = "N/A";

                        if (abuse.equals("yes"))
                            abuse+= "."+abuseDetails.getText().toString();
                        if (disorder.equals("yes"))
                            disorder +="."+treatmentDetails.getText().toString();
                        stringTreatmentDetails = treatmentDetails.getText().toString();

                        JSONObject res = new JSONObject();
                        try {
                            res.put("name",u_name);
                            res.put("email",email);
                            res.put("age",age);
                            res.put("user_id",id);
                            String NA = "Not Specified";
                            if(maritalStatus.length()==0) maritalStatus=NA;
                            res.put("maritalStatus",maritalStatus);

                            if(gender.length()==0) gender=NA;
                            res.put("Gender",gender);

                            area = area.length()==0?NA:area;
                            res.put("livingArea",area);

                            res.put("residenceType",NA);

                            family = family.length()==0?NA:family;
                            res.put("familyType",family);

                            education = education.length()==0?NA:education;
                            res.put("education",education);

                            occ = occ.length()==0?NA:occ;
                            res.put("occupation",occ);

                            income = income.length()==0?NA:income;
                            res.put("monthlyIncome",income);

                            res.put("familyIllness",NA);

                            complaints = complaints.length()==0?NA:complaints;
                            res.put("complaint",complaints);

                            stringChildAbuse = stringChildAbuse.length()==0?NA:stringChildAbuse;
                            res.put("childhoodDeprivation",stringChildAbuse);

                            stringRelationshipPrb = stringRelationshipPrb.length()==0?NA:stringRelationshipPrb;
                            res.put("relationProblem",stringRelationshipPrb);

                            stress = stress.length()==0?NA:stress;
                            res.put("stressfullEvent",stress);

                            abuse = abuse.length()==0?NA:abuse;
                            res.put("subtanceAbuse",abuse);

                            disorder = disorder.length()==0?NA:disorder;
                            res.put("diagnosedDisorder",disorder);

                            stringTreatmentDetails = stringTreatmentDetails.length()==0?NA:stringTreatmentDetails;
                            res.put("treatmentHistory",stringTreatmentDetails);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String url = "https://monershastho.herokuapp.com/users/update-profile/android";
                        try {
                            new HttpPostRequest().execute(url,res.toString()).get();
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else {
                        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(update_profile_page_4.this);
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
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        CheckBox one,two,three,four;
        one = findViewById(R.id.no_abusee);
        two = findViewById(R.id.yes_abusee);
        three = findViewById(R.id.no_disorderr);
        four = findViewById(R.id.yes_disorderr);
        int id = view.getId();
        if (id == R.id.no_abusee) {
            if (checked) {
                one.setChecked(true);
                two.setChecked(false);

                abuse = "no";
             }
        } else if (id == R.id.yes_abusee) {
            if (checked) {
                one.setChecked(false);
                two.setChecked(true);

                abuse = "yes.";
                EditText t = findViewById(R.id.abuseg);
                abuse+= t.getText().toString();
              }
        } else if (id == R.id.no_disorderr) {
            if (checked) {
                three.setChecked(true);
                four.setChecked(false);
                disorder = "no";
              }

        } else if (id == R.id.yes_disorderr) {
            if (checked) {
                three.setChecked(false);
                four.setChecked(true);
                disorder = "yes";
              }
        }
    }
    public class HttpPostRequest extends AsyncTask<String, Void, Void> {
        String verdict, message;
        ProgressDialog progressDialog;
        int statusCode;
        StringBuilder sb = new StringBuilder();

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(update_profile_page_4.this, "Login...", "Wait");

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
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(data);
                writer.close();
                outputStream.close();

                //Read
                statusCode = urlConnection.getResponseCode();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                Log.e("Response223", Integer.toString(statusCode));
                Log.e("Response223", data);
                Log.e("Response223", sb.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(Void code) {
            progressDialog.dismiss();

            SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = Objects.requireNonNull(pref).edit();
            editor.putString("name", u_name);
            editor.apply();

            Intent intent = new Intent(update_profile_page_4.this, MainScreenActivity.class);
            startActivity(intent);
            super.onPostExecute(code);
        }
    }



}
