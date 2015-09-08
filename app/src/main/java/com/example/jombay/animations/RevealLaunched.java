package com.example.jombay.animations;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;
import java.util.Map;
/**
 * Created by jombay on 4/9/15.
 */
public class RevealLaunched extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launched);
        setEnterSharedElementCallback(new SharedElementCallback() {
            View mSnapshot;

            @Override
            public void onSharedElementStart(List<String> sharedElementNames,
                                             List<View> sharedElements,
                                             List<View> sharedElementSnapshots) {
                addSnapshot(sharedElementNames, sharedElements, sharedElementSnapshots, false);
                if (mSnapshot != null) {
                    mSnapshot.setVisibility(View.VISIBLE);
                }
                findViewById(R.id.planter).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames,
                                           List<View> sharedElements,
                                           List<View> sharedElementSnapshots) {
                addSnapshot(sharedElementNames, sharedElements, sharedElementSnapshots,
                        true);
                if (mSnapshot != null) {
                    mSnapshot.setVisibility(View.INVISIBLE);
                }
                findViewById(R.id.planter).setVisibility(View.VISIBLE);
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                findViewById(R.id.planter).setVisibility(View.INVISIBLE);
            }

            private void addSnapshot(List<String> sharedElementNames,
                                     List<View> sharedElements,
                                     List<View> sharedElementSnapshots,
                                     boolean relayoutContainer) {
                if (mSnapshot == null) {
                    for (int i = 0; i < sharedElementNames.size(); i++) {
                        if ("hello".equals(sharedElementNames.get(i))) {
                            FrameLayout element = (FrameLayout) sharedElements.get(i);
                            mSnapshot = sharedElementSnapshots.get(i);
                            int width = mSnapshot.getWidth();
                            int height = mSnapshot.getHeight();
                            FrameLayout.LayoutParams layoutParams =
                                    new FrameLayout.LayoutParams(width, height);
                            layoutParams.gravity = Gravity.CENTER;
                            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
                            int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
                            mSnapshot.measure(widthSpec, heightSpec);
                            mSnapshot.layout(0, 0, width, height);
                            mSnapshot.setTransitionName("snapshot");


                            if (relayoutContainer) {
                                ViewGroup container = (ViewGroup) findViewById(R.id.container);
                                int left = (container.getWidth() - width) / 2;
                                int top = (container.getHeight() - height) / 2;
                                element.measure(widthSpec, heightSpec);
                                element.layout(left, top, left + width, top + height);

                            }
                            element.addView(mSnapshot, layoutParams);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
        findViewById(R.id.planter).setVisibility(View.VISIBLE);
    }
}
