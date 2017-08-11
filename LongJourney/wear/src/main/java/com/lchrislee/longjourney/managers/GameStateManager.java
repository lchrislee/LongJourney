package com.lchrislee.longjourney.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.Town;

public class GameStateManager extends LongJourneyManagerBase {

    private static GameStateManager instance;
    private String[] townNames;
    private String[] townSuffixes;


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

    public @NonNull Town generateRandomTown(@NonNull Context context)
    {
        if (townNames == null) {
            townNames = context.getResources().getStringArray(R.array.town_names);
        }
        if (townSuffixes == null)
        {
            townSuffixes = context.getResources().getStringArray(R.array.town_suffixes);
        }
        return Town.generateRandomTown(townNames, townSuffixes);
    }
}
