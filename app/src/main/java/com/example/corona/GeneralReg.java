package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GeneralReg extends AppCompatActivity {

    private Button joinus;
    private ImageView backtologin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        joinus = findViewById(R.id.joinus);
        backtologin = findViewById(R.id.backtologin);
        Handler handler = new Handler();

        joinus.setOnClickListener(handler);
        backtologin.setOnClickListener(handler);

    }
    class Handler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.joinus || v.getId()==R.id.backtologin){
                Intent go = new Intent(GeneralReg.this,LoginActivity.class);
                startActivity(go);
                finish();
            }
        }
    }
}
