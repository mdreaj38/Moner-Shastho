package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class profile extends AppCompatActivity {
    RemoteFindIterable<Document> findIterable;
   public String email,pass;
    private void showProfilenfo()
    {
        //Stitch.initializeDefaultAppClient("coronaapp-yvebc");
        StitchAppClient stitchAppClient = Stitch.getDefaultAppClient();
        RemoteMongoClient mongoClient = stitchAppClient.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
        RemoteMongoCollection<Document> usersCollection = mongoClient.getDatabase("Corona").getCollection("Users");
        Document query = new Document().append("email",
                new Document().append("$eq", email)).append("password", new Document().append("$eq",pass));
        findIterable=usersCollection.find(query);
        Task <List<Document>> itemsTask = findIterable.into(new ArrayList<Document>());
        itemsTask.addOnCompleteListener(new OnCompleteListener<List<Document>>() {
            @Override
            public void onComplete(@NonNull Task<List<Document>> task) {
                if (task.isSuccessful()) {
                    List<Document> items = task.getResult();
                    Log.d("app", String.format("successfully found %d documents", items.size()));
                    Log.d("app", "successfully found documents"+email+pass);
                    for (Document item: items) {
                        Log.d("app", String.format("successfully found:  %s", item.toString()));
                    }
                } else {
                    Log.e("app", "failed to find documents with: ", task.getException());
                }
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        LoginActivity x=new LoginActivity();
        email=x.email1;
        pass=x.pass1;
        Log.d("app", "successfully found documents"+email+pass);
        TextView update=findViewById(R.id.update_profile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this,update_profile.class);
                startActivity(intent);
            }
    });
}
}
