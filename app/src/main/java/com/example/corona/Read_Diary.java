package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Read_Diary extends AppCompatActivity {

    private Button calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read__diary);

        calendar = (Button) findViewById(R.id.calendar_ID);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Read_Diary.this,Show_Diary.class);
                startActivity(intent);

                Toast.makeText(Read_Diary.this, "Calendar clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
