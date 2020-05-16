package com.example.corona;

import android.content.Intent;
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
    public String Email, Age;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        /*Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);*/
        email = findViewById(R.id.update_email);
        pass = findViewById(R.id.password);
        age = findViewById(R.id.age);


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
            setAge(Age);
            intent.putExtra("Age", "22");
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
        switch (view.getId()) {
            case R.id.checkbox_male:
                if (checked)
                    Toast.makeText(getApplicationContext(), "Male checked", Toast.LENGTH_SHORT).show();
                    // do something like update database
                else
                    //remove it
                    break;
            case R.id.checkbox_female:
                if (checked)
                    //o something like update database
                    Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
                else
                    // something
                    break;
                //
        }
    }

    public void setAge(String s) {
        this.Age = s;
    }

    public String getAge() {
        return this.Age;
    }
}



