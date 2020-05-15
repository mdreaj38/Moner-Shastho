package com.example.corona;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class forget_password extends AppCompatActivity {

    EditText forgetEmail, newPass, confirmPass;
    String forget_email, new_pass, confirm_pass;
    ProgressDialog pgsdialog;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        pgsdialog = new ProgressDialog(forget_password.this);
        pgsdialog.setTitle("Please Wait");
        pgsdialog.setMessage("Updating Password..");
        forgetEmail = findViewById(R.id.forget_email);
        newPass = findViewById(R.id.new_pass);
        confirmPass = findViewById(R.id.confirm_pass);
        Button updatePass = findViewById(R.id.update_pass);
        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forget_email = forgetEmail.getText().toString();
                new_pass = newPass.getText().toString();
                confirm_pass = confirmPass.getText().toString();
                LoginActivity log = new LoginActivity();
                // Log.d("check", new_pass + confirm_pass);
                if (!log.isEmailValid(forget_email)) {
                    Toast.makeText(getApplicationContext(), "Your Email ID is Invalid!", Toast.LENGTH_SHORT).show();
                } else if (new_pass.equals(""))
                    Toast.makeText(getApplicationContext(), "You Didn't enter any password!", Toast.LENGTH_SHORT).show();
                else if (confirm_pass.equals(""))
                    Toast.makeText(getApplicationContext(), "Type the password again to confirm!", Toast.LENGTH_SHORT).show();
                else if (!new_pass.equals(confirm_pass)) {
                    Toast.makeText(getApplicationContext(), "Both passwords do not match!Try again!", Toast.LENGTH_SHORT).show();
                } else {
                    pgsdialog.show();
                }
            }
        });

        login = findViewById(R.id.forget_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forget_password.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}

