package com.lchrislee.longjourney.utility.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lchrislee.longjourney.utility.managers.JobSchedulerManager;

public class StepCountBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Enable the job to monitor steps.
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_OFF))
        {
            Toast.makeText(context, "start counting", Toast.LENGTH_SHORT).show();
            JobSchedulerManager.scheduleStepCountJob(context);
        }
        else // Disable job to monitor steps.
        {
            Toast.makeText(context, "stop counting", Toast.LENGTH_SHORT).show();
            JobSchedulerManager.removeStepCountJob(context);
        }
    }
}
