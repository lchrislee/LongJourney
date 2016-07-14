package com.lchrislee.longjourney;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.WatchViewStub;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class TravelActivity extends Activity {

    private BoxInsetLayout box;
    private ProgressBar xp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        box = (BoxInsetLayout) findViewById(R.id.travel_layout_box);
        box.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                return insets;
            }
        });
        xp = (ProgressBar) findViewById(R.id.travel_progress_xp); // TODO Animate, take a look at http://stackoverflow.com/questions/8035682/animate-progressbar-update-in-android
        xp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xp.setProgress(xp.getProgress() + 10);
            }
        });
    }
}
