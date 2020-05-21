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
                }
                else if(new_pass.equals(""))
                    Toast.makeText(getApplicationContext(), "You Didn't enter any password!", Toast.LENGTH_SHORT).show();
                else if(confirm_pass.equals(""))
                    Toast.makeText(getApplicationContext(), "Type the password again to confirm!", Toast.LENGTH_SHORT).show();
               else if (!new_pass.equals(confirm_pass)) {
                    Toast.makeText(getApplicationContext(), "Both passwords do not match!Try again!", Toast.LENGTH_SHORT).show();
                } else {
                    pgsdialog.show();
                    updateDocument();
                }
            }
        });

        login=findViewById(R.id.forget_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forget_password.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    void updateDocument() {
        Stitch.initializeDefaultAppClient("coronaapp-yvebc");
        StitchAppClient stitchClient = Stitch.getDefaultAppClient();
        RemoteMongoClient mongoClient = stitchClient.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
        RemoteMongoCollection<Document> usersCollection = mongoClient.getDatabase("Corona").getCollection("Users");
        Document filterDoc = new Document().append("email", forget_email);
        Document updateDoc = new Document().append("$set",
                new Document()
                        .append("password", new_pass)

        );

        final Task<RemoteUpdateResult> updateTask = usersCollection.updateOne(filterDoc, updateDoc);
        updateTask.addOnCompleteListener(new OnCompleteListener<RemoteUpdateResult>() {
            @Override
            public void onComplete(@NonNull Task<RemoteUpdateResult> task) {
                if (task.isSuccessful()) {
                    pgsdialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Password Updated!Now Log in to Enter!", Toast.LENGTH_SHORT).show();
                    long numMatched = task.getResult().getMatchedCount();
                    long numModified = task.getResult().getModifiedCount();
                    Log.d("app", String.format("successfully matched %d and modified %d documents",
                            numMatched, numModified));
                } else {
                    pgsdialog.dismiss();
                    Toast.makeText(getApplicationContext(), "failed to update document "+task.getException(), Toast.LENGTH_SHORT).show();
                    Log.e("app", "failed to update document with: ", task.getException());
                }
            }
        });
    }
}
