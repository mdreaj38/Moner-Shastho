package com.example.corona;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;

import org.bson.Document;


public class GeneralReg extends AppCompatActivity {

    public String EmailString, PasswordString, NameString;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button joinus = findViewById(R.id.joinus);
        ImageView backtologin = findViewById(R.id.backtologin);
        EditText Name = findViewById(R.id.name);
        EditText Email = findViewById(R.id.email);
        EditText Password = findViewById(R.id.password);

     /*   System.out.println("hello");
        System.out.println(NameString + EmailString + PasswordString);*/
        joinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GeneralReg.this.getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                // Code here executes on main thread after user presses button
                Stitch.initializeDefaultAppClient("coronaapp-yvebc");
                Stitch.getDefaultAppClient().getAuth().loginWithCredential(new AnonymousCredential()).addOnCompleteListener(new OnCompleteListener<StitchUser>() {
                    @Override
                    public void onComplete(@NonNull final Task<StitchUser> task) {
                        if (task.isSuccessful()) {

                            Log.d("stitch", "logged in anonymously");
                        } else {
                            Log.e("stitch", "failed to log in anonymously", task.getException());
                        }
                    }
                });
                NameString = Name.getText().toString();
                EmailString = Email.getText().toString();
                PasswordString = Password.getText().toString();
                StitchAppClient stitchAppClient = Stitch.getDefaultAppClient();
                RemoteMongoClient mongoClient = stitchAppClient.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
                RemoteMongoCollection<Document> usersCollection = mongoClient.getDatabase("Corona").getCollection("Users");
                Document newItem = new Document()
                        .append("name", NameString)
                        .append("email", EmailString)
                        .append("password", PasswordString);
                Task<RemoteInsertOneResult> insertTask = usersCollection.insertOne(newItem);
                insertTask.addOnCompleteListener(new OnCompleteListener<RemoteInsertOneResult>() {
                    @Override
                    public void onComplete(@NonNull Task<RemoteInsertOneResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("app", NameString);
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





