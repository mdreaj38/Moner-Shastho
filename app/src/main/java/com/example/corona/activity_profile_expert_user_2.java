package com.example.corona;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class activity_profile_expert_user_2 extends AppCompatActivity {
    SharedPreferences pref;
    String email, password, phoneNo, designation, organization,user_name;
    EditText institute, license, hdegree, field, country, city,Epassword;
    Button update;
    String string_institute, string_license, string_hdegree, string_field, string_country, string_city,string_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_expert_user_2);

        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //fetch data from db
        pref = getApplicationContext().getSharedPreferences("MyPrefExpert", 0); // 0 - for private mode
        email = pref.getString("email", null);
        user_name = pref.getString("name",null);
        phoneNo = pref.getString("phoneNo", null);
        designation = pref.getString("designation", null);
        organization = pref.getString("organization", null);
        password = pref.getString("password",null);
        string_license = pref.getString("license",null);
        Log.e("kop","ok1"+password);

        institute = findViewById(R.id.expert_institute);
        license = findViewById(R.id.expert_license);
        hdegree = findViewById(R.id.expert_hDegree);
        field = findViewById(R.id.expert_field);
        country = findViewById(R.id.expert_country);
        city = findViewById(R.id.expert_city);
        Epassword = findViewById(R.id.expert_password);
        Log.e("kop","ok2"+password);


        update = findViewById(R.id.expertUpdateProfileButton);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string_institute = institute.getText().toString();
                 string_field = field.getText().toString();
                string_hdegree = hdegree.getText().toString();
                string_country = country.getText().toString();
                string_city = city.getText().toString();
                String temp = Epassword.getText().toString();
                Log.e("kop","ok");
                Log.e("kop","ok3"+password);

                Log.e("kop",temp+" <> "+password);
                if(temp.equals(password)){
                    //update database here
                    //1.make json object
                    //2.json->string
                    //3.class call

                }
                else{
                    android.app.AlertDialog.Builder builder1 =
                            new android.app.AlertDialog.Builder(activity_profile_expert_user_2.this);
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
        });
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         finish();
        return super.onOptionsItemSelected(item);
    }

}


