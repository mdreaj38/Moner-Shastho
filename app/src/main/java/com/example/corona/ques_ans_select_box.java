package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ques_ans_select_box extends AppCompatActivity {
    public int i, flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_ans_select_box);



        TextView question = findViewById(R.id.textQuestion);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        RadioButton button1 = findViewById(R.id.radio_button1);
        RadioButton button2 = findViewById(R.id.radio_button2);
        RadioButton button3 = findViewById(R.id.radio_button3);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);


        //set radiobutton options
        button1.setText("Hello1");
        button2.setText("Hello2");
        button3.setText("Hello3");

        //set question
        String[] s = new String[100];
        s[0] = "hello";
        s[1] = "hello1";
        s[2] = "hello2";
        i = 0;
        List<String> stringArrayList = new ArrayList<>(Arrays.asList(s));
        i = 0;
        question.setText(stringArrayList.get(0));
        i = i + 1;
        int len = s.length;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    Toast toast = Toast.makeText(ques_ans_select_box.this, "Choose an answer!", Toast.LENGTH_SHORT);
                    View view1 = toast.getView();
                    view1.setBackgroundResource(R.color.colorAccent);
                    toast.show();
                } else {
                    question.setText(stringArrayList.get(i));
                    radioGroup.clearCheck();
                    flag = 0;
                    Toast.makeText(ques_ans_select_box.this, "length" + stringArrayList.get(i), Toast.LENGTH_SHORT).show();
                    i += 1;
                    //startActivity(intent);
                }
            }
        });

    }

    //get data from here
    public void onRadioButtonClicked(View view) {
        flag = 0;
        boolean checked = ((RadioButton) view).isChecked();
        String str = "";
        // Check which radio button was clicked
        int id = view.getId();
        if (id == R.id.radio_button1) {
            if (checked) {
                str = "1";
                flag = 1;
                Toast.makeText(ques_ans_select_box.this, "checked 1!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.radio_button2) {
            if (checked) {
                str = "2";
                flag = 1;
            }
        } else if (id == R.id.radio_button3) {
            if (checked) {
                str = "3";
                flag = 1;
            }
        }

    }
}

