package com.example.corona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;

import org.bson.Document;

import java.util.Objects;


public class profile extends AppCompatActivity {
    public String email, pass;
    RemoteFindIterable<Document> findIterable;
    TextView emailID, u_name;
    ImageView imageView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        emailID = findViewById(R.id.email_idd);
        u_name = findViewById(R.id.name);
        //Log.d("app", "successfully found documents" + mail);
        LoginActivity x = new LoginActivity();
        email = x.email1;
        pass = x.pass1;


        //shared preference
        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String _email = pref.getString("email", null);
        String _name = pref.getString("name", null);
        emailID.setText(_email);
        u_name.setText(_name);

        //Log.d("app", "successfully found documents" + email + pass);
        /*TextView update = findViewById(R.id.update_profile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, update_profile.class);
                startActivity(intent);
            }
        });*/

        imageView = findViewById(R.id.edit);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, update_profile.class);
                startActivity(intent);
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
