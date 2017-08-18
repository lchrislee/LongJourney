package com.lchrislee.longjourney;

import android.app.Application;
import android.os.Build;

import com.lchrislee.longjourney.utility.managers.BattleNotification;
import com.lchrislee.longjourney.utility.managers.DataManager;

public class LongJourneyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.get().getPlayer(getApplicationContext());
        DataManager.get().getTown(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            BattleNotification.get().createNotificationChannel(getApplicationContext());
        }
    }
}
