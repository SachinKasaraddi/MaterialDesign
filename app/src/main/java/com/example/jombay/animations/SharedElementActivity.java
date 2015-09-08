package com.example.jombay.animations;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jombay on 4/9/15.
 */
public class SharedElementActivity extends AppCompatActivity {
    private  int ANIM_DURATION = 500;
    private ViewGroup sceneRoot;
    private ImageView squareBlue;
    private SeekBar animDuration;
    private TextView animDurationText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_element);
        setupLayout();

        animDuration.setMax(5000);
        animDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ANIM_DURATION = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putInt("shareduration", ANIM_DURATION);
                editor.apply();
                animDurationText.setText("VALUE IS" + ANIM_DURATION);
                Toast.makeText(SharedElementActivity.this," "+ANIM_DURATION,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupLayout() {
        sceneRoot = (LinearLayout) findViewById(R.id.scene_root);
        animDuration = (SeekBar) findViewById(R.id.animDuration);
        animDurationText = (TextView) findViewById(R.id.animDurationText);
        squareBlue = (ImageView)findViewById(R.id.square_image);
        squareBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Explode explode = new Explode();

                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                int savedProgress = prefs.getInt("shareduration", -1);
                if (savedProgress != -1) {
                    explode.setDuration(savedProgress);
                    Transition mFadeTransition =
                            TransitionInflater.from(SharedElementActivity.this).
                                    inflateTransition(R.transition.shared_element_enter);
                    mFadeTransition.setDuration((long)savedProgress);
                    getWindow().setExitTransition(explode);
                    Fade fade = new Fade();
                    getWindow().setReenterTransition(fade);
                } else {
                    explode.setDuration(ANIM_DURATION);
                    getWindow().setExitTransition(explode);
                    Log.d("AnimDuration", "" + ANIM_DURATION);
                    Fade fade = new Fade();
                    getWindow().setReenterTransition(fade);
                }
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
