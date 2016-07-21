package com.lchrislee.longjourney.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.actors.Monster;
import com.lchrislee.longjourney.model.actors.Player;

/**
 * Created by Chris Lee on 7/16/16.
 */
public class BattleUtility {
    private static final String TAG = BattleUtility.class.getSimpleName();

    public static final int BATTLE_OPTION_FIGHT = 0; // TODO MAKE THESE BETTER
    public static final int BATTLE_OPTION_SNEAK = 1;
    public static final int BATTLE_OPTION_RUN = 2;

    public static boolean determineSneakSuccess(@NonNull Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SharedPreferenceUtility.STEP_PREF_NAME,
                Context.MODE_PRIVATE);

        Player p = SharedPreferenceUtility.pullPlayerFromPreferences(sharedPreferences);
        Monster m = SharedPreferenceUtility.pullMonsterFromPreferences(sharedPreferences);

        double threshold;
        long playerLevel = p.getLevel(), monsterLevel = m.getLevel();
        if (playerLevel >= (monsterLevel / 1.5)){
            threshold = 0.75;
        }else if (playerLevel >= (monsterLevel / 2)){
            threshold = 0.5;
        }else if (playerLevel >= (monsterLevel / 4)){
            threshold = 0.25;
        }else{
            threshold = 0.1;
        }

        Log.d(TAG, "Threshold is: " + threshold);
        return Math.random() < threshold;
    }

    public static void fight(@NonNull Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SharedPreferenceUtility.STEP_PREF_NAME,
                Context.MODE_PRIVATE);

        Player p = SharedPreferenceUtility.pullPlayerFromPreferences(sharedPreferences);
        Monster m = ActorUtility.generateMonster(p);

        Log.d(TAG, "Player health: " + p.getCurrentHealth());
        Log.d(TAG, "Monster health: " + m.getCurrentHealth());
        Log.d(TAG, "Player strength: " + p.getStrength() + " and weapon strength: " + p.getWeapon().getAttack());
        Log.d(TAG, "Player totalDamage: " + p.getTotalDamage());
        Log.d(TAG, "Monster totalDamage: " + m.getTotalDamage());
        Log.d(TAG, "Player defense: " + p.getDefense());
        Log.d(TAG, "Monster defense: " + m.getDefense());
        while (p.getCurrentHealth() > 0 && m.getCurrentHealth() > 0){
            Log.d(TAG, "Player health: " + p.getCurrentHealth());
            Log.d(TAG, "Monster health: " + m.getCurrentHealth());
            int damageToPlayer = -1 * m.getTotalDamage() + p.getDefense();
            int damageToMonster = -1 * p.getTotalDamage() + m.getDefense();
            Log.d(TAG, "Damage to player: " + damageToPlayer);
            Log.d(TAG, "Damage to monster: " + damageToMonster);
            p.changeCurrentHealthBy(damageToPlayer);
            m.changeCurrentHealthBy(damageToMonster);
        }

        Log.d(TAG, "player: " + p.getCurrentHealth() + " and monster: " + m.getCurrentHealth());
        SharedPreferenceUtility.storeMonster(m, sharedPreferences);
        SharedPreferenceUtility.storePlayer(p, sharedPreferences);
    }

    public static @Nullable Drawable getMonsterDrawable(@NonNull Context context){
        return ResourcesCompat.getDrawable(context.getResources(), R.drawable.slime, null);
    }
}
