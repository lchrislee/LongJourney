package com.lchrislee.longjourney.utility;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.lchrislee.longjourney.model.actors.Monster;
import com.lchrislee.longjourney.model.actors.Player;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class SharedPreferenceUtility {

    private static final String TAG = SharedPreferenceUtility.class.getSimpleName();

    public static final String STEP_PREF_NAME = SharedPreferenceUtility.class.getSimpleName() + ".STEP_PREF_NAME";
    public static final String STEP_REFERENCE = SharedPreferenceUtility.class.getSimpleName() + ".STEP_REFERENCE";
    public static final String STEP_COUNT = SharedPreferenceUtility.class.getSimpleName() + ".STEP_COUNT";

    public static final String PLAYER_HEALTH = SharedPreferenceUtility.class.getSimpleName() + ".PLAYER_HEALTH";
    public static final String PLAYER_CURRENT_HEALTH = SharedPreferenceUtility.class.getSimpleName() + ".PLAYER_CURRENT_HEALTH";
    public static final String PLAYER_GOLD = SharedPreferenceUtility.class.getSimpleName() + ".PLAYER_GOLD";
    public static final String PLAYER_LEVEL = SharedPreferenceUtility.class.getSimpleName() + ".PLAYER_LEVEL";
    public static final String PLAYER_STRENGTH = SharedPreferenceUtility.class.getSimpleName() + ".PLAYER_STRENGTH";
    public static final String PLAYER_DEFENSE = SharedPreferenceUtility.class.getSimpleName() + ".PLAYER_DEFENSE";
    public static final String PLAYER_XP = SharedPreferenceUtility.class.getSimpleName() + ".PLAYER_XP";

    public static final String MONSTER_HEALTH = SharedPreferenceUtility.class.getSimpleName() + ".MONSTER_HEALTH";
    public static final String MONSTER_CURRENT_HEALTH = SharedPreferenceUtility.class.getSimpleName() + ".MONSTER_CURRENT_HEALTH";
    public static final String MONSTER_GOLD = SharedPreferenceUtility.class.getSimpleName() + ".MONSTER_GOLD";
    public static final String MONSTER_LEVEL = SharedPreferenceUtility.class.getSimpleName() + ".MONSTER_LEVEL";
    public static final String MONSTER_STRENGTH = SharedPreferenceUtility.class.getSimpleName() + ".MONSTER_STRENGTH";
    public static final String MONSTER_DEFENSE = SharedPreferenceUtility.class.getSimpleName() + ".MONSTER_DEFENSE";
    public static final String MONSTER_XP = SharedPreferenceUtility.class.getSimpleName() + ".MONSTER_XP";
    public static final String MONSTER_NAME = SharedPreferenceUtility.class.getSimpleName() +  ".MONSTER_NAME";
    public static final String MONSTER_IMAGE = SharedPreferenceUtility.class.getSimpleName() + ".MONSTER_IMAGE";

    public static @NonNull Player pullPlayerFromPreferences(@NonNull SharedPreferences sharedPreferences){
        long stepRef = sharedPreferences.getLong(SharedPreferenceUtility.STEP_REFERENCE, 0);
        long stepCount = sharedPreferences.getLong(SharedPreferenceUtility.STEP_COUNT, 0);
        long playerGold = sharedPreferences.getLong(SharedPreferenceUtility.PLAYER_GOLD, 0);
        long playerLevel = sharedPreferences.getLong(SharedPreferenceUtility.PLAYER_LEVEL, 1);
        int playerHealth = sharedPreferences.getInt(SharedPreferenceUtility.PLAYER_HEALTH, 10);
        int playerCurrentHealth = sharedPreferences.getInt(SharedPreferenceUtility.PLAYER_CURRENT_HEALTH, 10);
        int playerStrength = sharedPreferences.getInt(SharedPreferenceUtility.PLAYER_STRENGTH, 5);
        int playerDefense = sharedPreferences.getInt(SharedPreferenceUtility.PLAYER_DEFENSE, 2);
        int playerXp = sharedPreferences.getInt(SharedPreferenceUtility.PLAYER_XP, 0);

        Log.d(TAG, "Player strength is: " + playerStrength);

        Player.Builder playerBuilder = new Player.Builder();
        playerBuilder.stepReference(stepRef);
        playerBuilder.stepCount(stepCount);
        playerBuilder.gold(playerGold);
        playerBuilder.level(playerLevel);
        playerBuilder.maxHealth(playerHealth);
        playerBuilder.currentHealth(playerCurrentHealth);
        playerBuilder.strength(playerStrength);
        playerBuilder.defense(playerDefense);
        playerBuilder.xp(playerXp);
        return playerBuilder.build();
    }

    public static void storePlayer(@NonNull Player p, @NonNull SharedPreferences sharedPreferences){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(PLAYER_LEVEL, p.getLevel());
        edit.putLong(PLAYER_GOLD, p.getGold());
        edit.putInt(PLAYER_HEALTH, p.getMaxHealth());
        edit.putInt(PLAYER_CURRENT_HEALTH, p.getCurrentHealth());
        edit.putInt(PLAYER_STRENGTH, p.getStrength());
        edit.putInt(PLAYER_DEFENSE, p.getDefense());
        edit.putInt(PLAYER_XP, p.getXp());
        edit.apply();
    }

    public static @NonNull Monster pullMonsterFromPreferences(@NonNull SharedPreferences sharedPreferences){
        String monsterName = sharedPreferences.getString(SharedPreferenceUtility.MONSTER_NAME, "");
        long monsterLevel = sharedPreferences.getLong(SharedPreferenceUtility.MONSTER_LEVEL, 1);
        long monsterGold = sharedPreferences.getLong(SharedPreferenceUtility.MONSTER_GOLD, 0);
        int monsterImage = sharedPreferences.getInt(SharedPreferenceUtility.MONSTER_IMAGE, 0);
        int monsterHealth = sharedPreferences.getInt(SharedPreferenceUtility.MONSTER_HEALTH, 10);
        int monsterCurrentHealth = sharedPreferences.getInt(SharedPreferenceUtility.MONSTER_CURRENT_HEALTH, 10);
        int monsterStrength = sharedPreferences.getInt(SharedPreferenceUtility.MONSTER_STRENGTH, 1);
        int monsterDefense = sharedPreferences.getInt(SharedPreferenceUtility.MONSTER_DEFENSE, 1);
        int monsterXp = sharedPreferences.getInt(SharedPreferenceUtility.MONSTER_XP, 0);

        Monster.Builder monsterBuilder = new Monster.Builder();
        monsterBuilder.name(monsterName);
        monsterBuilder.level(monsterLevel);
        monsterBuilder.gold(monsterGold);
        monsterBuilder.image(monsterImage);
        monsterBuilder.maxHealth(monsterHealth);
        monsterBuilder.currentHealth(monsterCurrentHealth);
        monsterBuilder.strength(monsterStrength);
        monsterBuilder.defense(monsterDefense);
        monsterBuilder.xp(monsterXp);
        return monsterBuilder.build();
    }

    public static void storeMonster(@NonNull Monster m, @NonNull SharedPreferences sharedPreferences){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(MONSTER_NAME, m.getName());
        edit.putLong(MONSTER_LEVEL, m.getLevel());
        edit.putLong(MONSTER_GOLD, m.getGold());
        edit.putInt(MONSTER_IMAGE, m.getImage());
        edit.putInt(MONSTER_HEALTH, m.getMaxHealth());
        edit.putInt(MONSTER_CURRENT_HEALTH, m.getCurrentHealth());
        edit.putInt(MONSTER_STRENGTH, m.getStrength());
        edit.putInt(MONSTER_DEFENSE, m.getDefense());
        edit.putInt(MONSTER_XP, m.getXp());
        edit.apply();
    }
}
