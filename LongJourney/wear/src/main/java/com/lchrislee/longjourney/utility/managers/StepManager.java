package com.lchrislee.longjourney.utility.managers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lchrislee.longjourney.R;

public class StepManager extends LongJourneyManagerBase {

    public interface StepReceived
    {
        void OnStepReceived();
    }

    private static final int MAX_SENSOR_LATENCY = 1000;
    private static final boolean DOES_WAKEUP = false;

    private StepSensorListener stepSensorListener;
    private SensorManager stepSensorManager;

    private StepReceived stepReceivedListener;

    public StepManager(@NonNull Context context, @NonNull StepReceived listener) {
        stepSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        stepReceivedListener = listener;
    }

    public void registerSensor(@NonNull Context context)
    {
        stepSensorListener = new StepSensorListener(context);
        Sensor sensor = stepSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR, DOES_WAKEUP);
        boolean success = stepSensorManager.registerListener(
                stepSensorListener,
                sensor,
                SensorManager.SENSOR_DELAY_GAME,
                MAX_SENSOR_LATENCY
        );

        if (!success)
        {
            Toast.makeText(
                    context,
                    R.string.fragment_travel_step_fail,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void unregisterSensor()
    {
        stepSensorManager.unregisterListener(stepSensorListener);
    }

    private class StepSensorListener implements SensorEventListener
    {
        private Context context;

        StepSensorListener(@NonNull Context context) {
            this.context = context;
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            DataManager dm = DataManager.get();
            if (sensorEvent.values.length == 1)
            {
                dm.increaseDistanceWalked(context, (int) sensorEvent.values[0]);
            }
            else
            {
                dm.increaseDistanceWalked(context, sensorEvent.values.length);
            }

            if (stepReceivedListener != null) {
                stepReceivedListener.OnStepReceived();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }
}
