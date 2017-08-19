package com.lchrislee.longjourney.utility.receivers;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.lchrislee.longjourney.utility.services.StepCountJobService;

public class StepCountBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "STEP_COUNT_BR";

    private static final int MAX_SENSOR_LATENCY = 1000;
    private static final int MIN_SENSOR_DELAY = 500;
    private static final int JOB_NUMBER_STEP_COUNT = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        JobScheduler scheduler = (JobScheduler)
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // Enable the job to monitor steps.
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_OFF))
        {
            ComponentName serviceComponent = new ComponentName(context, StepCountJobService.class);
            JobInfo.Builder builder = new JobInfo.Builder(JOB_NUMBER_STEP_COUNT, serviceComponent);
            builder.setMinimumLatency(MIN_SENSOR_DELAY);
            builder.setOverrideDeadline(MAX_SENSOR_LATENCY);
            JobInfo stepJobInfo = builder.build();

            scheduler.schedule(stepJobInfo);
        }
        else // Disable job to monitor steps.
        {
            scheduler.cancel(JOB_NUMBER_STEP_COUNT);
        }
    }
}
