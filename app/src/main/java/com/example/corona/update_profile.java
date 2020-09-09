package com.example.corona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class update_profile extends AppCompatActivity {
    public EditText email, age,name;
    public TextView gender, maritalStatus;
    public String Email, Age, string_gender, string_maritalStatus,username;
    public String result, result1;

    SharedPreferences pref;
    Editor editor;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        /*Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);*/
        email = findViewById(R.id.update_email);
        name = findViewById(R.id.usernamegen);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        maritalStatus = findViewById(R.id.maritualStatus);

        //shared preference
        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String gemail = pref.getString("email", null);
        String gname = pref.getString("name", null);
        email.setText(gemail);
        email.setEnabled(false);

        name.setText(gname);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        else {
            Intent intent = new Intent(update_profile.this, update_profile_page_2.class);
            Email = email.getText().toString();
            Age = age.getText().toString();
            string_gender = result;
            string_maritalStatus = result1;
            username = name.getText().toString();
            //shared preferences
            editor = Objects.requireNonNull(pref).edit();
            //  editor.putString("email", Email);
            editor.putString("update_name",username);
            editor.putString("age", Age);
            editor.putString("gender", string_gender);
            editor.putString("maritalStatus", string_maritalStatus);
            editor.apply();
            // intent.putExtra("Age", "22");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        CheckBox one,two;
        one  = findViewById(R.id.checkbox_male);
        two = findViewById(R.id.checkbox_female);


        // Check which checkbox was clicked
        int id = view.getId();
        if (id == R.id.checkbox_male) {
            if (checked) {
                one.setChecked(true);
                two.setChecked(false);

                result = "Male";
                 // do something like update database
            }
        } else if (id == R.id.checkbox_female) {
            if (checked) {
                one.setChecked(false);
                two.setChecked(true);
                result = "female";
                //do something like update database
             }

        }
    }

    public void onCheckboxClicked1(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        CheckBox u,d,m,w;
        u = findViewById(R.id.unmarried);
        d = findViewById(R.id.divorced);
        m = findViewById(R.id.married);
        w = findViewById(R.id.widow);



        // Check which checkbox was clicked
        int id = view.getId();
        if (id == R.id.married) {
            if (checked) {
                u.setChecked(false);
                d.setChecked(false);
                m.setChecked(true);
                w.setChecked(false);

                result1 = "married";
                 // do something like update database
            }
        } else if (id == R.id.unmarried) {
            if (checked) {
                u.setChecked(true);
                d.setChecked(false);
                m.setChecked(false);
                w.setChecked(false);

                result1 = "unmarried";
                //do something like update database
             }
        } else if (id == R.id.widow) {
            if (checked) {
                u.setChecked(false);
                d.setChecked(false);
                m.setChecked(false);
                w.setChecked(true);

                result1 = "widow";
             }
        } else if (id == R.id.divorced) {
            if (checked) {
                u.setChecked(false);
                d.setChecked(true);
                m.setChecked(false);
                w.setChecked(false);

                result1 = "divorced";
             }
        }


    }
}



