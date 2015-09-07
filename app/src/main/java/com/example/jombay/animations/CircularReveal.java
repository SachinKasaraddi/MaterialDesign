package com.example.jombay.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by jombay on 4/9/15.
 */
public class CircularReveal extends AppCompatActivity {
    private ViewGroup sceneRoot;
    private View squareOrange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circular_reveal_layout);
        setupLayout();
    }
    private void setupLayout() {
        sceneRoot = (LinearLayout) findViewById(R.id.scene_root);

        squareOrange = findViewById(R.id.square_orange);
        squareOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CircularReveal.this, CircularReveal2.class);
                View sharedView = squareOrange;
                String transitionName = getString(R.string.square_orange_name);
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(CircularReveal.this, sharedView, transitionName);
                startActivity(i, transitionActivityOptions.toBundle());
            }
        });
    }
}
