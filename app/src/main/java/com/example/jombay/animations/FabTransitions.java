package com.example.jombay.animations;

/**
 * Created by jombay on 4/9/15.
 */
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class FabTransitions extends AppCompatActivity {
    private View mFab;
    private FrameLayout mFabContainer;
    private LinearLayout mControlsContainer;
    public final static float SCALE_FACTOR = 13f;
    public  int ANIMATION_DURATION = 300;
    public final static int MINIMUN_X_DISTANCE = 200;
    private boolean mRevealFlag;
    private long mFabSize;

    TextView animDurationText,currentAnimDuration;
    SeekBar animDuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_layout);
        mFab = findViewById(R.id.fab);
        mFabSize = getResources().getDimensionPixelSize(R.dimen.fab_size);
        mFabContainer = (FrameLayout) findViewById(R.id.fab_container);

        animDurationText = (TextView) findViewById(R.id.fabAnimDuration);

        currentAnimDuration = (TextView) findViewById(R.id.currentFabDuration);
        mControlsContainer = (LinearLayout) findViewById(R.id.media_controls_container);
        animDuration = (SeekBar) findViewById(R.id.animDuration);
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        final int savedProgress = prefs.getInt("durationOfFab", -1);
        animDuration.setMax(1000);
        if (savedProgress!=-1){
            animDuration.setProgress(savedProgress);
            animDurationText.setText("Last Duration is : " + savedProgress);
        }
        else
        {
            animDuration.setProgress(ANIMATION_DURATION);
        }

        animDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ANIMATION_DURATION = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putInt("durationOfFab", ANIMATION_DURATION);
                editor.apply();
                currentAnimDuration.setText("Current Selected is : "+ANIMATION_DURATION);
            }
        });

    }
    public void onFabPressed(View view) {
        final float startX = mFab.getX();
        AnimatorPath path = new AnimatorPath();
        path.moveTo(0, 0);
        path.curveTo(-200, 200, -400, 100, -600, 50);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        final int savedProgress = prefs.getInt("durationOfFab", -1);
        if (savedProgress!=-1){
            final ObjectAnimator anim = ObjectAnimator.ofObject(this, "fabLoc",
                    new PathEvaluator(), path.getPoints().toArray());
            anim.setInterpolator(new AccelerateInterpolator());
            anim.setDuration(savedProgress);
            anim.start();
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (Math.abs(startX - mFab.getX()) > MINIMUN_X_DISTANCE) {
                        if (!mRevealFlag) {
                            mFabContainer.setY(mFabContainer.getY() + mFabSize / 2);
                            mFab.animate()
                                    .scaleXBy(SCALE_FACTOR)
                                    .scaleYBy(SCALE_FACTOR)
                                    .setListener(mEndRevealListener)
                                    .setDuration(savedProgress);
                            mRevealFlag = true;
                        }
                    }
                }
            });
            animDuration.setProgress(savedProgress);
        }
        else
        {
            final ObjectAnimator anim = ObjectAnimator.ofObject(this, "fabLoc",
                    new PathEvaluator(), path.getPoints().toArray());
            anim.setInterpolator(new AccelerateInterpolator());
            anim.setDuration(ANIMATION_DURATION);
            anim.start();
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (Math.abs(startX - mFab.getX()) > MINIMUN_X_DISTANCE) {
                        if (!mRevealFlag) {
                            mFabContainer.setY(mFabContainer.getY() + mFabSize / 2);
                            mFab.animate()
                                    .scaleXBy(SCALE_FACTOR)
                                    .scaleYBy(SCALE_FACTOR)
                                    .setListener(mEndRevealListener)
                                    .setDuration(ANIMATION_DURATION);
                            mRevealFlag = true;
                        }
                    }
                }
            });
        }


    }
    private AnimatorListenerAdapter mEndRevealListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            mFab.setVisibility(View.INVISIBLE);
            mFabContainer.setBackgroundColor(getResources()
                    .getColor(R.color.brand_accent));
            for (int i = 0; i < mControlsContainer.getChildCount(); i++) {
                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                int savedProgress = prefs.getInt("durationOfFab", -1);
                if (savedProgress!=-1){
                    View v = mControlsContainer.getChildAt(i);
                    ViewPropertyAnimator animator = v.animate()
                            .scaleX(1).scaleY(1)
                            .setDuration(savedProgress);
                    animator.setStartDelay(i * 50);
                    animator.start();
                }
                else
                {
                    View v = mControlsContainer.getChildAt(i);
                    ViewPropertyAnimator animator = v.animate()
                            .scaleX(1).scaleY(1)
                            .setDuration(ANIMATION_DURATION);
                    animator.setStartDelay(i * 50);
                    animator.start();
                }



            }
        }
    };
    /**
     * We need this setter to translate between the information the animator
     * produces (a new "PathPoint" describing the current animated location)
     * and the information that the button requires (an xy location). The
     * setter will be called by the ObjectAnimator given the 'fabLoc'
     * property string.
     */
    public void setFabLoc(PathPoint newLoc) {
        mFab.setTranslationX(newLoc.mX);
        if (mRevealFlag)
            mFab.setTranslationY(newLoc.mY - (mFabSize / 2));
        else
            mFab.setTranslationY(newLoc.mY);
    }
}
