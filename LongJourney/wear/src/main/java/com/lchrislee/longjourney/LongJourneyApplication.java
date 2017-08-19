package com.lchrislee.longjourney;

import android.app.Application;
import android.os.Build;

import com.lchrislee.longjourney.utility.BattleNotification;
import com.lchrislee.longjourney.utility.DataPersistence;

public class LongJourneyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataPersistence.player(getApplicationContext());
        DataPersistence.town(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            BattleNotification.get().createNotificationChannel(getApplicationContext());
        }
    }
}
