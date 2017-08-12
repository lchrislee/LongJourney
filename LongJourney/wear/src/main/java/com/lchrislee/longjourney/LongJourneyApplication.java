package com.lchrislee.longjourney;

import android.app.Application;

import com.lchrislee.longjourney.managers.DataManager;

public class LongJourneyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.get().getPlayer(getApplicationContext());
        DataManager.get().getTown(getApplicationContext());
    }
}
