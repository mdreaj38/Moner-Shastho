package com.example.corona;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;

import org.bson.Document;

public class LoginActivity extends AppCompatActivity {
    String[] user_type;
    StitchAppClient stitchClient = null;
     protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
         Button signup = findViewById(R.id.signup);
        Handler handler = new Handler();
        signup.setOnClickListener(handler);
         Button logIn = findViewById(R.id.login);
        logIn.setOnClickListener(handler);

    }
    class Handler implements View.OnClickListener{
        EditText emailLogin=findViewById(R.id.email_login);
        EditText passLogin=findViewById(R.id.password_login);
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.signup){
                user_type = new String[]{"General","Expert"};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(LoginActivity.this);
                mbuilder.setTitle("Choose One");
                mbuilder.setSingleChoiceItems(user_type, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                        if(which==0){
                            Intent intent = new Intent(LoginActivity.this, GeneralReg.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
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
            if(v.getId()==R.id.login)
            {


                String string_email_login=emailLogin.getText().toString();
                String string_pass_login=passLogin.getText().toString();
                Stitch.initializeDefaultAppClient("coronaapp-yvebc");
                Stitch.getDefaultAppClient().getAuth().loginWithCredential(new AnonymousCredential()).addOnCompleteListener(new OnCompleteListener<StitchUser>() {
                    @Override
                    public void onComplete(@NonNull final Task<StitchUser> task) {
                        /*if (task.isSuccessful()) {

                            Log.d("stitch", "logged in anonymously");
                        } else {
                            Log.e("stitch", "failed to log in anonymously", task.getException());
                        }*/
                    }
                });
                StitchAppClient stitchAppClient = Stitch.getDefaultAppClient();
                RemoteMongoClient mongoClient = stitchAppClient.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
                RemoteMongoCollection<Document> usersCollection = mongoClient.getDatabase("Corona").getCollection("Users");
                Document query = new Document().append("Name",
                        new Document().append("$eq",string_email_login));
                final Task <Document> findOneAndUpdateTask = usersCollection.findOne(query);
                findOneAndUpdateTask.addOnCompleteListener(new OnCompleteListener <Document> () {
                    @Override
                    public void onComplete(@NonNull Task <Document> task) {
                        if (task.getResult() == null) {
                            Toast.makeText(getApplicationContext(), "You are not Signed Up!Please Sign up first!", Toast.LENGTH_SHORT).show();
                            Log.d("app", String.format("No document matches the provided query"));
                        }
                        else if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
                            startActivity(intent);
                            Log.d("app", String.format("Successfully found document: %s",
                                    task.getResult()));
                        } else {
                            Toast.makeText(getApplicationContext(), "Login Failed!Please Try again!", Toast.LENGTH_SHORT).show();
                            Log.e("app", "Failed to findOne: ", task.getException());
                        }
                    }
                });


            }
        }
    }
}
