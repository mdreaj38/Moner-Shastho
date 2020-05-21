package com.example.corona;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class scored_ans_based_qstn extends AppCompatActivity {
    public int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scored_ans_based_qstn);

        Button zero = findViewById(R.id.zero);
        Button one = findViewById(R.id.one);
        Button three = findViewById(R.id.three);
        Button two = findViewById(R.id.two);

        TextView question = findViewById(R.id.textQuestion);

        //bring stringlist from ques_ans_select_box
        String string = "I experienced trembling (eg, in the hands)";
        question.setText(string);

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setText(string);
                //do nothing as sum+=0
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum += 1;
                question.setText(string);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum += 2;
                question.setText(string);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum += 3;
                question.setText(string);
                Toast.makeText(scored_ans_based_qstn.this, "Successfully Regstered", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
