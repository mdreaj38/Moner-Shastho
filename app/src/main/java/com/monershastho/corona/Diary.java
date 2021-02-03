package com.monershastho.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class Diary extends AppCompatActivity {


    private Button read_button, write_button,extra1,extra2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("My Diary");

        read_button = (Button) findViewById(R.id.read_diary);
        write_button = findViewById(R.id.write_diary);
        extra1 = findViewById(R.id.b1);
        extra2 = findViewById(R.id.b2);

        extra2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Diary.this,stress_list.class);
                startActivity(intent);
            }
        });
        extra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Diary.this,write_stressor.class);
                startActivity(intent);
            }
        });

        read_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(Diary.this,Read_Diary.class);
                startActivity(intent);
            }
        });

        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(Diary.this,Write_diary.class);
                startActivity(intent);

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
