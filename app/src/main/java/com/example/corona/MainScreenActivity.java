package com.example.corona;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainScreenActivity extends AppCompatActivity {


    public String email;
    GridView gridView;
    String[] Options = {"Lock Down", "Remain Healthy", "Track Record", "Resources", "Log Out", "Profile"};
    int[] OptionImage = {R.drawable.lockdown, R.drawable.active, R.drawable.wellbeing, R.drawable.books, R.drawable.logout, R.drawable.man};
    int cnt = 0;
    private ImageView imageView;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        email = getIntent().getStringExtra("email");
        //finding listview
        gridView = findViewById(R.id.gridview);

        //set username in mainactivity
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String temp = pref.getString("name", null);
        String UserType = pref.getString("usertype",null);
        Options[5] = temp;
      //  Log.e("type2",temp);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(MainScreenActivity.this, LockDown.class);
                    startActivity(intent);
                } else if (i == 1) {
                    Intent intent = new Intent(MainScreenActivity.this, ques_ans_select_box.class);
                    startActivity(intent);
                } else if (i == 2) {
                    Intent intent = new Intent(MainScreenActivity.this, chart_mental_stress.class);
                    startActivity(intent);
                } else if (i == 3) {
                    Intent intent = new Intent(MainScreenActivity.this, resource.class);
                    startActivity(intent);
                } else if (i == 4) {
                    SharedPreferences settings = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    settings.edit().clear().apply();
                    Intent next = new Intent(getApplicationContext(), LoginActivity.class);

                    //kill all the previous activity
                    next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(next);
                    finish();
                } else if (i == 5) {
                    String cur = "expert";
                    if(cur.equals(UserType)) {
                        Intent intent = new Intent(MainScreenActivity.this, profile_expert_user.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(MainScreenActivity.this, profile.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText((MainScreenActivity.this), "STOP", Toast.LENGTH_SHORT).show();
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

        @Override
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

