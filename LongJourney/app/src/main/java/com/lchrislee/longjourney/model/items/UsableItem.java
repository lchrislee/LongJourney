package com.lchrislee.longjourney.model.items;

import android.support.annotation.NonNull;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class UsableItem {
    private @NonNull String name;
    private @NonNull String description;

    public UsableItem(@NonNull String name, @NonNull String description) {
        this.name = name;
        this.description = description;
    }

    public @NonNull String getName() {
        return name;
    }

    public @NonNull String getDescription() {
        return description;
    }

}
