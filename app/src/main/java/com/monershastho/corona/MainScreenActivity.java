package com.monershastho.corona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainScreenActivity extends AppCompatActivity {


    public String email;
    GridView gridView;
    String[] Options = {"Practice", "Assessment", "Track Record", "Resources", "Connect With Expert", "Profile"};
    int[] OptionImage = {R.drawable.practice, R.drawable.assessment, R.drawable.activity, R.drawable.resources, R.drawable.connetexpert, R.drawable.profile};
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

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(MainScreenActivity.this, LockDown.class);
                    startActivity(intent);
                } else if (i == 1) {
                    Intent intent = new Intent(MainScreenActivity.this, TestList.class);
                    startActivity(intent);
                } else if (i == 2) {
                    Intent intent = new Intent(MainScreenActivity.this, chart_mental_stress.class);
                    startActivity(intent);
                } else if (i == 3) {
                    Intent intent = new Intent(MainScreenActivity.this, resource.class);
                    startActivity(intent);
                } else if (i == 4) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://trin-innovation.com/services/emergencyAppointment/"));startActivity(browserIntent);

                } else if (i == 5) {
                    String cur = "expert";
                    if(cur.equals(UserType)) {
                        Intent intent = new Intent(MainScreenActivity.this, profile_expert_user.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(MainScreenActivity.this, update_profile.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
         if(item.getItemId()==R.id.action_aboutus){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://monershastho.herokuapp.com/about"));
            startActivity(browserIntent);

        }else {
            SharedPreferences settings = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            settings.edit().clear().apply();
            Intent next = new Intent(getApplicationContext(), LoginActivity.class);

            //kill all the previous activity
            next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(next);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu2, menu);
        return true;
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

