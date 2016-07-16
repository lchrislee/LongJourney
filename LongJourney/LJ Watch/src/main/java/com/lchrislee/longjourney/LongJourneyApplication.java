package com.lchrislee.longjourney;

import android.app.Application;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.model.actors.Monster;
import com.lchrislee.longjourney.model.actors.Player;
import com.lchrislee.longjourney.utility.ActorUtility;
import com.lchrislee.longjourney.utility.constants.SharedPreferenceConstants;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class LongJourneyApplication extends Application {

    private Player player;
    private Monster monster;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public @NonNull Player getPlayer(){
        if (player == null){
            player = (Player) ActorUtility.pullDataFromPreferences(
                    getSharedPreferences(SharedPreferenceConstants.STEP_PREF_NAME, MODE_PRIVATE));
        }
        return player;
    }

    public @NonNull Monster getMonster(){
        if (monster == null){
            monster = new Monster.Builder().buildDefault();
        }
        return monster;
    }
}
