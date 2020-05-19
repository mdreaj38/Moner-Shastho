package com.example.corona;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.Chart;

import java.util.Objects;

public class MainScreenActivity extends AppCompatActivity {


    GridView gridView;
    private ImageView imageView;

    String[] Options = {"Lock Down", "Remain Healthy", "Track Record", "Resources", "Log Out", "Profile"};
    int[] OptionImage = {R.drawable.lockdown, R.drawable.active, R.drawable.wellbeing, R.drawable.books, R.drawable.logout, R.drawable.man};
    int cnt = 0;
    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        //finding listview
        gridView = findViewById(R.id.gridview);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(MainScreenActivity.this, LockDown.class);
                    startActivity(intent);
                } else if (i == 2) {
                    Intent intent = new Intent(MainScreenActivity.this, chart.class);
                    startActivity(intent);
                } else if (i == 3) {
                    Intent intent = new Intent(MainScreenActivity.this, resource.class);
                    startActivity(intent);

                } else if (i == 5) {
                    Intent intent = new Intent(MainScreenActivity.this, profile.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (R.id.item1 == item.getItemId()) {
            MyAlertDialog();
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void MyAlertDialog() {
        final Dialog myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.activity_demo_dialog);
        myDialog.setTitle("App Demo");
        imageView = findViewById(R.id.imageView);

        Button skip = findViewById(R.id.skipp);
        Button next = findViewById(R.id.nextt);

        assert skip != null;


        //skip.setEnabled(true);
        //next.setEnabled(true);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cnt > 5) myDialog.cancel();
                imageView.setImageResource(OptionImage[cnt]);
                cnt++;
            }
        });
        myDialog.show();
    }
*/
}

