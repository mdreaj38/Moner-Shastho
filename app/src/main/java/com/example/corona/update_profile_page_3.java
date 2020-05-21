package com.example.corona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class update_profile_page_3 extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    EditText complaints, stressful_life, personality_pattern, relationship_prb, child_abuse;
    String stringComplaints, stringStressfulLife, stringPersonalityPattern, stringRelationshipPrb, stringChildAbuse;
    String yes_relationprb, yes_childhood_abuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_page_3);
        /*Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        *///shared preference


        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        complaints = findViewById(R.id.complaints);
        stressful_life = findViewById(R.id.stressful_life);
        personality_pattern = findViewById(R.id.personality_pattern);
        relationship_prb = findViewById(R.id.relation_prb_details);
        child_abuse = findViewById(R.id.abuse_details);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item1) {   //this item has your app icon
            Intent intent = new Intent(update_profile_page_3.this, update_profile_page_4.class);


            stringComplaints = complaints.getText().toString();
            stringStressfulLife = stressful_life.getText().toString();
            stringPersonalityPattern = personality_pattern.getText().toString();

            editor = Objects.requireNonNull(pref).edit();
            editor.putString("complaints", stringComplaints);
            editor.putString("stressfulLife", stringStressfulLife);
            editor.putString("personalityPattern", stringPersonalityPattern);
            editor.putString("relationshipPrb", stringRelationshipPrb);
            //  if (stringRelationshipPrb.equals("yes")) {
            yes_relationprb = relationship_prb.getText().toString();
            editor.putString("relationPrbDetails", yes_relationprb);

            // }
            editor.putString("childAbuse", stringChildAbuse);
            // if (stringChildAbuse.equals("yes")) {
            yes_childhood_abuse = child_abuse.getText().toString();
            editor.putString("childAbuseDetails", yes_childhood_abuse);
            //}
            editor.apply();


            startActivity(intent);
            Toast.makeText(this, "Tapped on icon", Toast.LENGTH_SHORT).show();
            return true;
        }
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
        if (id == R.id.no_relation_prb) {
            if (checked) {
                stringRelationshipPrb = "no";
                Toast.makeText(getApplicationContext(), "Male checked", Toast.LENGTH_SHORT).show();
                // do something like update database
            }
        } else if (id == R.id.yes_relation_prb) {
            if (checked) {
                stringRelationshipPrb = "yes";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.no_abuse) {
            if (checked) {
                stringChildAbuse = "no";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.yes_abuse) {
            if (checked) {
                stringChildAbuse = "yes";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
