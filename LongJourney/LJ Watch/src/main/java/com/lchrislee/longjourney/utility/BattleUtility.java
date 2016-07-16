package com.lchrislee.longjourney.utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;

import com.lchrislee.longjourney.R;

/**
 * Created by Chris Lee on 7/16/16.
 */
public class BattleUtility {
    public static final int BATTLE_SNEAK_OPTION_LEFT = 0; // TODO MAKE THESE BETTER
    public static final int BATTLE_SNEAK_OPTION_RIGHT = 1;

    public static final int BATTLE_OPTION_FIGHT = 2;
    public static final int BATTLE_OPTION_SNEAK = 3;
    public static final int BATTLE_OPTION_RUN = 4;

    public static final int BATTLE_FROM_TRAVEL = 5;

    public static boolean determineSneakSuccess(@NonNull Object tag){
        if (!(tag instanceof Integer)){
            return false;
        }else{
            boolean result = false;
            return result;
        }
    }

    public static @Nullable Drawable getMonsterDrawable(@NonNull Context context){
        return ResourcesCompat.getDrawable(context.getResources(), R.drawable.slime, null);
    }
}
