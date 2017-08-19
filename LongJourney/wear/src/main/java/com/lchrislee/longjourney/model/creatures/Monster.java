package com.lchrislee.longjourney.model.creatures;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lchrislee.longjourney.utility.DataPersistence;

public class Monster extends BaseCreature {

    private String name;

    // TODO: Something for images.

    private Monster()
    {
        super(5, 2, 2, 1, 1, 1);
        this.name = "";
    }

    public @NonNull String name() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        // TODO: parse out data to string.
        return builder.toString();
    }

    public void save(@NonNull Context context)
    {
        DataPersistence.saveMonster(context, this);
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

        public @NonNull Builder level(int levelIn)
        {
            this.level = levelIn;
            return this;
        }

        public @NonNull Builder name(String name) {
            this.name = name;
            return this;
        }

        public @NonNull Builder maxHealth(int maxHealth) {
            this.maxHealth = maxHealth;
            return this;
        }

        public @NonNull Builder experience(int experience) {
            this.experience = experience;
            return this;
        }

        public @NonNull Builder gold(int gold) {
            this.gold = gold;
            return this;
        }

        public @NonNull Builder strength(int strength) {
            this.strength = strength;
            return this;
        }

        public @NonNull Builder defense(int defense) {
            this.defense = defense;
            return this;
        }

        public @NonNull Monster build()
        {
            Monster monster = new Monster();
            monster.name = this.name;
            monster.level = this.level;
            monster.maxHealth = this.maxHealth;
            monster.currentHealth = this.maxHealth;
            monster.currentExperience = this.experience;
            monster.goldCarried = this.gold;
            monster.strength = this.strength;
            monster.defense = this.defense;
            return monster;
        }
    }
}
