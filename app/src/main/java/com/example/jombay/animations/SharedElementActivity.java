package com.example.jombay.animations;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by jombay on 4/9/15.
 */
public class SharedElementActivity extends AppCompatActivity {
    private ViewGroup sceneRoot;
    private ImageView squareBlue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_element);
        setupWindowAnimations();
        setupLayout();
    }
    private void setupWindowAnimations() {
        Explode explode = new Explode();
        explode.setDuration(2000);
        getWindow().setExitTransition(explode);
        Fade fade = new Fade();
        getWindow().setReenterTransition(fade);
    }
    private void setupLayout() {
        sceneRoot = (LinearLayout) findViewById(R.id.scene_root);

        squareBlue = (ImageView)findViewById(R.id.square_image);
        squareBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SharedElementActivity.this, SharedElementActivity2.class);
                ImageView sharedView = squareBlue;
                String transitionName = getString(R.string.square_blue_name);
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(SharedElementActivity.this, squareBlue, transitionName);
                startActivity(i, transitionActivityOptions.toBundle());
            }
        });


    }
    private void setViewWidth(View view, int x) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = x;
        view.setLayoutParams(params);
    }
}
