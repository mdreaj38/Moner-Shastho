package com.example.corona;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class update_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(update_profile.this, update_profile_page_2.class);
                startActivity(intent);
            }
        });



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

}
