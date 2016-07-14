package com.lchrislee.longjourney;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TravelActivity extends Activity {
    private static final String TAG = TravelActivity.class.getSimpleName();

    private ProgressBar xp;
    private TextView level;
    private TextView gold;
    private TextView steps;

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private StepSensorManager stepSensorListener;

    private int stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        initializeUI();
    }

    private void initializeUI(){
        final BoxInsetLayout box = (BoxInsetLayout) findViewById(R.id.travel_layout_box);
        box.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                return insets;
            }
        });

        xp = (ProgressBar) findViewById(R.id.travel_progress_xp); // TODO Animate, take a look at http://stackoverflow.com/questions/8035682/animate-progressbar-update-in-android
        xp.setProgress(0);
        xp.setSecondaryProgress(0);
        xp.setVisibility(View.GONE);
        xp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xp.setProgress(xp.getProgress() + 10);
            }
        });

        level = (TextView) findViewById(R.id.travel_text_level);
        level.setText("1");
        gold = (TextView) findViewById(R.id.travel_text_gold);
        gold.setText("0");
        steps = (TextView) findViewById(R.id.travel_text_steps);
        steps.setText("0");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) == null){
            Log.e(TAG, "No step counter!");
            Toast.makeText(getApplicationContext(), "Sorry, you can't use this app!", Toast.LENGTH_SHORT).show();
        }else{
            Log.d(TAG, "Step counter found!");
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            stepSensorListener = new StepSensorManager();
            sensorManager.registerListener(stepSensorListener, stepSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(stepSensorListener);
    }

    class StepSensorManager implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            ++stepCount;
            steps.setText(String.valueOf(stepCount));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
