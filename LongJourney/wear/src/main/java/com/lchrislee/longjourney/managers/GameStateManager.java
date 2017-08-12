package com.lchrislee.longjourney.managers;

import android.content.Context;
import android.support.annotation.NonNull;

public class GameStateManager extends LongJourneyManagerBase {

    private static GameStateManager instance;

    private GameStateManager()
    {

    }

    public static @NonNull GameStateManager get()
    {
        if (instance == null)
        {
            instance = new GameStateManager();
        }
        return instance;
    }
}
