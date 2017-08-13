package com.lchrislee.longjourney.utility.managers;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.utility.services.StepCountJobService;

public class JobSchedulerManager extends LongJourneyManagerBase {

    private static final String TAG = "JOB_SCHEDULER_MANAGER";

    private static final int MAX_SENSOR_LATENCY = 1000;
    private static final int MIN_SENSOR_DELAY = 500;

    private static final int JOB_NUMBER_STEP_COUNT = 0;

    public static void scheduleStepCountJob(@NonNull Context context)
    {
        ComponentName serviceComponent = new ComponentName(context, StepCountJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_NUMBER_STEP_COUNT, serviceComponent);
        builder.setMinimumLatency(MIN_SENSOR_DELAY);
        builder.setOverrideDeadline(MAX_SENSOR_LATENCY);
        JobInfo stepJobInfo = builder.build();

        JobScheduler scheduler = (JobScheduler)
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(stepJobInfo);
    }

    public static void removeStepCountJob(@NonNull Context context)
    {
        JobScheduler scheduler = (JobScheduler)
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancel(JOB_NUMBER_STEP_COUNT);
    }
}
