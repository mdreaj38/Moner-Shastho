package com.example.corona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class profile_expert_user extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    EditText email;
    EditText username;
    EditText phoneNo;
    EditText designation;
    EditText organization,license;
    String Pass;
    String string_email, string_username, string_phoneNo, string_designation, string_organization,string_license;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_expert_user);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        email = findViewById(R.id.expert_email);
        username = findViewById(R.id.expert_username);
        phoneNo = findViewById(R.id.expert_phoneNo);
        designation = findViewById(R.id.expert_designation);
        organization = findViewById(R.id.expert_organization);
        license = findViewById(R.id.expert_license);

        //shared preference
        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String eemail = pref.getString("email", null);
        String t_name = pref.getString("name", null);
        Pass = pref.getString("password",null);
        username.setText(t_name);
        email.setText(eemail);
        email.setEnabled(false);


        pref = getSharedPreferences("MyPrefExpert", Context.MODE_PRIVATE);



    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item1) {   //this item has your app icon
            Intent intent = new Intent(profile_expert_user.this, activity_profile_expert_user_2.class);

            string_email = email.getText().toString();
            string_username = username.getText().toString();
            string_phoneNo = phoneNo.getText().toString();
            string_designation = designation.getText().toString();
            string_organization = organization.getText().toString();
            string_license = license.getText().toString();

            //insert data to db
            editor = Objects.requireNonNull(pref).edit();
            editor.putString("email", string_email);
            editor.putString("name", string_username);
            editor.putString("phoneNo", string_phoneNo);
            editor.putString("license",string_license);
            editor.putString("designation", string_designation);
            editor.putString("organization", string_organization);
            editor.putString("password",Pass);
            editor.apply();


            startActivity(intent);
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

