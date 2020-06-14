package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Write_diary extends AppCompatActivity {

    private EditText situation_Edittext,emotions_EditText , reactions_Edittext,thoughts_editText, behaviour_Edittext;
    private Button done_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        situation_Edittext = (EditText) findViewById(R.id.situation_id);
        emotions_EditText = (EditText) findViewById(R.id.emotions_id);
        thoughts_editText=(EditText) findViewById(R.id.thoughts_id);
        reactions_Edittext=(EditText) findViewById(R.id.reacitons_id);
        behaviour_Edittext=(EditText) findViewById(R.id.behaviour_ID);

        done_id = (Button) findViewById(R.id.done_id);

        done_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Write_diary.this, "Done", Toast.LENGTH_SHORT).show();
            }
        });

        situation_Edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(situation_Edittext.hasFocus()){
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }

                return false;
            }
        });


        emotions_EditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(emotions_EditText.hasFocus()){
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }

                return false;
            }
        });


        thoughts_editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(thoughts_editText.hasFocus()){
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }

                return false;
            }
        });

        reactions_Edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(reactions_Edittext.hasFocus()){
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }

                return false;
            }
        });

        behaviour_Edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(behaviour_Edittext.hasFocus()){
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }

                return false;
            }
        });




    }
}
