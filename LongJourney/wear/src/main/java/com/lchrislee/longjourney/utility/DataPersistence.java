package com.lchrislee.longjourney.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.Town;
import com.lchrislee.longjourney.model.creatures.Monster;
import com.lchrislee.longjourney.model.creatures.Player;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataPersistence {

    private static final String TAG = "DataPersistence";

    private static final String SHARED_PREFERENCES = "LongJourneyMainShared";
    private static final String PREFERENCE_LOCATION = "PREFERENCE_LOCATION";
    private static final String PREFERENCE_TOWN_NAME = "PREFERENCE_TOWN_NAME";
    private static final String PREFERENCE_TOWN_SCOST = "PREFERENCE_TOWN_SCOST";
    private static final String PREFERENCE_TOWN_DCOST = "PREFERENCE_TOWN_DCOST";
    private static final String PREFERENCE_TOWN_HCOST = "PREFERENCE_TOWN_HCOST";
    private static final String PREFERENCE_TOWN_COUNT = "PREFERENCE_TOWN_COUNT";

    private static final String PREFERENCE_DISTANCE_START = "PREFERENCE_DISTANCE_START";
    private static final String PREFERENCE_DISTANCE_REMAINING = "PREFERENCE_DISTANCE_REMAINING";
    private static final String PREFERENCE_DISTANCE_TOTAL = "PREFERENCE_DISTANCE_TOTAL";

    private static final String PREFERENCE_AVOID_SUCCESS = "PREFERENCE_AVOID_SUCCESS";
    private static final String PREFERENCE_BATTLE_OUTCOME = "PREFERENCE_BATTLE_OUTCOME";

    private static final String PLAYER_FILE_NAME = "player.ljf";
    private static final String MONSTER_FILE_NAME = "monster.ljf";

    private static String townNames[];
    private static String townSuffixes[];

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

    /*
     * Distance
     */

    private static int increaseDistanceWalked(@NonNull Context context, int amount)
    {
        SharedPreferences preferences = preferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        int remaining = distanceToTown(context) - amount;
        if (remaining < 0)
        {
            remaining = 0;
        }
        int total = totalDistanceTraveled(context) + amount;
        editor.putInt(PREFERENCE_DISTANCE_REMAINING, remaining);
        editor.putInt(PREFERENCE_DISTANCE_TOTAL, total);
        editor.apply();
        return remaining;
    }

    public static void decreaseDistanceWalked(@NonNull Context context, int amount)
    {
        SharedPreferences preferences = preferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        int remaining = distanceToTown(context) + amount;
        editor.putInt(PREFERENCE_DISTANCE_REMAINING, remaining);
        editor.apply();
    }

    public static int totalTownDistance(@NonNull Context context)
    {
        return preferences(context).getInt(PREFERENCE_DISTANCE_START, 10);
    }

    public static int distanceToTown(@NonNull Context context)
    {
        int totalDistance = totalTownDistance(context);
        return preferences(context).getInt(PREFERENCE_DISTANCE_REMAINING, totalDistance);
    }

    private static int totalDistanceTraveled(@NonNull Context context)
    {
        return preferences(context).getInt(PREFERENCE_DISTANCE_TOTAL, 0);
    }

    /*
     * Town
     */

    static int townsVisited(@NonNull Context context)
    {
        return preferences(context).getInt(PREFERENCE_TOWN_COUNT, 0);
    }

    public static void saveTown(@NonNull Context context, @NonNull Town town)
    {
        SharedPreferences.Editor editor = editor(context);
        editor.putString(PREFERENCE_TOWN_NAME, town.name());
        editor.putInt(PREFERENCE_TOWN_SCOST, town.strengthCost());
        editor.putInt(PREFERENCE_TOWN_DCOST, town.defenseCost());
        editor.putInt(PREFERENCE_TOWN_HCOST, town.healthCost());
        editor.apply();
    }

    public static @NonNull Town town(@NonNull Context context)
    {
        SharedPreferences prefs = preferences(context);
        final String name = prefs.getString(PREFERENCE_TOWN_NAME, null);
        Town townToReturn;
        if (name == null)
        {
            if (townNames == null)
            {
                townNames = context.getResources().getStringArray(R.array.town_names);
            }
            if (townSuffixes == null)
            {
                townSuffixes = context.getResources().getStringArray(R.array.town_suffixes);
            }
            townToReturn = Town.generateRandomTown(townNames, townSuffixes);
        }
        else
        {
            final int sCost = prefs.getInt(PREFERENCE_TOWN_SCOST, 1);
            final int dCost = prefs.getInt(PREFERENCE_TOWN_DCOST, 3);
            final int hCost = prefs.getInt(PREFERENCE_TOWN_HCOST, 5);
            townToReturn = new Town(name, sCost, dCost, hCost);
            saveTown(context, townToReturn);
        }
        return townToReturn;
    }

    public static void leaveTown(@NonNull Context context)
    {
        SharedPreferences preferences = preferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(PREFERENCE_TOWN_NAME);
        editor.remove(PREFERENCE_TOWN_SCOST);
        editor.remove(PREFERENCE_TOWN_DCOST);
        editor.remove(PREFERENCE_TOWN_HCOST);
        editor.putInt(PREFERENCE_TOWN_COUNT, preferences.getInt(PREFERENCE_TOWN_COUNT, 0) + 1);
        editor.apply();
    }

    public static void enterTown(@NonNull Context context)
    {
        SharedPreferences preferences = preferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        // Increase number of towns visited.
        final int townsVisited = preferences.getInt(PREFERENCE_TOWN_COUNT, 0) + 1;
        editor.putInt(PREFERENCE_TOWN_COUNT, townsVisited);

        // TODO: Better difficulty curve system.
        final int previousDistance = preferences(context).getInt(PREFERENCE_DISTANCE_START, 10);
        final int newDistance = (int) (previousDistance * 1.5);
        editor.putInt(PREFERENCE_DISTANCE_REMAINING, newDistance);
        editor.putInt(PREFERENCE_DISTANCE_START, newDistance);

        editor.apply();
    }

    /*
     * Monster
     */

    public static void saveMonster(@NonNull Context context, @NonNull Monster monster)
    {
        writeToFile(context, MONSTER_FILE_NAME, monster.toString());
    }

    private static void resetMonster(@NonNull Context context)
    {
        writeToFile(context, MONSTER_FILE_NAME, null);
    }

    public static @NonNull Monster monster(@NonNull Context context)
    {
        String monsterString = readFromFile(context, MONSTER_FILE_NAME);
        Monster monsterToReturn = Monster.loadFromString(monsterString);
        if (monsterToReturn != null)
        {
            return monsterToReturn;
        }

        Monster.Builder monsterBuilder = new Monster.Builder()
                .name("Evil Bunny")
                .level(1)
                .maxHealth(2)
                .strength(1)
                .defense(1)
                .experience(2)
                .gold(1);
        return monsterBuilder.build();
    }

    /*
     * Battle
     */

    public static boolean isAvoidSuccessful(@NonNull Context context)
    {
        int success = preferences(context).getInt(PREFERENCE_AVOID_SUCCESS, 0);
        if (success == 1)
        {
            return true;
        }
        else if (success == -1)
        {
            return false;
        }
        else
        {
            double random = Math.random();
            editor(context).putInt(PREFERENCE_AVOID_SUCCESS, random < 0.5f ? -1 : 1);
            return isAvoidSuccessful(context);
        }
    }

    public static void resetAvoidSuccess(@NonNull Context context)
    {
        editor(context).remove(PREFERENCE_AVOID_SUCCESS);
    }

    public static void saveDidPlayerWin(@NonNull Context context, boolean didPlayerWin)
    {
        editor(context).putBoolean(PREFERENCE_BATTLE_OUTCOME, didPlayerWin);
    }

    private static boolean isPlayerWinner(@NonNull Context context)
    {
        return preferences(context).getBoolean(PREFERENCE_BATTLE_OUTCOME, false);
    }

    public static void completeBattle(@NonNull Context context)
    {
        final Monster monster = monster(context);
        final int goldChange = monster.goldCarried();
        final int experienceChange = monster.currentExperience();

        final Player player = player(context);
        if (isPlayerWinner(context))
        {
            player.gainGold(goldChange);
            player.gainExperience(context, experienceChange);
        }
        else
        {
            player.loseGold(goldChange);
        }

        savePlayer(context, player);
        resetMonster(context);
    }

    /*
     * Location
     */

    public static void saveCurrentLocation(@NonNull Context context,
                                    @PlayerLocation int location)
    {
        SharedPreferences.Editor editor = editor(context);
        editor.putInt(PREFERENCE_LOCATION, location);
        editor.apply();
    }

    public static @PlayerLocation
    int currentLocation(@NonNull Context context)
    {
        SharedPreferences preferences = preferences(context);
        int locationNum = preferences.getInt(PREFERENCE_LOCATION, TOWN);
        switch(locationNum)
        {
            case 2:
                return TRAVEL;
            case 3:
                return ENGAGE;
            case 4:
                return BATTLE;
            case 5:
                return BATTLE_REWARD;
            case 6:
                return BATTLE_LOST;
            case 7:
                return REST;
            case 8:
                return SNEAK;
            case 9:
                return RUN;
            default:
                return TOWN;
        }
    }

    /*
     * Player
     */

    public static void savePlayer(@NonNull Context context, @NonNull Player player)
    {
        writeToFile(context, PLAYER_FILE_NAME, player.toString());
    }

    public static @NonNull Player player(@NonNull Context context)
    {
        final String playerString = readFromFile(context, PLAYER_FILE_NAME);
        Player playerToReturn = Player.loadFromString(playerString);
        if (playerToReturn != null)
        {
            return playerToReturn;
        }

        playerToReturn = new Player();
        savePlayer(context, playerToReturn);
        return playerToReturn;
    }

    /*
     * Utility
     */

    private static void writeToFile(@NonNull Context context,
                                    @NonNull String fileName,
                                    @Nullable String data
    ) {
        FileOutputStream outputStream;
        try
        {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            if (data == null) {
                outputStream.write(new byte[]{});
            }
            else
            {
                outputStream.write(data.getBytes());
            }
            outputStream.flush();
            outputStream.close();
        }
        catch (FileNotFoundException e) {
            Log.e(TAG, "Could not save " + fileName + " because file not openable for write.");
        } catch (IOException e) {
            Log.e(TAG, "Could not save " + fileName + " because could not write to file.");
        }
    }

    private static @Nullable String readFromFile(@NonNull Context context, @NonNull String fileName)
    {
        FileInputStream inputStream;
        StringBuilder builder = new StringBuilder();
        try
        {
            inputStream = context.openFileInput(fileName);

            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Could not load " + fileName + " because file found for reading.");
            return null;
        } catch (IOException e) {
            Log.e(TAG, "Could not load " + fileName + " because file was not readable.");
            return null;
        }
        return builder.toString();
    }

    private static SharedPreferences preferences(@NonNull Context context)
    {
        return context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor editor(@NonNull Context context)
    {
        return preferences(context).edit();
    }

}