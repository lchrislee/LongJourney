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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lchrislee.longjourney.model.actors.Player;
import com.lchrislee.longjourney.utility.SharedPreferenceConstants;

public class TravelActivity extends Activity {
    private static final String TAG = TravelActivity.class.getSimpleName();

    private ProgressBar xp;
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
        pullDataFromPreferences();
        initializeUI();
    }

    private void pullDataFromPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferenceConstants.STEP_PREF_NAME, MODE_PRIVATE);
        long stepRef = sharedPreferences.getLong(SharedPreferenceConstants.STEP_REFERENCE, 0);
        long stepCount = sharedPreferences.getLong(SharedPreferenceConstants.STEP_COUNT, 0);
        long playerGold = sharedPreferences.getLong(SharedPreferenceConstants.PLAYER_GOLD, 0);
        long playerHealth = sharedPreferences.getLong(SharedPreferenceConstants.PLAYER_HEALTH, 10);
        long playerLevel = sharedPreferences.getLong(SharedPreferenceConstants.PLAYER_LEVEL, 1);
        long playerStrength = sharedPreferences.getLong(SharedPreferenceConstants.PLAYER_STRENGTH, 1);
        long playerDefense = sharedPreferences.getLong(SharedPreferenceConstants.PLAYER_DEFENSE, 1);

        Player.Builder playerBuilder = new Player.Builder();
        playerBuilder.stepReference(stepRef);
        playerBuilder.stepCount(stepCount);
        playerBuilder.gold(playerGold);
        playerBuilder.health(playerHealth);
        playerBuilder.level(playerLevel);
        playerBuilder.strength(playerStrength);
        playerBuilder.defense(playerDefense);
        player = playerBuilder.build();
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
            player.increaseStepCount(1);
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
                player.increaseStepCount(newTotalCount - ref);
            } else if (newTotalCount < ref){
                player.increaseStepCount(newTotalCount);
            }
            sensorManager.unregisterListener(this);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
