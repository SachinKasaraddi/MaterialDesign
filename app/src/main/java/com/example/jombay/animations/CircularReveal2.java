package com.example.jombay.animations;

/**
 * Created by jombay on 4/9/15.
 */
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class CircularReveal2 extends AppCompatActivity {
    private  int ANIM_DURATION = 500;
    private View bgViewGroup;
    SeekBar progressBar ;


    TextView progressText,currentProgresText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circular_reveal_layout2);
        progressBar = (SeekBar)findViewById(R.id.circularProgress);
        progressBar.setMax(1000);
        progressText = (TextView) findViewById(R.id.circularProgressText);
        currentProgresText = (TextView) findViewById(R.id.currentProgressText);
        setupLayout();
        setupWindowAnimations();
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                editor.putInt("duration", ANIM_DURATION);
                editor.apply();
                currentProgresText.setText(""+ANIM_DURATION);
            }
        });
    }
    private void setupLayout() {
        bgViewGroup = findViewById(R.id.backgroundViewGroup);
    }
    private void setupWindowAnimations() {
        setupEnterAnimations();
        setupExitAnimations();
    }
    private void setupEnterAnimations() {
        Transition enterTransition = getWindow().getSharedElementEnterTransition();
        enterTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {}
            @Override
            public void onTransitionEnd(Transition transition) {
                animateRevealShow(bgViewGroup);
            }
            @Override
            public void onTransitionCancel(Transition transition) {}
            @Override
            public void onTransitionPause(Transition transition) {}
            @Override
            public void onTransitionResume(Transition transition) {}
        });
    }
    private void setupExitAnimations() {
        Transition sharedElementReturnTransition = getWindow().getSharedElementReturnTransition();
        sharedElementReturnTransition.setStartDelay(ANIM_DURATION);
        Transition returnTransition = getWindow().getReturnTransition();
        returnTransition.setDuration(ANIM_DURATION);
        returnTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                animateRevealHide(bgViewGroup);
            }
            @Override
            public void onTransitionEnd(Transition transition) {}
            @Override
            public void onTransitionCancel(Transition transition) {}
            @Override
            public void onTransitionPause(Transition transition) {}
            @Override
            public void onTransitionResume(Transition transition) {}
        });
    }
    private void animateRevealShow(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int savedProgress = prefs.getInt("duration", -1);
        if (savedProgress!=-1){
            progressText.setText("Last duration = " + savedProgress);
            anim.setDuration(savedProgress);
            anim.start();
            Toast.makeText(CircularReveal2.this, " " + savedProgress, Toast.LENGTH_SHORT).show();
            progressBar.setProgress(savedProgress);
        }
        else
        {
            anim.setDuration(ANIM_DURATION);
            anim.start();
        }




    }
    private void animateRevealHide(final View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int initialRadius = viewRoot.getWidth();
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewRoot.setVisibility(View.INVISIBLE);
            }
        });

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int savedProgress = prefs.getInt("duration", -1);
        if (savedProgress!=-1){
         //   progressText.setText("Last duration = " + savedProgress);
            anim.setDuration(savedProgress);
            anim.start();
            Toast.makeText(CircularReveal2.this, " " + savedProgress, Toast.LENGTH_SHORT).show();
            progressBar.setProgress(savedProgress);
        }
        else
        {
            anim.setDuration(ANIM_DURATION);
            anim.start();
        }
    }
}
