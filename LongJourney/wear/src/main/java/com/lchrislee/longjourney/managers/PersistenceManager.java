package com.lchrislee.longjourney.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.Town;
import com.lchrislee.longjourney.model.creatures.Player;

public class PersistenceManager extends LongJourneyManagerBase {

    private static PersistenceManager instance;

    private Player player;
    private Town town;

    private String townNames[];
    private String townSuffixes[];

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
    Player getPlayer(@NonNull Context context)
    {
        if (player == null)
        {
            player = new Player();
        }
        return player;
    }

    public @NonNull
    Town getTown(@NonNull Context context)
    {
        if (town == null)
        {
            town = generateRandomTown(context);
        }
        return town;
    }

    private @NonNull Town generateRandomTown(@NonNull Context context)
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

    public boolean purchaseStrength()
    {
        if (town.getStrengthCost() > player.getGoldCarried())
        {
            return false;
        }
        loseGold(town.getStrengthCost());
        town.increaseStrengthCost();
        player.increaseStrength();
        return true;
    }

    public boolean purchaseDefense()
    {
        if (town.getDefenseCost() > player.getGoldCarried())
        {
            return false;
        }
        loseGold(town.getDefenseCost());
        town.increaseDefenseCost();
        player.increaseDefense();
        return true;
    }

    public boolean purchaseHealth()
    {
        if (town.getHealthCost() > player.getGoldCarried())
        {
            return false;
        }
        loseGold(town.getHealthCost());
        town.increaseHealthCost();
        player.increaseHealth();
        return true;
    }

    public void loseGold(int goldLost)
    {
        player.loseGold(goldLost);
    }
}
