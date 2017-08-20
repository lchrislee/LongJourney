package com.lchrislee.longjourney;

import android.app.Application;
import android.os.Build;

import com.lchrislee.longjourney.utility.BattleNotification;

public class LongJourneyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            BattleNotification.instance().createNotificationChannel(getApplicationContext());
        }
    }
}
