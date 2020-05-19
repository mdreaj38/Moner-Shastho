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
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class update_profile_page_2 extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String education, getMentalIllness, occupation, areaOfLiving, monthlyIncome, familyType, mentalIllness;
    EditText areaofLiving, monthlyincome, illness_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_page_2);

        //shared preference
        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        areaofLiving = findViewById(R.id.area_of_living);
        monthlyincome = findViewById(R.id.monthly_income);
        illness_info = findViewById(R.id.illness);


       /* Intent intent = getIntent();
        String age = intent.getStringExtra("Age");
        Toast toast = Toast.makeText(this, "Age" + age, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.color.colorAccent);
        toast.show();*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item1) {   //this item has your app icon
            Intent intent = new Intent(update_profile_page_2.this, update_profile_page_3.class);


            areaOfLiving = areaofLiving.getText().toString();
            monthlyIncome = monthlyincome.getText().toString();


            editor = Objects.requireNonNull(pref).edit();
            editor.putString("education", education);
            editor.putString("occupation", occupation);
            editor.putString("familyType", familyType);
            editor.putString("mentalIllness", mentalIllness);

            assert mentalIllness!=null;
            //if (mentalIllness.equals("Yes")) {
                getMentalIllness = illness_info.getText().toString();
                editor.putString("mentalIllnessDetails", getMentalIllness);
            //}
            editor.putString("areaOfLiving", areaOfLiving);
            editor.putString("monthlyIncome", monthlyIncome);
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
        if (id == R.id.illiterate) {
            if (checked) {
                education = "illiterate";
                Toast.makeText(getApplicationContext(), "Male checked", Toast.LENGTH_SHORT).show();
                // do something like update database
            }
        } else if (id == R.id.upto_class_five) {
            if (checked) {
                education = "upto_class_five";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.ssc) {
            if (checked) {
                education = "ssc";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.hsc) {
            if (checked) {
                education = "hsc";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.graduate) {
            if (checked) {
                education = "graduate";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.post_gradute) {
            if (checked) {
                education = "postGraduate";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.others) {
            if (checked) {
                occupation = "others";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.teachers) {
            if (checked) {
                occupation = "teachers";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.farmer) {
            if (checked) {
                occupation = "farmer";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.service) {
            if (checked) {
                occupation = "service";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.unemployed) {
            if (checked) {
                occupation = "unemployed";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.student) {
            if (checked) {
                occupation = "student";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.businessman) {
            if (checked) {
                occupation = "businessman";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.housewife) {
            if (checked) {
                occupation = "housewife";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nuclear) {
            if (checked) {
                familyType = "nuclear";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.joint) {
            if (checked) {
                familyType = "joint";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.no_illness) {
            if (checked) {
                mentalIllness = "No";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.yes_illness) {
            if (checked) {
                mentalIllness = "Yes";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
