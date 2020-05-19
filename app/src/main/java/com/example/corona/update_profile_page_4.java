package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class update_profile_page_4 extends AppCompatActivity {
    SharedPreferences pref;
    String email, age, gender, maritalStatus, mentalIllness, stringChildAbuse, stringRelationshipPrb;
    EditText abuseDetails, treatmentDetails;
    String stringAbuseDetails, stringTreatmentDetails, stringDisorderDetails, abuse, disorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_page_4);

        abuseDetails = findViewById(R.id.abuse);
        treatmentDetails = findViewById(R.id.treatment);


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
                //update database
                if (abuse.equals("yes"))
                    stringAbuseDetails = abuseDetails.getText().toString();
                if (disorder.equals("yes"))
                    stringDisorderDetails = treatmentDetails.getText().toString();
                stringTreatmentDetails = treatmentDetails.getText().toString();


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
        int id = view.getId();
        if (id == R.id.no_abuse) {
            if (checked) {
                abuse = "no";
                Toast.makeText(getApplicationContext(), "Male checked", Toast.LENGTH_SHORT).show();
                // do something like update database
            }
        } else if (id == R.id.yes_abuse) {
            if (checked) {
                abuse = "yes";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.no_disorder) {
            if (checked) {
                disorder = "no";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.yes_disorder) {
            if (checked) {
                disorder = "yes";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }

        }

    }


}
