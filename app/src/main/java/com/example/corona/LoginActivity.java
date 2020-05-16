package com.example.corona;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mongodb.stitch.android.core.StitchAppClient;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    String[] user_type;
    /*ProgressBar pgsBar;*/
    StitchAppClient stitchClient = null;
    ProgressDialog pgsdialog;
    TextView forgetPass;
    public String email1, pass1;

    public LoginActivity() {
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        pgsdialog = new ProgressDialog(LoginActivity.this);
        pgsdialog.setTitle("Please Wait");
        pgsdialog.setMessage("Logging In..");
        // pgsBar = (ProgressBar) findViewById(R.id.pBar);


        Button signup = findViewById(R.id.signup);
        Handler handler = new Handler();
        signup.setOnClickListener(handler);
        Button logIn = findViewById(R.id.login);
        logIn.setOnClickListener(handler);

        //forget password section
        forgetPass = findViewById(R.id.forget_password);
        forgetPass.setOnClickListener(handler);

        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class Handler implements View.OnClickListener {
        EditText emailLogin = findViewById(R.id.email_login);
        EditText passLogin = findViewById(R.id.password_login);
        String string_email_login, string_email_pass;

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.signup) {
                user_type = new String[]{"General", "Expert"};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(LoginActivity.this);
                mbuilder.setTitle("Choose One");
                mbuilder.setSingleChoiceItems(user_type, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                        if (which == 0) {
                            Intent intent = new Intent(LoginActivity.this, GeneralReg.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, ExpertReg.class);
                            startActivity(intent);
                            finish();
                        }
                        dialog.dismiss();
                    }
                });
                mbuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog mdialog = mbuilder.create();
                mdialog.show();


            }
            if (v.getId() == R.id.login) {


                string_email_login = emailLogin.getText().toString();
                string_email_pass = passLogin.getText().toString();
                email1 = string_email_login;
                pass1 = string_email_pass;

                Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
                intent.putExtra("email",email1);
                startActivity(intent);
                pgsdialog.dismiss();
            }


            if (v.getId() == R.id.forget_password) {
                Intent intent = new Intent(LoginActivity.this, forget_password.class);
                startActivity(intent);
            }
        }
    }
}

