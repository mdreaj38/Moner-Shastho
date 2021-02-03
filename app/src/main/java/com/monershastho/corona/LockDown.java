package com.monershastho.corona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class LockDown extends AppCompatActivity {

    GridView gridView;

    String[] Options = {"Manage Stress", "Relaxation", "Mindfulness","Self Care","Sleep","Specific Problem","Connected","Activity Schedule"};
    int[] OptionImage = {R.drawable.stress_mng,  R.drawable.relax, R.drawable.mind, R.drawable.self_care, R.drawable.sleep, R.drawable.problem_solving,R.drawable.connect, R.drawable.activity_schedule};
    int cnt = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_down);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Practice");

        gridView = findViewById(R.id.lgridview);
        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = Objects.requireNonNull(pref).edit();


        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter((ListAdapter) customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent next = new Intent(LockDown.this, LockdownResource.class);
                editor.putString("Tag",Options[i]);
                editor.apply();
                next.putExtra("CAT", Options[i]);
                startActivity(next);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    static class ViewHolder {
        public TextView name;
        ImageView image;
    }


    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return OptionImage.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = view;
            ViewHolder viewHolder;
            Log.d("Options", String.valueOf(i));
            if (view1 == null) {
                view1 = getLayoutInflater().inflate(R.layout.row_data, null);
                //getting view in row_data
                viewHolder = new ViewHolder();
                viewHolder.name = view1.findViewById(R.id.fruits);
                viewHolder.image = view1.findViewById(R.id.images);
                view1.setTag(viewHolder);
            }
            {
                viewHolder = (ViewHolder) view1.getTag();
                viewHolder.name.setText(Options[i]);
                viewHolder.image.setImageResource(OptionImage[i]);
            }
            return view1;
        }

    }

}
