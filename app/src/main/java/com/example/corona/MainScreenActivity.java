package com.example.corona;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainScreenActivity extends AppCompatActivity {


    GridView gridView;
    private ImageView imageView;

    String[] Options = {"Manage Stress", "Stay Active", "Mental Well being", "Resources", "Contact with professionals", "Profile"};
    int[] OptionImage = {R.drawable.stress, R.drawable.active, R.drawable.wellbeing, R.drawable.books, R.drawable.professionals, R.drawable.man};
    int cnt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //finding listview
        gridView = findViewById(R.id.gridview);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


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
            View view1 = getLayoutInflater().inflate(R.layout.row_data, null);
            //getting view in row_data
            TextView name = view1.findViewById(R.id.fruits);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(Options[i]);
            image.setImageResource(OptionImage[i]);
            return view1;


        }
    }

    @Override
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


        skip.setEnabled(true);
        next.setEnabled(true);


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

}

