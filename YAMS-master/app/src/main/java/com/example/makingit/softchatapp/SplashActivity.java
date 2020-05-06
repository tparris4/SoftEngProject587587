package com.example.makingit.softchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by makingit on 11/14/18.
 */

public class SplashActivity extends AppCompatActivity {


    /* Splash screen intent created
    this intent will display apps logo while the app is loading
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
