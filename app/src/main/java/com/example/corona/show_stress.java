package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class show_stress extends AppCompatActivity {
    EditText s1,s2,s3,s4;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stress);
        String curr = getIntent().getStringExtra("stress_body");
        String [] diary = curr.split("#");

        s1 = findViewById(R.id.ss1_id);
        s2 = findViewById(R.id.ss2_id);
        s3 = findViewById(R.id.ss3_id);
        s4 = findViewById(R.id.ss4_id);
        s1.setText(diary[0]);;
        s2.setText(diary[1]);
        s3.setText(diary[2]);
        s4.setText(diary[3]);
        button = findViewById(R.id.ss_done);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}