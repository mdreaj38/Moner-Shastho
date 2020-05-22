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

            assert mentalIllness != null;
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
         CheckBox Illiterate,Ssc,Hsc,Graduate,Post_graduate,Uptofive,nuc,jo,fn,fy;
         CheckBox St,se,ho,te,un,bu,fa,ot;

         Illiterate = findViewById(R.id.illiterate);
         Ssc = findViewById(R.id.ssc);
         Hsc = findViewById(R.id.hsc);
         Graduate = findViewById(R.id.graduate);
         Post_graduate = findViewById(R.id.post_gradute);
         Uptofive = findViewById(R.id.upto_class_five);



         St = findViewById(R.id.student);
         se = findViewById(R.id.service);
         ho = findViewById(R.id.housewife);
         te = findViewById(R.id.teachers);
         un = findViewById(R.id.unemployed);
         bu = findViewById(R.id.businessman);
         fa = findViewById(R.id.farmer);
         ot = findViewById(R.id.others);

         nuc = findViewById(R.id.nuclear);
         jo = findViewById(R.id.joint);

         fn = findViewById(R.id.no_illness);
         fy = findViewById(R.id.yes_illness);

        int id = view.getId();
        if (id == R.id.illiterate) {

            if (checked) {
                Illiterate.setChecked(true);
                Ssc.setChecked(false);
                Hsc.setChecked(false);
                Graduate.setChecked(false);
                Post_graduate.setChecked(false);
                Uptofive.setChecked(false);

                education = "illiterate";
                Toast.makeText(getApplicationContext(), "Male checked", Toast.LENGTH_SHORT).show();
                // do something like update database
            }
        } else if (id == R.id.upto_class_five) {

            if (checked) {

                Illiterate.setChecked(false);
                Ssc.setChecked(false);
                Hsc.setChecked(false);
                Graduate.setChecked(false);
                Post_graduate.setChecked(false);
                Uptofive.setChecked(true);

                education = "upto_class_five";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.ssc) {
            if (checked) {

                Illiterate.setChecked(false);
                Ssc.setChecked(true);
                Hsc.setChecked(false);
                Graduate.setChecked(false);
                Post_graduate.setChecked(false);
                Uptofive.setChecked(false);

                education = "ssc";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.hsc) {
            if (checked) {
                Illiterate.setChecked(false);
                Ssc.setChecked(false);
                Hsc.setChecked(true);
                Graduate.setChecked(false);
                Post_graduate.setChecked(false);
                Uptofive.setChecked(false);

                education = "hsc";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.graduate) {
            if (checked) {
                Illiterate.setChecked(false);
                Ssc.setChecked(false);
                Hsc.setChecked(false);
                Post_graduate.setChecked(false);
                Uptofive.setChecked(false);
                Graduate.setChecked(true);

                education = "graduate";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.post_gradute) {
            if (checked) {
                Illiterate.setChecked(false);
                Ssc.setChecked(false);
                Hsc.setChecked(false);
                Graduate.setChecked(false);
                Uptofive.setChecked(false);
                Post_graduate.setChecked(true);

                education = "postGraduate";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.others) {
            if (checked) {
                St.setChecked(false);
                se.setChecked(false);
                ho.setChecked(false);
                te.setChecked(false);
                un.setChecked(false);
                bu.setChecked(false);
                fa.setChecked(false);
                ot.setChecked(true);

                occupation = "others";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.teachers) {
            if (checked) {
                St.setChecked(false);
                se.setChecked(false);
                ho.setChecked(false);
                te.setChecked(true);
                un.setChecked(false);
                bu.setChecked(false);
                fa.setChecked(false);
                ot.setChecked(false);

                occupation = "teachers";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.farmer) {
            if (checked) {
                St.setChecked(false);
                se.setChecked(false);
                ho.setChecked(false);
                te.setChecked(false);
                un.setChecked(false);
                bu.setChecked(false);
                fa.setChecked(true);
                ot.setChecked(false);

                occupation = "farmer";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.service) {
            if (checked) {
                St.setChecked(false);
                se.setChecked(true);
                ho.setChecked(false);
                te.setChecked(false);
                un.setChecked(false);
                bu.setChecked(false);
                fa.setChecked(false);
                ot.setChecked(false);

                occupation = "service";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.unemployed) {
            if (checked) {
                St.setChecked(false);
                se.setChecked(false);
                ho.setChecked(false);
                te.setChecked(false);
                un.setChecked(true);
                bu.setChecked(false);
                fa.setChecked(false);
                ot.setChecked(false);

                occupation = "unemployed";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.student) {
            if (checked) {
                St.setChecked(true);
                se.setChecked(false);
                ho.setChecked(false);
                te.setChecked(false);
                un.setChecked(false);
                bu.setChecked(false);
                fa.setChecked(false);
                ot.setChecked(false);

                occupation = "student";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.businessman) {
            if (checked) {
                St.setChecked(false);
                se.setChecked(false);
                ho.setChecked(false);
                te.setChecked(false);
                un.setChecked(false);
                bu.setChecked(true);
                fa.setChecked(false);
                ot.setChecked(false);

                occupation = "businessman";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.housewife) {
            if (checked) {
                St.setChecked(false);
                se.setChecked(false);
                ho.setChecked(true);
                te.setChecked(false);
                un.setChecked(false);
                bu.setChecked(false);
                fa.setChecked(false);
                ot.setChecked(false);

                occupation = "housewife";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nuclear) {
            if (checked) {

                nuc.setChecked(true);
                jo.setChecked(false);

                familyType = "nuclear";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.joint) {
            if (checked) {
                nuc.setChecked(false);
                jo.setChecked(true);

                familyType = "joint";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.no_illness) {
            if (checked) {

                fn.setChecked(true);
                fy.setChecked(false);

                mentalIllness = "No";
                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.yes_illness) {
            if (checked) {
                fn.setChecked(false);
                fy.setChecked(true);

                mentalIllness = "Yes";

                //do something like update database
                Toast.makeText(getApplicationContext(), "FeMale checked", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
