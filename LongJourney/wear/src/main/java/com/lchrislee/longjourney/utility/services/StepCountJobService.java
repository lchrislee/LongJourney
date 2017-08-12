package com.lchrislee.longjourney.utility.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.utility.managers.DataManager;

public class StepCountJobService extends JobService
    implements SensorEventListener
{
    private SensorManager stepSensorManager;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Context context = getApplicationContext();
        Toast.makeText(context, "Registering", Toast.LENGTH_SHORT).show();
        stepSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = stepSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        boolean success = stepSensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_GAME
        );

        if (!success)
        {
            Toast.makeText(
                    context,
                    R.string.activity_travel_step_fail,
                    Toast.LENGTH_SHORT
            ).show();
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Toast.makeText(getApplicationContext(), "removing listener", Toast.LENGTH_SHORT).show();
        stepSensorManager.unregisterListener(this);
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Context context = getApplicationContext();
        DataManager dm = DataManager.get();
        if (sensorEvent.values.length == 1)
        {
            Toast.makeText(context, "step: " + sensorEvent.values[0], Toast.LENGTH_SHORT).show();
            dm.increaseDistanceWalked(context, (int) sensorEvent.values[0]);
        }
        else
        {
            Toast.makeText(context, "step: " + sensorEvent.values.length, Toast.LENGTH_SHORT).show();
            dm.increaseDistanceWalked(context, sensorEvent.values.length);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
