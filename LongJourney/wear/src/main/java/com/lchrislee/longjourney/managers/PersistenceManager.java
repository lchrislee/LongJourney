package com.lchrislee.longjourney.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.model.creatures.Player;

public class PersistenceManager extends LongJourneyManagerBase {

    private static PersistenceManager instance;

    private Player player;

    private PersistenceManager()
    {

    }

    public static @NonNull PersistenceManager get()
    {
        if (instance == null)
        {
            instance = new PersistenceManager();
        }
        return instance;
    }

    public @NonNull
    Player getPlayer(Context context)
    {
        if (player == null)
        {
            player = new Player();
        }
        return player;
    }
}
