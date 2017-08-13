package com.lchrislee.longjourney.utility.managers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.lchrislee.longjourney.R;

public class StepCountManager extends LongJourneyManagerBase
    implements SensorEventListener
{
    public interface StepReceived
    {
        void OnStepReceived();
    }

    private static final int MAX_SENSOR_LATENCY = 500;
    private static final boolean DOES_WAKEUP = false;

    private final SensorManager stepSensorManager;

    private final StepReceived stepReceivedListener;

    private final Context context;

    public StepCountManager(@NonNull Context context, @Nullable StepReceived listener) {
        this.context = context;
        stepSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        stepReceivedListener = listener;
    }

    public void start() {
        Sensor sensor = stepSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR, DOES_WAKEUP);
        boolean success = stepSensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_GAME,
                MAX_SENSOR_LATENCY
        );

        if (!success)
        {
            Toast.makeText(context, R.string.fragment_travel_step_fail, Toast.LENGTH_SHORT).show();
        }
    }

    public void stop() {
        stepSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        DataManager dm = DataManager.get();
        int remaining;
        if (sensorEvent.values.length == 1)
        {
            remaining = dm.increaseDistanceWalked(context, (int) sensorEvent.values[0]);
        }
        else
        {
            remaining = dm.increaseDistanceWalked(context, sensorEvent.values.length);
        }

        if (remaining == 0)
        {
            stop();
        }

        if (stepReceivedListener != null) {
            stepReceivedListener.OnStepReceived();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
