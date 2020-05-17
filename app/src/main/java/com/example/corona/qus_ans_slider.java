package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class qus_ans_slider extends AppCompatActivity {
    public String progress_value;
    public int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qus_ans_slider);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        TextView value = findViewById(R.id.range);


        value.setText("Range: " + seekBar.getProgress() + "/" + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Toast.makeText(getApplicationContext(), progress + "", Toast.LENGTH_SHORT).show();
                value.setText("Range: " + progress + "/" + seekBar.getMax());
                progress_value = Integer.toString(progress);
                //store progress value in db
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(qus_ans_slider.this, "Last progress" + progress_value, Toast.LENGTH_SHORT).show();

            }
        });

        String[] s = new String[100];
        s[0] = "hello";
        s[1] = "hello1";
        s[2] = "hello2";
        i = 0;
        List<String> stringArrayList = new ArrayList<>(Arrays.asList(s));
        i = 0;
        Button nextQues = findViewById(R.id.next_ques);
        TextView question = findViewById(R.id.questionSlider);
        question.setText(stringArrayList.get(0));
        i = i + 1;
        int len = s.length;
        nextQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(qus_ans_slider.this, qus_ans_slider.class);
                question.setText(stringArrayList.get(i));
                seekBar.setProgress(0);
                Toast.makeText(qus_ans_slider.this, "length" + stringArrayList.get(i), Toast.LENGTH_SHORT).show();
                i += 1;
                //startActivity(intent);
            }
        });

    }


}


