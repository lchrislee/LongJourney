package com.lchrislee.longjourney.managers;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StorageManager extends LongJourneyManagerBase {

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

    private static final String PLAYER_FILE_NAME = "player.ljf";
    private static final String MONSTER_FILE_NAME = "monster.ljf";

    public static void saveTown(@NonNull Context context, @NonNull Town town)
    {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCE_TOWN_NAME, town.getName());
        editor.putInt(PREFERENCE_TOWN_SCOST, town.getStrengthCost());
        editor.putInt(PREFERENCE_TOWN_DCOST, town.getDefenseCost());
        editor.putInt(PREFERENCE_TOWN_HCOST, town.getHealthCost());
        editor.apply();
    }

    public static @Nullable Town loadTown(@NonNull Context context)
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

    public static void clearTown(@NonNull Context context)
    {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(PREFERENCE_TOWN_NAME);
        editor.remove(PREFERENCE_TOWN_SCOST);
        editor.remove(PREFERENCE_TOWN_DCOST);
        editor.remove(PREFERENCE_TOWN_HCOST);
        editor.apply();
    }

    public static void saveMonster(@NonNull Context context, @NonNull Monster monster)
    {
        writeToFile(context, MONSTER_FILE_NAME, monster.toString());
    }

    public static void clearMonster(@NonNull Context context)
    {
        writeToFile(context, MONSTER_FILE_NAME, null);
    }

    public static @Nullable Monster loadMonster(@NonNull Context context)
    {
        String monsterString = readFromFile(context, MONSTER_FILE_NAME);
        return Monster.loadFromString(monsterString);
    }

    public static void saveCurrentLocation(@NonNull Context context, @PlayerLocation int location)
    {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(PREFERENCE_LOCATION, location);
        editor.apply();
    }

    public static @PlayerLocation int loadCurrentLocation(@NonNull Context context)
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

    public static void savePlayer(@NonNull Context context, @NonNull Player player)
    {
        Log.e(TAG, "saving player");
        writeToFile(context, PLAYER_FILE_NAME, player.toString());
    }

    public static @Nullable Player loadPlayer(@NonNull Context context)
    {
        Log.e(TAG, "directory: " + context.getFilesDir().getName());
        for(File f : context.getFilesDir().listFiles())
        {
            Log.e(TAG, "reading files: " + f.getName() + " at " + System.lineSeparator() + f.getAbsolutePath());
        }

        String playerString = readFromFile(context, PLAYER_FILE_NAME);
        Log.e(TAG, "player loads from: " + playerString);
        return Player.loadFromString(playerString);
    }

    private static void writeToFile(@NonNull Context context,
                                    @NonNull String fileName,
                                    @Nullable String data
    )
    {
        FileOutputStream outputStream;
        try
        {
            if (data == null)
            {
                File file = new File(fileName);
                if (!file.delete())
                {
                    Log.e(TAG, "Could not delete file " + fileName);
                }
            }
            else
            {
                Log.e(TAG, "data: " + data);
                outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write(data.getBytes());
                outputStream.flush();
                outputStream.close();
            }
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
