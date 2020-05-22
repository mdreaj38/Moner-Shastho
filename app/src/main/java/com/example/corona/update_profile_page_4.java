package com.example.corona;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class update_profile_page_4 extends AppCompatActivity {
    SharedPreferences pref;
    String email, age, gender, maritalStatus, mentalIllness, stringChildAbuse, stringRelationshipPrb;
    EditText abuseDetails, treatmentDetails,password;
    String stringAbuseDetails, stringTreatmentDetails, stringDisorderDetails,pass;
    String  abuse="", disorder = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_page_4);

        abuseDetails = findViewById(R.id.abuse);
        treatmentDetails = findViewById(R.id.treatment);
        password = findViewById(R.id.passg);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        email = pref.getString("email", null);
        age = pref.getString("age", null);
        gender = pref.getString("gender", null);
        maritalStatus = pref.getString("maritalStatus", null);
        pref.getString("education", null);
        pref.getString("occupation", null);
        pref.getString("familyType", null);
        pref.getString("mentalIllness", null);
        //  if (mentalIllness.equals("Yes")) {
        pref.getString("mentalIllnessDetails", null);
        // }
        pref.getString("areaOfLiving", null);
        pref.getString("monthlyIncome", null);
        pref.getString("complaints", null);
        pref.getString("stressfulLife", null);
        pref.getString("personalityPattern", null);
        pass = pref.getString("password", null);
        stringRelationshipPrb = pref.getString("relationshipPrb", null);
        assert stringRelationshipPrb != null;
//        if (stringRelationshipPrb.equals("yes")) {
        pref.getString("relationPrbDetails", null);

        // }
        stringChildAbuse = pref.getString("childAbuse", null);
        assert stringChildAbuse != null;
//        if (stringChildAbuse.equals("yes")) {
        pref.getString("childAbuseDetails", null);
        //  }


        /*Log.d("Pref", email);
        Log.d("Pref", age);
    //    Log.d("Pref", gender);
        Log.d("Pref", maritalStatus);
*/
        /*Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button update = findViewById(R.id.updateProfileButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (abuse == "" || disorder == "") {
                    String warn = "";
                    if (abuse == "") warn = "Abuse can't be empty";
                    else if (disorder == "") warn = "Disorder can't be empty";
                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(update_profile_page_4.this);
                    builder1.setCancelable(false);
                    builder1.setTitle(warn);
                    builder1.setPositiveButton(
                            Html.fromHtml("<font color='#FF0000'>Ok</font>"),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    android.app.AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    String temp_pass = password.getText().toString();
                    if (temp_pass.equals(pass)) {

                        //update database
                        stringAbuseDetails = "N/A";
                        stringDisorderDetails = "N/A";

                        if (abuse.equals("yes"))
                            stringAbuseDetails = abuseDetails.getText().toString();
                        if (disorder.equals("yes"))
                            stringDisorderDetails = treatmentDetails.getText().toString();
                        stringTreatmentDetails = treatmentDetails.getText().toString();
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
                Toast.makeText(getApplicationContext(), "Male checked", Toast.LENGTH_SHORT).show();
                // do something like update database
            }
        } else if (id == R.id.yes_abusee) {
            if (checked) {
                one.setChecked(false);
                two.setChecked(true);

                abuse = "yes";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.no_disorderr) {
            if (checked) {
                three.setChecked(true);
                four.setChecked(false);
                disorder = "no";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.yes_disorderr) {
            if (checked) {
                three.setChecked(false);
                four.setChecked(true);

                disorder = "yes";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }

        }

    }


}
