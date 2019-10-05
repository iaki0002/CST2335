package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    public static final String ACTIVITY_NAME = "MainActivity";


    protected void onCreate(Bundle saveInstanceState) {
            super.onCreate(saveInstanceState);
            setContentView(R.layout.activity_main);

            Button loginButton = findViewById(R.id.loginButton);
            EditText emailText = findViewById(R.id.emailTextField);
            this.prefs = getApplicationContext().getSharedPreferences("account_info", 0);
            EditText profileEmail = findViewById(R.id.emailTextField);
            profileEmail.setText(this.prefs.getString("email_address", ""));

            if(loginButton != null)
                loginButton.setOnClickListener(v -> {
                    Intent goToProfilePage = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(goToProfilePage);

                    goToProfilePage.putExtra("emailTyped", emailText.getText().toString());

                    startActivity(goToProfilePage);

                    Log.e(ACTIVITY_NAME,"In function: onCreate()");

                });
        }

        @Override
    protected void onPause() {
            super.onPause();
            EditText profileEmail = findViewById(R.id.emailTextField);
            SharedPreferences.Editor editor = this.prefs.edit();
            editor.putString("email_address", profileEmail.getText().toString());
            editor.commit();
            Log.e(ACTIVITY_NAME,"In function: onPause()");
        }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME,"In function: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME,"In function: onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME,"In function: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME,"In function: onDestroy()");
    }
}