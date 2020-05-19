package com.example.corona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;


public class update_profile extends AppCompatActivity {
    public EditText email, pass, age;
    public TextView gender, maritalStatus;
    public String Email, Age, string_gender, string_maritalStatus;
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
        pass = findViewById(R.id.password);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        maritalStatus = findViewById(R.id.maritualStatus);

        //shared preference
        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String eemail = pref.getString("email",null);
        email.setText(eemail);
        email.setEnabled(false);




        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item1) {//this item has your app icon
            Intent intent = new Intent(update_profile.this, update_profile_page_2.class);
            Email = email.getText().toString();
            Age = age.getText().toString();
            string_gender = result;
            string_maritalStatus = result1;

            //shared preferences
            editor = Objects.requireNonNull(pref).edit();
          //  editor.putString("email", Email);
            editor.putString("age", Age);
            editor.putString("gender", string_gender);
            editor.putString("maritalStatus", string_maritalStatus);
            editor.apply();
            // intent.putExtra("Age", "22");
            startActivity(intent);

            //Change Toast Color
            Toast toast = Toast.makeText(this, "Tapped on icon", Toast.LENGTH_SHORT);
            Toast.makeText(this, "Age" + Age, Toast.LENGTH_SHORT).show();
            View view = toast.getView();
            view.setBackgroundResource(R.color.colorAccent);
            toast.show();


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
        if (id == R.id.checkbox_male) {
            if (checked) {
                result = "Male";
                Toast.makeText(getApplicationContext(), "Male checked", Toast.LENGTH_SHORT).show();
                // do something like update database
            }
        } else if (id == R.id.checkbox_female) {
            if (checked) {
                result = "female";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void onCheckboxClicked1(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        int id = view.getId();
        if (id == R.id.married) {
            if (checked) {
                result1 = "married";
                Toast.makeText(getApplicationContext(), "Married", Toast.LENGTH_SHORT).show();
                // do something like update database
            }
        } else if (id == R.id.unmarried) {
            if (checked) {
                result1 = "unmarried";
                //do something like update database
                Toast.makeText(getApplicationContext(), "unmarried", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.widow) {
            if (checked) {
                result1 = "widow";
                Toast.makeText(getApplicationContext(), "widow", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.divorced) {
            if (checked) {
                result1 = "divorced";
                Toast.makeText(getApplicationContext(), "divorced", Toast.LENGTH_SHORT).show();
            }
        }


    }
}



