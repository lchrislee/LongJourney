package com.lchrislee.longjourney.model.creatures;

import android.support.annotation.NonNull;

public class Monster extends CreatureBase {

    private String name;

    // TODO: Something for images.


    public Monster(@NonNull String name) {
        /*
        int maxHealth,
            int currentHealth,
            int currentExperience,
            int goldCarried,
            int level,
            int strength,
            int defense
         */
        super(5, 2, 2, 1, 1, 1);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
