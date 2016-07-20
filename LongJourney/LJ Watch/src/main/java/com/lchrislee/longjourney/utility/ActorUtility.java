package com.lchrislee.longjourney.utility;

import android.support.annotation.NonNull;

import com.lchrislee.longjourney.model.actors.Monster;
import com.lchrislee.longjourney.model.actors.Player;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class ActorUtility {

    public static final int MONSTER_TYPE_SLIME = 0;
    public static final int MONSTER_TYPE_GOBLIN = 1;
    public static final int MONSTER_TYPE_TROLL = 2;

    public static @NonNull Monster generateMonster(@NonNull Player p){
        long stepCount = p.getStepCount();
        int monsterType;

        if (stepCount < 10){
            monsterType = MONSTER_TYPE_SLIME;
        }else if (stepCount < 30){
            monsterType = MONSTER_TYPE_GOBLIN;
        }else{
            monsterType = MONSTER_TYPE_TROLL;
        }

        return Monster.Builder.build(p, monsterType);
    }
}
