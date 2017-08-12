package com.lchrislee.longjourney.utility.managers;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.Town;
import com.lchrislee.longjourney.model.creatures.Player;

public class DataManager extends LongJourneyManagerBase {

    @IntDef({TOWN, TRAVEL, BATTLE_MID, BATTLE_REWARD, BATTLE_LOST, REST, SNEAK, RUN})
    public @interface PlayerLocation {}
    public static final int TOWN = 1;
    public static final int TRAVEL = 2;
    public static final int BATTLE_MID = 3;
    public static final int BATTLE_REWARD = 4;
    public static final int BATTLE_LOST = 5;
    public static final int REST = 6;
    public static final int SNEAK = 7;
    public static final int RUN = 8;

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
            Player storedPlayer = PersistenceManager.loadPlayer(context);
            if (storedPlayer != null)
            {
                player = storedPlayer;
            }
            else
            {
                player = new Player();
                PersistenceManager.savePlayer(context, player);
            }
        }
        return player;
    }

    private void updatePlayer(@NonNull Context context)
    {
        PersistenceManager.savePlayer(context, player);
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
        PersistenceManager.saveTown(context, town);
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
        PersistenceManager.saveTown(context, town);
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
        PersistenceManager.saveTown(context, town);
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
            Town storedTown = PersistenceManager.loadTown(context);
            if (storedTown != null) {
                town = storedTown;
            }
            else
            {
                town = generateRandomTown(context);
                PersistenceManager.saveTown(context, town);
            }
        }
        return town;
    }

    public void increaseDistanceWalked(@NonNull Context context, int amount)
    {
        PersistenceManager.increaseDistanceWalked(context, amount);
    }

    public int loadDistanceToTown(@NonNull Context context)
    {
        return PersistenceManager.loadDistanceToTown(context);
    }

    public int loadTotalDistanceTraveled(@NonNull Context context)
    {
        return PersistenceManager.loadTotalDistanceTraveled(context);
    }

    public void leaveTown(@NonNull Context context)
    {
        town = null;
        PersistenceManager.leaveTown(context);
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

    public @PlayerLocation int loadLocation(@NonNull Context context)
    {
        return PersistenceManager.loadCurrentLocation(context);
    }

    public void changeLocation(@NonNull Context context,
                               @PlayerLocation int newLocation)
    {
        PersistenceManager.saveCurrentLocation(context, newLocation);
    }
}
