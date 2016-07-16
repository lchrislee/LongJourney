package com.lchrislee.longjourney.utility;

import android.support.annotation.NonNull;

/**
 * Created by Chris Lee on 7/16/16.
 */
public class BattleUtility {
    public static final int BATTLE_SNEAK_OPTION_LEFT = 0; // TODO MAKE THESE BETTER
    public static final int BATTLE_SNEAK_OPTION_RIGHT = 1;
    public static final int BATTLE_CONCLUSION_SNEAK = 0;

    public static boolean determineSneakSuccess(@NonNull Object tag){
        if (!(tag instanceof Integer)){
            return false;
        }else{
            boolean result = false;
            return result;
        }
    }
}
