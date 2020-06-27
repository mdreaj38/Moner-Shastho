package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import static java.lang.Integer.parseInt;

public class showTask extends AppCompatActivity {

    SharedPreferences pref;
    TextView textView;
    public String CurStress;
    SharedPreferences.Editor editor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        setTitle("Follow This step");
        textView = findViewById(R.id.taskbody);
        String body = getIntent().getStringExtra("curbody");
        textView.setText(Html.fromHtml(body));
    }

    public void onBackPressed() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Select your stress level now");
        alert.setMessage("");

        LinearLayout linear=new LinearLayout(this);

        linear.setOrientation(LinearLayout.VERTICAL);
        TextView text=new TextView(this);
        //text.setText("Hello Android");
        text.setPadding(450, 10, 10, 10);

        SeekBar seek=new SeekBar(this);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                String stress = Integer.toString(progress);
                text.setText(Html.fromHtml("<h3><font color='#008000'><b>"+stress+"/"+seek.getMax()+"</b></font></h3>"));
                CurStress = stress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        linear.addView(seek);
        linear.addView(text);
        alert.setView(linear);

        alert.setPositiveButton("Ok",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int id)
            {
                pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                String current_stress = pref.getString("CurStress","0");
                String pre_stress  = pref.getString("PreStress","0");
                String cnt = pref.getString("count","0");
              //  int avg = Integer.parseInt(avgstress);
                int c = Integer.parseInt(cnt);
                int now_stress = (Integer.parseInt(CurStress)*100)/120;
                int p_stress  = Integer.parseInt(pre_stress);
                int diff  = now_stress-p_stress;

                now_stress = (diff+(Integer.parseInt(current_stress)))/2;

                pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                editor = Objects.requireNonNull(pref).edit();
                editor.putString("count",cnt);
                editor.putString("CurStress", Integer.toString(now_stress));
                editor.apply();
               // Toast.makeText(getApplicationContext(),Integer.toString(avg),Toast.LENGTH_LONG).show();
                finish();
            }
        });

        alert.show();
    }
}