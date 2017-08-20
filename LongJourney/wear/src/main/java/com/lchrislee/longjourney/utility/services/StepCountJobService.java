package com.lchrislee.longjourney.utility.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.lchrislee.longjourney.utility.StepSensor;

public class StepCountJobService extends JobService
{
    private static final String TAG = "StepCountJobService";

    private StepSensor manager;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        manager = new StepSensor(getApplicationContext(), null);
        manager.start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        manager.stop();
        return true;
    }
}
