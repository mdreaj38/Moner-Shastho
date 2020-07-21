package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class stress_list extends AppCompatActivity {


    public ArrayList<String> mydata = new ArrayList<String>();

    public ArrayList<String> situation = new ArrayList<String>();
    public ArrayList<String> stress = new ArrayList<String>();
    public ArrayList<String> response = new ArrayList<String>();
    public ArrayList<String> stress_after_response = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_list);
         /*back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String Id = pref.getString("id", "");


        mydata.add("pklp");
        situation.add("1");
        stress.add("2");
        response.add("3");
        stress_after_response.add("4");
        ListView listView = findViewById(R.id.stress_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(stress_list.this, R.layout.sample_view, R.id.textView, mydata);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animation animation1 = new AlphaAnimation(0.7f, 1.0f);
                animation1.setDuration(2000);
                view.startAnimation(animation1);
                // Toast.makeText(resource.this,mydata.get(position),Toast.LENGTH_SHORT).show();
                String temp = mydata.get(position);
                String cur = situation.get(position)+"#"+stress.get(position)+"#"+response.get(position)+
                        "#"+stress_after_response.get(position);
                Toast.makeText(stress_list.this, cur, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(stress_list.this, show_stress.class);
                intent.putExtra("stress_body",cur);
                startActivity(intent);
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}