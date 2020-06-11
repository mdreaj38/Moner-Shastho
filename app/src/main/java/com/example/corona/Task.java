package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Task extends AppCompatActivity {


    public ArrayList<String> mydata = new ArrayList<String>();


    public ArrayList<String> Id = new ArrayList<String>();
    public ArrayList<String> Title = new ArrayList<String>();
    public ArrayList<String> Body = new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        setTitle("Task");
        /*back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String check = getIntent().getStringExtra("all");
        String[] root =check.split("\\$");



        String[] Title = root[1].split("#");
        String[] Body = root[2].split("#");

        for(String i:Title){
            mydata.add(i);
        }

        ListView listView = findViewById(R.id.task_listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Task.this, R.layout.sample_view, R.id.textView, mydata);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animation animation1 = new AlphaAnimation(0.7f, 1.0f);
                animation1.setDuration(2000);
                view.startAnimation(animation1);
                // Toast.makeText(resource.this,mydata.get(position),Toast.LENGTH_SHORT).show();
                String temp = mydata.get(position);
                Toast.makeText(Task.this, temp, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Task.this, showTask.class);
                intent.putExtra("curbody",Body[position]);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.getItemId();
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}