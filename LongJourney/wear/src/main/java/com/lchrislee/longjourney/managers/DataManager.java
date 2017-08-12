package com.lchrislee.longjourney.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.Town;
import com.lchrislee.longjourney.model.creatures.Player;

public class DataManager extends LongJourneyManagerBase {

    private static DataManager instance;

    private Player player;
    private Town town;

    private String townNames[];
    private String townSuffixes[];

    private DataManager()
    {

    }

    public static @NonNull
    DataManager get()
    {
        if (instance == null)
        {
            instance = new DataManager();
        }
        return instance;
    }

    public @NonNull
    Player getPlayer(@NonNull Context context)
    {
        if (player == null)
        {
            Player storedPlayer = StorageManager.loadPlayer(context);
            if (storedPlayer != null)
            {
                player = storedPlayer;
            }
            else
            {
                player = new Player();
                StorageManager.savePlayer(context, player);
            }
        }
        return player;
    }

    private void updatePlayer(@NonNull Context context)
    {
        StorageManager.savePlayer(context, player);
    }

    public boolean purchaseStrength(@NonNull Context context)
    {
        if (town.getStrengthCost() > player.getGoldCarried())
        {
            return false;
        }
        loseGold(town.getStrengthCost());
        town.increaseStrengthCost();
        player.increaseStrength();
        updatePlayer(context);
        StorageManager.saveTown(context, town);
        return true;
    }

    public boolean purchaseDefense(@NonNull Context context)
    {
        if (town.getDefenseCost() > player.getGoldCarried())
        {
            return false;
        }
        loseGold(town.getDefenseCost());
        town.increaseDefenseCost();
        player.increaseDefense();
        updatePlayer(context);
        StorageManager.saveTown(context, town);
        return true;
    }

    public boolean purchaseHealth(@NonNull Context context)
    {
        if (town.getHealthCost() > player.getGoldCarried())
        {
            return false;
        }
        loseGold(town.getHealthCost());
        town.increaseHealthCost();
        player.increaseHealth();
        updatePlayer(context);
        StorageManager.saveTown(context, town);
        return true;
    }

    public void loseGold(int goldLost)
    {
        player.loseGold(goldLost);
    }

    public @NonNull
    Town getTown(@NonNull Context context)
    {
        if (town == null)
        {
            Town storedTown = StorageManager.loadTown(context);
            if (storedTown != null) {
                town = storedTown;
            }
            else
            {
                town = generateRandomTown(context);
                StorageManager.saveTown(context, town);
            }
        }
        return town;
    }

    public void clearTown(@NonNull Context context)
    {
        town = null;
        StorageManager.clearTown(context);
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
}
