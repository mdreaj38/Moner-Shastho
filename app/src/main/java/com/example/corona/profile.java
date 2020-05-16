package com.example.corona;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;

import org.bson.Document;


public class profile extends AppCompatActivity {
    RemoteFindIterable<Document> findIterable;
    public String email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        LoginActivity x = new LoginActivity();
        email = x.email1;
        pass = x.pass1;
        Log.d("app", "successfully found documents" + email + pass);
        TextView update = findViewById(R.id.update_profile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, update_profile.class);
                startActivity(intent);
            }
        });
    }
}
