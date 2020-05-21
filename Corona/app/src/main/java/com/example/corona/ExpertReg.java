package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;

import org.bson.Document;

public class ExpertReg extends AppCompatActivity {
    Button joinUs;
    EditText name, email, password, affiliation, country, city, designation, licence, hiDegree, institute;
    String stringName, stringEmail, stringPass, stringAffiliation, stringCity, stringDesignation, stringLicence, stringHDegree, stringInstitute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expertreg);
        joinUs = findViewById(R.id.ex_joinus);
        name = findViewById(R.id.ex_name);
        email = findViewById(R.id.ex_email);
        password = findViewById(R.id.ex_pass);
        affiliation = findViewById(R.id.affiliation);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        designation = findViewById(R.id.designation);
        licence = findViewById(R.id.licence);
        hiDegree = findViewById(R.id.hdegree);
        institute = findViewById(R.id.institute);
        joinUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringName = name.getText().toString();
                stringEmail = email.getText().toString();
                stringPass = password.getText().toString();
                stringAffiliation = affiliation.getText().toString();
                stringCity = city.getText().toString();
                stringDesignation = designation.getText().toString();
                stringLicence = licence.getText().toString();
                stringHDegree = hiDegree.getText().toString();
                stringInstitute = institute.getText().toString();
                Stitch.initializeDefaultAppClient("coronaapp-yvebc");
                StitchAppClient stitchAppClient = Stitch.getDefaultAppClient();
                RemoteMongoClient mongoClient = stitchAppClient.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
                RemoteMongoCollection<Document> expertUsersCollection = mongoClient.getDatabase("Corona").getCollection("expertUsers");
                Document newItem = new Document()
                        .append("name", stringName)
                        .append("email", stringEmail)
                        .append("password", stringPass)
                        .append("currentAffiliation", stringAffiliation)
                        .append("designation", stringDesignation)
                        .append("license", stringLicence)
                        .append("hDegree", stringHDegree)
                        .append("institute", stringInstitute);
                Task<RemoteInsertOneResult> insertTask = expertUsersCollection.insertOne(newItem);
                insertTask.addOnCompleteListener(new OnCompleteListener<RemoteInsertOneResult>() {
                    @Override
                    public void onComplete(@NonNull Task<RemoteInsertOneResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("app", String.format("successfully inserted item with id %s",
                                    task.getResult().getInsertedId()));


                        } else {
                            Log.e("app", "failed to insert document with: ", task.getException());
                        }
                    }
                });
            }
        });
    }
}

