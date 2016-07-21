package com.lchrislee.longjourney;

import android.app.Application;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.model.actors.Monster;
import com.lchrislee.longjourney.model.actors.Player;
import com.lchrislee.longjourney.utility.SharedPreferenceUtility;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class LongJourneyApplication extends Application {

    private Player player;
    private Monster monster;

    public @NonNull Player getPlayer(){
        if (player == null){
            player = SharedPreferenceUtility.pullPlayerFromPreferences(
                    getSharedPreferences(SharedPreferenceUtility.STEP_PREF_NAME, MODE_PRIVATE));
        }
        return player;
    }

    public @NonNull Monster getMonster(){
        if (monster == null){
            monster = SharedPreferenceUtility.pullMonsterFromPreferences(
                    getSharedPreferences(SharedPreferenceUtility.STEP_PREF_NAME, MODE_PRIVATE));
        }
        return monster;
    }
}
