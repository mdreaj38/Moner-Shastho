package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.Objects;

public class LockDown extends AppCompatActivity {

    GridView gridView;
    private ImageView imageView;

    String[] Options = {"Manage Stress", "Keep Connected", "Relaxation", "Mindfulness"};
    int[] OptionImage = {R.drawable.stress_mng, R.drawable.connect, R.drawable.relax, R.drawable.mind};
    int cnt = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_down);
        gridView = findViewById(R.id.gridview);
        imageView = findViewById(R.id.sleep);
        Handler handle = new Handler();
        imageView.setOnClickListener(handle);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter((ListAdapter) customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(LockDown.this, qus_ans_slider.class);
                    startActivity(intent);
                    Toast.makeText((LockDown.this), "Zero", Toast.LENGTH_SHORT).show();
                } else if (i == 1) {
                    Intent intent = new Intent(LockDown.this, ques_ans_takeInput.class);
                    startActivity(intent);
                    Toast.makeText((LockDown.this), "One", Toast.LENGTH_SHORT).show();
                } else if (i == 2) {
                    Intent intent = new Intent(LockDown.this, ques_ans_select_box.class);
                    startActivity(intent);
                    Toast.makeText((LockDown.this), "Two", Toast.LENGTH_SHORT).show();
                } else if (i == 3) {
                    Toast.makeText((LockDown.this), "Three", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

    class Handler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.sleep) {
                Toast.makeText((LockDown.this), "SLEEEPPPPPPP", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
