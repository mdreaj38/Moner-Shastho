package com.example.corona;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;

public class LoginActivity extends AppCompatActivity {
    String[] user_type;
    private Button signup;
    private int ind = 0;
    StitchAppClient stitchClient = null;
     protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         /*Stitch.initializeDefaultAppClient(
                 getResources().getString(R.string.my_app_id)
         );
         System.out.println(getResources().getString(R.string.my_app_id));
         this.stitchClient = Stitch.getDefaultAppClient();

         Log.d("stitch", "logging in anonymously");
         Stitch.getDefaultAppClient().getAuth().loginWithCredential(new AnonymousCredential()).addOnCompleteListener(new OnCompleteListener<StitchUser>() {
             @Override
             public void onComplete(@NonNull final Task<StitchUser> task) {
                 if (task.isSuccessful()) {
                     Log.d("stitch", "logged in anonymously");
                 } else {
                     Log.e("stitch", "failed to log in anonymously", task.getException());
                 }
             }
             });*/

        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.signup);
        Handler handler = new Handler();
        signup.setOnClickListener(handler);
    }
    class Handler implements View.OnClickListener{

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
        }
    }
}
