package com.example.corona;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;

import org.bson.Document;

public class LoginActivity extends AppCompatActivity {
    String[] user_type;
    /*ProgressBar pgsBar;*/
    StitchAppClient stitchClient = null;
    ProgressDialog pgsdialog;

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

               /* pgsBar = findViewById(R.id.pBar);
                pgsBar.setVisibility(View.VISIBLE);
*/

                string_email_login = emailLogin.getText().toString();
                string_email_pass = passLogin.getText().toString();
                if (!isEmailValid(string_email_login)) {
                    Toast.makeText(getApplicationContext(), "Your Email ID is Invalid!", Toast.LENGTH_SHORT).show();
                } else {
                    pgsdialog.show();
                    String string_pass_login = passLogin.getText().toString();
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
                    Document query = new Document().append("email",
                            new Document().append("$eq", string_email_login)).append("password", new Document().append("$eq", string_email_pass));
                    final Task<Document> findOneAndUpdateTask = usersCollection.findOne(query);
                    findOneAndUpdateTask.addOnCompleteListener(new OnCompleteListener<Document>() {
                        @Override
                        public void onComplete(@NonNull Task<Document> task) {
                            if (task.getResult() == null) {
                                check_expertsCollection();
                                pgsdialog.dismiss();
                            } else if (task.isSuccessful()) {

                                Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
                                startActivity(intent);
                                pgsdialog.dismiss();
                                Log.d("app", String.format("Successfully found document: %s",
                                        task.getResult()));
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Failed!Please Try again!", Toast.LENGTH_SHORT).show();
                                Log.e("app", "Failed to findOne: ", task.getException());
                                pgsdialog.dismiss();
                            }
                        }

                    });

                }
            }


        }

        void check_expertsCollection() {

            StitchAppClient stitchAppClient = Stitch.getDefaultAppClient();
            RemoteMongoClient mongoClient = stitchAppClient.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
            RemoteMongoCollection<Document> expertUsersCollection = mongoClient.getDatabase("Corona").getCollection("expertUsers");
            Document query = new Document().append("email",
                    new Document().append("$eq", string_email_login)).append("password", new Document().append("$eq", string_email_pass));
            final Task<Document> findOneAndUpdateTask = expertUsersCollection.findOne(query);
            findOneAndUpdateTask.addOnCompleteListener(new OnCompleteListener<Document>() {
                @Override
                public void onComplete(@NonNull Task<Document> task) {
                    if (task.getResult() == null) {
                        Toast.makeText(getApplicationContext(), "You are not Signed Up!Please Sign up first!", Toast.LENGTH_SHORT).show();
                        Log.d("app", "No document matches the provided query");
                        pgsdialog.dismiss();
                    } else if (task.isSuccessful()) {

                        Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
                        startActivity(intent);
                        pgsdialog.dismiss();
                        Log.d("app", String.format("Successfully found document: %s",
                                task.getResult()));
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed!Please Try again!", Toast.LENGTH_SHORT).show();
                        Log.e("app", "Failed to findOne: ", task.getException());
                        pgsdialog.dismiss();
                    }
                }

            });
        }
    }
}

