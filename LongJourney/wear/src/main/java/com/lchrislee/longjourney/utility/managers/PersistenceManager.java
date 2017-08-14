package com.lchrislee.longjourney.utility.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lchrislee.longjourney.model.Town;
import com.lchrislee.longjourney.model.creatures.Monster;
import com.lchrislee.longjourney.model.creatures.Player;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class PersistenceManager extends LongJourneyBaseManager {

    private static final String TAG = "PERSISTENCE_MANAGER";

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

    private static final String PLAYER_FILE_NAME = "player.ljf";
    private static final String MONSTER_FILE_NAME = "monster.ljf";

    /*
     * Distance
     */

    static int increaseDistanceWalked(@NonNull Context context, int amount)
    {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        int remaining = loadDistanceToTown(context) - amount;
        if (remaining < 0)
        {
            remaining = 0;
        }
        int total = loadTotalDistanceTraveled(context) + amount;
        editor.putInt(PREFERENCE_DISTANCE_REMAINING, remaining);
        editor.putInt(PREFERENCE_DISTANCE_TOTAL, total);
        editor.apply();
        return remaining;
    }

    static int loadTotalTownDistance(@NonNull Context context)
    {
        return getPreferences(context).getInt(PREFERENCE_DISTANCE_START, 10);
    }

    static int loadDistanceToTown(@NonNull Context context)
    {
        int totalDistance = loadTotalTownDistance(context);
        return getPreferences(context).getInt(PREFERENCE_DISTANCE_REMAINING, totalDistance);
    }

    static int loadTotalDistanceTraveled(@NonNull Context context)
    {
        return getPreferences(context).getInt(PREFERENCE_DISTANCE_TOTAL, 0);
    }

    /*
     * Town
     */

    static int loadTownsVisited(@NonNull Context context)
    {
        return getPreferences(context).getInt(PREFERENCE_TOWN_COUNT, 0);
    }

    static void saveTown(@NonNull Context context, @NonNull Town town)
    {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCE_TOWN_NAME, town.getName());
        editor.putInt(PREFERENCE_TOWN_SCOST, town.getStrengthCost());
        editor.putInt(PREFERENCE_TOWN_DCOST, town.getDefenseCost());
        editor.putInt(PREFERENCE_TOWN_HCOST, town.getHealthCost());
        editor.apply();
    }

    static @Nullable Town loadTown(@NonNull Context context)
    {
        SharedPreferences prefs = getPreferences(context);
        String name = prefs.getString(PREFERENCE_TOWN_NAME, null);
        if (name == null)
        {
            return null;
        }
        else
        {
            int sCost = prefs.getInt(PREFERENCE_TOWN_SCOST, 1);
            int dCost = prefs.getInt(PREFERENCE_TOWN_DCOST, 3);
            int hCost = prefs.getInt(PREFERENCE_TOWN_HCOST, 5);
            return new Town(name, sCost, dCost, hCost);
        }
    }

    static void leaveTown(@NonNull Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(PREFERENCE_TOWN_NAME);
        editor.remove(PREFERENCE_TOWN_SCOST);
        editor.remove(PREFERENCE_TOWN_DCOST);
        editor.remove(PREFERENCE_TOWN_HCOST);
        editor.putInt(PREFERENCE_TOWN_COUNT, preferences.getInt(PREFERENCE_TOWN_COUNT, 0) + 1);
        editor.apply();
    }

    static void enterTown(@NonNull Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        // Increase number of towns visited.
        int townsVisited = preferences.getInt(PREFERENCE_TOWN_COUNT, 0) + 1;
        editor.putInt(PREFERENCE_TOWN_COUNT, townsVisited);

        // TODO: Better difficulty curve system.
        int previousDistance = getPreferences(context).getInt(PREFERENCE_DISTANCE_START, 10);
        int newDistance = (int) (previousDistance * 1.5);
        editor.putInt(PREFERENCE_DISTANCE_REMAINING, newDistance);
        editor.putInt(PREFERENCE_DISTANCE_START, newDistance);

        editor.apply();
    }

    /*
     * Monster
     */

    static @NonNull Monster generateMonster(@NonNull Context context)
    {
        Monster.Builder monsterBuilder = new Monster.Builder()
            .setName("Evil Bunny")
            .setLevel(1)
            .setMaxHealth(2)
            .setStrength(1)
            .setDefense(1)
            .setExperience(2)
            .setGold(1);
        return monsterBuilder.build();
    }

    static void saveMonster(@NonNull Context context, @NonNull Monster monster)
    {
        writeToFile(context, MONSTER_FILE_NAME, monster.toString());
    }

    static void clearMonster(@NonNull Context context)
    {
        writeToFile(context, MONSTER_FILE_NAME, null);
    }

    static @Nullable Monster loadMonster(@NonNull Context context)
    {
        String monsterString = readFromFile(context, MONSTER_FILE_NAME);
        return Monster.loadFromString(monsterString);
    }

    /*
     * Battle
     */

    static boolean getAvoidSuccess(@NonNull Context context)
    {
        int success = getPreferences(context).getInt(PREFERENCE_AVOID_SUCCESS, 0);
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
            getEditor(context).putInt(PREFERENCE_AVOID_SUCCESS, random < 0.5f ? -1 : 1);
            return getAvoidSuccess(context);
        }
    }

    static void clearAvoidSuccess(@NonNull Context context)
    {
        getEditor(context).remove(PREFERENCE_AVOID_SUCCESS);
    }

    /*
     * Location
     */

    static void saveCurrentLocation(@NonNull Context context,
                                    @DataManager.PlayerLocation int location)
    {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(PREFERENCE_LOCATION, location);
        editor.apply();
    }

    static @DataManager.PlayerLocation
    int loadCurrentLocation(@NonNull Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        int locationNum = preferences.getInt(PREFERENCE_LOCATION, DataManager.TOWN);
        switch(locationNum)
        {
            case 2:
                return DataManager.TRAVEL;
            case 3:
                return DataManager.ENGAGE;
            case 4:
                return DataManager.BATTLE;
            case 5:
                return DataManager.BATTLE_REWARD;
            case 6:
                return DataManager.BATTLE_LOST;
            case 7:
                return DataManager.REST;
            case 8:
                return DataManager.SNEAK;
            case 9:
                return DataManager.RUN;
            default:
                return DataManager.TOWN;
        }
    }

    /*
     * Player
     */

    static void savePlayer(@NonNull Context context, @NonNull Player player)
    {
        writeToFile(context, PLAYER_FILE_NAME, player.toString());
    }

    static @Nullable Player loadPlayer(@NonNull Context context)
    {
        String playerString = readFromFile(context, PLAYER_FILE_NAME);
        return Player.loadFromString(playerString);
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

    private static SharedPreferences getPreferences(@NonNull Context context)
    {
        return context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(@NonNull Context context)
    {
        return getPreferences(context).edit();
    }

}