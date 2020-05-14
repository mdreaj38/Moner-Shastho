package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;

public class forget_password extends AppCompatActivity {

    EditText forgetEmail, newPass, confirmPass;
    String forget_email, new_pass, confirm_pass;
    ProgressDialog pgsdialog;
    TextView login;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        pgsdialog = new ProgressDialog(forget_password.this);
        pgsdialog.setTitle("Please Wait");
        pgsdialog.setMessage("Updating Password..");
        forgetEmail = findViewById(R.id.forget_email);
        radioGroup  = findViewById(R.id.radio);
        Button updatePass = findViewById(R.id.update_pass);
        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*update here*/
            }
        });

    }

}
