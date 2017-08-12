package com.lchrislee.longjourney.model.creatures;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Monster extends CreatureBase {

    private String name;

    // TODO: Something for images.

    private Monster()
    {
        super(5, 2, 2, 1, 1, 1);
        this.name = "";
    }

    public Monster(@NonNull String name) {
        super(5, 2, 2, 1, 1, 1);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        // TODO: parse out data to string.
        return builder.toString();
    }

    public static @Nullable Monster loadFromString(@Nullable String monsterString)
    {
        if (monsterString == null)
        {
            return null;
        }
        Monster monster = new Monster();
        // TODO: parse input string.
        return monster;
    }
}
