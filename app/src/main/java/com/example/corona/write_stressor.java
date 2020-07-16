package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class write_stressor extends AppCompatActivity {

     TextView s1,s2,s3,s4,s5;
     String s_1,s_2,s_3,s_4,s_5;
     Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_stressor);
        s1 = findViewById(R.id.s1_id);
        s2 = findViewById(R.id.s2_id);
        s3 = findViewById(R.id.s3_id);
        s4 = findViewById(R.id.s4_id);
        s5 = findViewById(R.id.s5_id);
        button = findViewById(R.id.s_done);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_1 = s1.getText().toString();
                s_2 = s2.getText().toString();
                s_3 = s3.getText().toString();
                s_4 = s4.getText().toString();
                s_5 = s5.getText().toString();

                Intent intent = new Intent(write_stressor.this,Diary.class);
                //kill all the previous activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });

    }
}