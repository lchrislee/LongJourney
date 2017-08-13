package com.lchrislee.longjourney.utility.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lchrislee.longjourney.utility.managers.JobSchedulerManager;

public class StepCountBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "STEP_COUNT_BR";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Enable the job to monitor steps.
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_OFF))
        {
            JobSchedulerManager.scheduleStepCountJob(context);
        }
        else // Disable job to monitor steps.
        {
            JobSchedulerManager.removeStepCountJob(context);
        }
    }
}
