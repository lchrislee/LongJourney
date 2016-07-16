package com.lchrislee.longjourney.utility;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.model.actors.Actor;
import com.lchrislee.longjourney.model.actors.Player;
import com.lchrislee.longjourney.utility.constants.SharedPreferenceConstants;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class ActorUtility {

    public static @NonNull
    Actor pullDataFromPreferences(@NonNull SharedPreferences sharedPreferences){
        long stepRef = sharedPreferences.getLong(SharedPreferenceConstants.STEP_REFERENCE, 0);
        long stepCount = sharedPreferences.getLong(SharedPreferenceConstants.STEP_COUNT, 0);
        long playerGold = sharedPreferences.getLong(SharedPreferenceConstants.PLAYER_GOLD, 0);
        int playerHealth = sharedPreferences.getInt(SharedPreferenceConstants.PLAYER_HEALTH, 10);
        long playerLevel = sharedPreferences.getLong(SharedPreferenceConstants.PLAYER_LEVEL, 1);
        long playerStrength = sharedPreferences.getLong(SharedPreferenceConstants.PLAYER_STRENGTH, 1);
        long playerDefense = sharedPreferences.getLong(SharedPreferenceConstants.PLAYER_DEFENSE, 1);
        int playerXp = sharedPreferences.getInt(SharedPreferenceConstants.PLAYER_XP, 0);

        Player.Builder playerBuilder = new Player.Builder();
        playerBuilder.stepReference(stepRef);
        playerBuilder.stepCount(stepCount);
        playerBuilder.gold(playerGold);
        playerBuilder.maxHealth(playerHealth);
        playerBuilder.level(playerLevel);
        playerBuilder.strength(playerStrength);
        playerBuilder.defense(playerDefense);
        playerBuilder.xp(playerXp);
        return playerBuilder.build();
    }
}
