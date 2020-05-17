package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ques_ans_takeInput extends AppCompatActivity {
    public int i;
    String ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_ans_take_input);

        String[] s = new String[100];
        s[0] = "hello";
        s[1] = "hello1";
        s[2] = "hello2";
        i = 0;
        List<String> stringArrayList = new ArrayList<>(Arrays.asList(s));
        i = 0;

        Button nextQues = findViewById(R.id.next_ques);
        EditText editText = findViewById(R.id.editText);
        TextView question = findViewById(R.id.textQuestion);
        Button submit = findViewById(R.id.submit);


        question.setText(stringArrayList.get(0));
        i = i + 1;
        int len = s.length;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans = editText.getText().toString();
                if (ans.equals("")) {
                    Toast toast = Toast.makeText(ques_ans_takeInput.this, "Answer Can not be empty!", Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundResource(R.color.colorAccent);
                    toast.show();

                }


                //store ans to db
            }
        });
        nextQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans = editText.getText().toString();
                if (ans.equals("")) {
                    Toast toast = Toast.makeText(ques_ans_takeInput.this, "Answer Can not be empty!", Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundResource(R.color.red);
                    toast.show();

                } else {
                    question.setText(stringArrayList.get(i));
                    editText.setText("");
                    Toast.makeText(ques_ans_takeInput.this, "length" + stringArrayList.get(i), Toast.LENGTH_SHORT).show();
                    i += 1;
                }
                //startActivity(intent);
            }
        });

    }
}
