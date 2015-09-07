package com.example.jombay.animations;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by jombay on 4/9/15.
 */
public class RevealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launching);
    }

    public void launch(View view) {
        Intent intent = new Intent(this, RevealLaunched.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view, "hello");
        startActivity(intent, options.toBundle());
    }
}
