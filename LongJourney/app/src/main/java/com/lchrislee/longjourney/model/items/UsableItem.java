package com.lchrislee.longjourney.model.items;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class UsableItem {
    private @NonNull String name;
    private @NonNull String description;
    private @DrawableRes int image;

    UsableItem(@NonNull String name, @NonNull String description, @DrawableRes int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public @NonNull String getName() {
        return name;
    }

    public @NonNull String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }
}
