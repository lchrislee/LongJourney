package com.lchrislee.longjourney.utility.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntDef;
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

public class PersistenceManager extends LongJourneyManagerBase {

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

    private static final String TAG = "STORAGE_MANAGER";

    private static final String SHARED_PREFERENCES = "LongJourneyMainShared";
    private static final String PREFERENCE_LOCATION = "PREFERENCE_LOCATION";
    private static final String PREFERENCE_TOWN_NAME = "PREFERENCE_TOWN_NAME";
    private static final String PREFERENCE_TOWN_SCOST = "PREFERENCE_TOWN_SCOST";
    private static final String PREFERENCE_TOWN_DCOST = "PREFERENCE_TOWN_DCOST";
    private static final String PREFERENCE_TOWN_HCOST = "PREFERENCE_TOWN_HCOST";
    private static final String PREFERENCE_TOWN_COUNT = "PREFERENCE_TOWN_COUNT";

    private static final String PREFERENCE_DISTANCE_REMAINING = "PREFERENCE_DISTANCE_REMAINING";
    private static final String PREFERENCE_DISTANCE_TOTAL = "PREFERENCE_DISTANCE_TOTAL";

    private static final String PLAYER_FILE_NAME = "player.ljf";
    private static final String MONSTER_FILE_NAME = "monster.ljf";

    static void increaseDistanceWalked(@NonNull Context context, int amount)
    {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        int steps = preferences.getInt(PREFERENCE_DISTANCE_REMAINING, 0);
        long total = preferences.getLong(PREFERENCE_DISTANCE_TOTAL, 0);
        editor.putInt(PREFERENCE_DISTANCE_REMAINING, steps + amount);
        editor.putLong(PREFERENCE_DISTANCE_TOTAL, total + amount);
        editor.apply();
    }

    static int loadDistanceToTown(@NonNull Context context)
    {
        return getPreferences(context).getInt(PREFERENCE_DISTANCE_REMAINING, 0);
    }

    static int loadTotalDistanceTraveled(@NonNull Context context)
    {
        return getPreferences(context).getInt(PREFERENCE_DISTANCE_TOTAL, 0);
    }

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

    static void saveCurrentLocation(@NonNull Context context, @PlayerLocation int location)
    {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(PREFERENCE_LOCATION, location);
        editor.apply();
    }

    static @PlayerLocation int loadCurrentLocation(@NonNull Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        int locationNum = preferences.getInt(PREFERENCE_LOCATION, TOWN);
        switch(locationNum)
        {
            case 2:
                return TRAVEL;
            case 3:
                return BATTLE_MID;
            case 4:
                return BATTLE_REWARD;
            case 5:
                return BATTLE_LOST;
            case 6:
                return REST;
            case 7:
                return SNEAK;
            case 8:
                return RUN;
            default:
                return TOWN;
        }
    }

    static void savePlayer(@NonNull Context context, @NonNull Player player)
    {
        writeToFile(context, PLAYER_FILE_NAME, player.toString());
    }

    static @Nullable Player loadPlayer(@NonNull Context context)
    {
        String playerString = readFromFile(context, PLAYER_FILE_NAME);
        return Player.loadFromString(playerString);
    }

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