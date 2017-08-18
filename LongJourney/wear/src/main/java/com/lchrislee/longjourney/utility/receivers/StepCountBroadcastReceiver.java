package com.lchrislee.longjourney.utility.receivers;

import android.content.Context;
import android.content.Intent;

import com.lchrislee.longjourney.utility.managers.BackgroundStepScheduler;

public class StepCountBroadcastReceiver extends BaseBroadcastReceiver {

    private static final String TAG = "STEP_COUNT_BR";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Enable the job to monitor steps.
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_OFF))
        {
            BackgroundStepScheduler.scheduleStepCountJob(context);
        }
        else // Disable job to monitor steps.
        {
            BackgroundStepScheduler.removeStepCountJob(context);
        }
    }
}
