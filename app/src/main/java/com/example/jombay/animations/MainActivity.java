package com.example.jombay.animations;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ListView lvDetail;
    Context context = MainActivity.this;
    ArrayList<Animation> myList = new ArrayList<Animation>();

    String[] title = new String[]{
            "Explode Animation", "Menu-to-Arrow", "Circular-reveal", "fab-transformation",
            "Reveal Activity", "Shared Element Activity" };

    private View squareOrange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lvDetail = (ListView) findViewById(R.id.listView);

        getDataInList();
        lvDetail.setAdapter(new AnimationAdapter(context, myList));

        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent explode = new Intent(MainActivity.this,ExplodeAnimation.class);
                        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                         startActivity(explode,transitionActivityOptions.toBundle());
                        break;
                    case 1:
                        Intent menutodrawer = new Intent(MainActivity.this,MenuToArrow.class);
                        startActivity(menutodrawer);
                        break;
                    case 2:
                        Intent circularReveal = new Intent(MainActivity.this,CircularReveal.class);
                        startActivity(circularReveal);
                        break;
                    case 3:
                        Intent fabtransitions = new Intent(MainActivity.this,FabTransitions.class);
                        startActivity(fabtransitions);
                        break;
                    case 4:
                        Intent revealActivity = new Intent(MainActivity.this,RevealActivity.class);
                        startActivity(revealActivity);
                        break;
                    case 5:
                        Intent sharedElement =new Intent(MainActivity.this,SharedElementActivity.class);
                        startActivity(sharedElement);


                }
            }
        });
    }
    private void setupWindowAnimations() {
        Explode explode = new Explode();
        explode.setDuration(2000);
        getWindow().setExitTransition(explode);
        Fade fade = new Fade();
        getWindow().setReenterTransition(fade);
    }

    private void getDataInList() {
        for (int i = 0; i < title.length; i++) {
            // Create a new object for each list item
            Animation am = new Animation();
            am.setTitle(title[i]);
            // Add this object into the ArrayList myList
            myList.add(am);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


