package com.example.jombay.animations;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class ExplodeAnimation  extends AppCompatActivity {

    SeekBar progressBar ;

    int progressValue=500;

    TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explode_layout);
        progressBar = (SeekBar)findViewById(R.id.explodeProgress);
        progressText = (TextView) findViewById(R.id.progressText);
        setupWindowAnimations();

        progressBar.setMax(2000);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putInt("duration", progressValue);
                editor.apply();
                Toast.makeText(ExplodeAnimation.this, " " + progressValue, Toast.LENGTH_SHORT).show();


            }
        });

    }
    private void setupWindowAnimations() {
        Explode explode = new Explode();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int savedProgress = prefs.getInt("duration", -1);
            if (savedProgress!=-1){
                explode.setDuration(savedProgress);
                progressText.setText("Last duration = " + savedProgress);
                progressBar.setProgress(savedProgress);
            }
            else
            {
                explode.setDuration(progressValue);
 //               progressBar.setProgress(progressValue);
            }


        getWindow().setEnterTransition(explode);
    }

}