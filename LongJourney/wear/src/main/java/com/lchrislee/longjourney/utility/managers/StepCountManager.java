package com.lchrislee.longjourney.utility.managers;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.activities.MasterActivity;

public class StepCountManager extends LongJourneyBaseManager
    implements SensorEventListener
{
    public interface StepReceived
    {
        void OnMonsterFind();
        void OnStepReceived();
    }

    private static final String TAG = "StepCounterManager";

    private static final int MAX_LATENCY_MS = 500;
    private static final boolean DOES_WAKEUP = false;

    private final SensorManager stepSensorManager;

    private final StepReceived stepReceivedListener;

    private final Context context;
    private boolean isNotificationTriggered;

    public StepCountManager(@NonNull Context context, @Nullable StepReceived listener) {
        this.context = context;
        stepSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        stepReceivedListener = listener;
        isNotificationTriggered = false;
    }

    public void start() {
        isNotificationTriggered = false;
        Sensor sensor = stepSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR, DOES_WAKEUP);
        boolean doesSense = stepSensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_GAME,
            MAX_LATENCY_MS
        );

        if (!doesSense)
        {
            Toast.makeText(context, R.string.fragment_travel_step_fail, Toast.LENGTH_SHORT).show();
        }
    }

    public void stop() {
        stepSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (isNotificationTriggered)
        {
            return;
        }

        DataManager dm = DataManager.get();
        int remaining = dm.increaseDistanceWalked(context, (int) sensorEvent.values[0]);

        if (remaining == 0)
        {
            stop();
        }
        else
        {
            int totalDistance = DataManager.get().loadTotalTownDistance(context);
            double chance = ((double) remaining) / totalDistance;
            double roll = Math.random();
            Log.d(TAG, "chance of encounter: " + chance + ", roll: " + roll);

            if (roll < chance)
            {
                isNotificationTriggered = true;
            }
        }

        if (isNotificationTriggered)
        {
            DataManager.get().changeLocation(context, DataManager.ENGAGE);
            if (stepReceivedListener != null)
            {
                stepReceivedListener.OnStepReceived();
            }
            else
            {
                LJNotifactionManager.get().triggerBattleNotification(context);
                Intent clearApp = new Intent(context, MasterActivity.class);
                clearApp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(clearApp);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
