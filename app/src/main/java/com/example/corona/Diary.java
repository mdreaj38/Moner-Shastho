package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Diary extends AppCompatActivity {


    private Button read_button, write_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        read_button = (Button) findViewById(R.id.read_diary);
        write_button = (Button) findViewById(R.id.write_diary);

        read_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Diary.this, "Read button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Diary.this, "write clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
