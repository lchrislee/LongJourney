package com.lchrislee.longjourney.utility.managers;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.Town;
import com.lchrislee.longjourney.model.creatures.Monster;
import com.lchrislee.longjourney.model.creatures.Player;

public class DataManager extends LongJourneyBaseManager {

    @IntDef({TOWN, TRAVEL, ENGAGE, BATTLE, BATTLE_REWARD, BATTLE_LOST, REST, SNEAK, RUN})
    public @interface PlayerLocation {}
    public static final int TOWN = 1;
    public static final int TRAVEL = 2;
    public static final int ENGAGE = 3;
    public static final int BATTLE = 4;
    public static final int BATTLE_REWARD = 5;
    public static final int BATTLE_LOST = 6;
    public static final int REST = 7;
    public static final int SNEAK = 8;
    public static final int RUN = 9;

    private static final String TAG = "DATA_MANAGER";

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
    Monster getMonster(@NonNull Context context)
    {
        Monster monster;
        Monster storedMonster = PersistenceManager.loadMonster(context);
        if (storedMonster != null)
        {
            monster = storedMonster;
        }
        else
        {
            monster = PersistenceManager.generateMonster(context);
        }
        return monster;
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

    public boolean purchaseStrength(@NonNull Context context)
    {
        if (town.getStrengthCost() > player.getGoldCarried())
        {
            return false;
        }
        town.increaseStrengthCost();
        player.increaseStrength();
        finishPurchase(context, town.getStrengthCost());
        return true;
    }

    public boolean purchaseDefense(@NonNull Context context)
    {
        if (town.getDefenseCost() > player.getGoldCarried())
        {
            return false;
        }
        town.increaseDefenseCost();
        player.increaseDefense();
        finishPurchase(context, town.getDefenseCost());
        return true;
    }

    public boolean purchaseHealth(@NonNull Context context)
    {
        if (town.getHealthCost() > player.getGoldCarried())
        {
            return false;
        }
        town.increaseHealthCost();
        player.increaseHealth();
        finishPurchase(context, town.getHealthCost());
        return true;
    }

    private void finishPurchase(@NonNull Context context, int goldSpent)
    {
        loseGold(goldSpent);
        PersistenceManager.savePlayer(context, player);
        PersistenceManager.saveTown(context, town);
    }

    public boolean getAvoidSuccess(@NonNull Context context)
    {
        return PersistenceManager.getAvoidSuccess(context);
    }

    public void clearAvoidSuccess(@NonNull Context context)
    {
        PersistenceManager.clearAvoidSuccess(context);
    }

    public void setBattleOutcome(@NonNull Context context, boolean isPlayerWinner)
    {
        PersistenceManager.setBattleOutcome(context, isPlayerWinner);
    }

    public boolean getBattleOutcome(@NonNull Context context)
    {
        return PersistenceManager.loadBattleOutcome(context);
    }

    public void completeBattleSideEffects(@NonNull Context context)
    {
        if (getBattleOutcome(context))
        {
            Monster monster = getMonster(context);
            player.gainGold(monster.getGoldCarried());
            player.gainExperience(monster.getCurrentExperience());
        }
        else
        {
            loseGold(getMonster(context).getGoldCarried());
        }
        PersistenceManager.savePlayer(context, player);
        PersistenceManager.clearMonster(context);
    }

    private void loseGold(int goldLost)
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

    int increaseDistanceWalked(@NonNull Context context, int amount)
    {
        return PersistenceManager.increaseDistanceWalked(context, amount);
    }

    private void decreaseDistanceWalked(@NonNull Context context, int amount)
    {
        PersistenceManager.decreaseDistanceWalked(context, amount);
    }

    int loadTotalTownDistance(@NonNull Context context)
    {
        return PersistenceManager.loadTotalTownDistance(context);
    }

    public int loseDistanceTraveled(@NonNull Context context)
    {
        int expectedDistance = loadTotalTownDistance(context);
        int distanceLost = (int) (expectedDistance * 0.2f);
        decreaseDistanceWalked(context, distanceLost);
        return distanceLost;
    }

    public int loadDistanceToTown(@NonNull Context context)
    {
        return PersistenceManager.loadDistanceToTown(context);
    }

    public int loadTotalDistanceTraveled(@NonNull Context context)
    {
        return PersistenceManager.loadTotalDistanceTraveled(context);
    }

    public void enterTown(@NonNull Context context)
    {
        PersistenceManager.enterTown(context);
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
