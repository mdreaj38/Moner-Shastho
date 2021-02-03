package com.monershastho.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Show_Diary extends AppCompatActivity {

     private EditText situation_Edittext,emotions_EditText , reactions_Edittext,thoughts_editText, behaviour_Edittext;
    private Button done_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__diary);


        setTitle("Your Diary");
        /*back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String curr = getIntent().getStringExtra("body");
        String [] diary = curr.split("#");

        situation_Edittext = (EditText) findViewById(R.id.ssituation_id);
        emotions_EditText = (EditText) findViewById(R.id.semotions_id);
        thoughts_editText=(EditText) findViewById(R.id.sthoughts_id);
        reactions_Edittext=(EditText) findViewById(R.id.sreacitons_id);
        behaviour_Edittext=(EditText) findViewById(R.id.sbehaviour_ID);
        done_id = (Button) findViewById(R.id.sdone_id);
        situation_Edittext.setText(diary[0]);
        emotions_EditText.setText(diary[1]);
       thoughts_editText.setText(diary[2]);
        reactions_Edittext.setText(diary[3]);
        behaviour_Edittext.setText(diary[4]);
        done_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            finish();
        return super.onOptionsItemSelected(item);
    }
}
