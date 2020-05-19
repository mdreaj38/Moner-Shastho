package com.example.corona;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;

import org.bson.Document;

import java.util.Objects;


public class profile extends AppCompatActivity {
    RemoteFindIterable<Document> findIterable;
    public String email, pass;
    TextView emailID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        String mail = intent.getStringExtra("email");
        emailID = findViewById(R.id.email_idd);
        Log.d("app", "successfully found documents" + mail);
        emailID.setText(mail);
        LoginActivity x = new LoginActivity();
        email = x.email1;
        pass = x.pass1;
        //Log.d("app", "successfully found documents" + email + pass);
        TextView update = findViewById(R.id.update_profile);
        update.setOnClickListener(new View.OnClickListener() {
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
