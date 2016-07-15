package com.lchrislee.longjourney;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;
import android.widget.Toast;

import com.lchrislee.longjourney.model.actors.Player;
import com.lchrislee.longjourney.utility.PlayerInteraction;
import com.lchrislee.longjourney.utility.SharedPreferenceConstants;

public class TravelActivity extends Activity {
    private static final String TAG = TravelActivity.class.getSimpleName();

    private TextView levelText;
    private TextView goldText;
    private TextView stepsText;

    private SensorManager sensorManager;
    private StepDetectorUpdateManager stepDetectorListener;
    private StepCounterUpdateManager stepCounterListener;

    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        player = PlayerInteraction.pullDataFromPreferences(getSharedPreferences(SharedPreferenceConstants.STEP_PREF_NAME, MODE_PRIVATE));
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

        levelText = (TextView) findViewById(R.id.travel_text_level);
        goldText = (TextView) findViewById(R.id.travel_text_gold);
        stepsText = (TextView) findViewById(R.id.travel_text_steps);

        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeStepSensors();
        updateUI();
    }

    private void initializeStepSensors(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        Sensor stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepDetector == null || stepCounter == null){
            Log.e(TAG, "Insufficient step detection!");
            Toast.makeText(getApplicationContext(), "Sorry, you can't use this app!", Toast.LENGTH_SHORT).show();
        }else {
            Log.d(TAG, "Step counter and detector found!");
            stepDetectorListener = new StepDetectorUpdateManager();
            sensorManager.registerListener(stepDetectorListener, stepDetector, SensorManager.SENSOR_DELAY_GAME);
            stepCounterListener = new StepCounterUpdateManager();
            sensorManager.registerListener(stepCounterListener, stepCounter, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    private void updateUI(){
        levelText.setText(String.valueOf(player.getLevel()));
        goldText.setText(String.valueOf(player.getGold()));
        stepsText.setText(String.valueOf(player.getStepCount()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(stepDetectorListener);
        saveDataToPreferences();
    }

    private void saveDataToPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferenceConstants.STEP_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SharedPreferenceConstants.STEP_REFERENCE, player.getStepReference());
        editor.putLong(SharedPreferenceConstants.STEP_COUNT, player.getStepCount());
        editor.apply();
    }


    class StepDetectorUpdateManager implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            player.increaseStepCountBy(1);
            stepsText.setText(String.valueOf(player.getStepCount()));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    class StepCounterUpdateManager implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            long newTotalCount = (long) event.values[0];
            long ref = player.getStepReference();
            if (newTotalCount > ref){
                player.increaseStepCountBy(newTotalCount - ref);
            } else if (newTotalCount < ref){
                player.increaseStepCountBy(newTotalCount);
            }
            sensorManager.unregisterListener(this);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
