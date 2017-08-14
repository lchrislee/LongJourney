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

    public @NonNull String getName() {
        return name;
    }

    private void setName(@NonNull String name)
    {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        // TODO: parse out data to string.
        return builder.toString();
    }

    public static @Nullable Monster loadFromString(@Nullable String monsterString)
    {
        if (monsterString == null || monsterString.length() == 0)
        {
            return null;
        }
        Monster.Builder builder = new Builder();

        // TODO: parse input string.
        return builder.build();
    }

    public static class Builder
    {
        private String name;
        // TODO: Something for the image.
        private int maxHealth;
        private int level;
        private int experience;
        private int gold;
        private int strength;
        private int defense;

        public @NonNull Builder setLevel(int levelIn)
        {
            this.level = levelIn;
            return this;
        }

        public @NonNull Builder setName(String name) {
            this.name = name;
            return this;
        }

        public @NonNull Builder setMaxHealth(int maxHealth) {
            this.maxHealth = maxHealth;
            return this;
        }

        public @NonNull Builder setExperience(int experience) {
            this.experience = experience;
            return this;
        }

        public @NonNull Builder setGold(int gold) {
            this.gold = gold;
            return this;
        }

        public @NonNull Builder setStrength(int strength) {
            this.strength = strength;
            return this;
        }

        public @NonNull Builder setDefense(int defense) {
            this.defense = defense;
            return this;
        }

        public @NonNull Monster build()
        {
            Monster monster = new Monster();
            monster.setName(this.name);
            monster.setLevel(this.level);
            monster.setMaxHealth(this.maxHealth);
            monster.setCurrentHealth(this.maxHealth);
            monster.setCurrentExperience(this.experience);
            monster.setGoldCarried(this.gold);
            monster.setStrength(this.strength);
            monster.setDefense(this.defense);
            return monster;
        }
    }
}
