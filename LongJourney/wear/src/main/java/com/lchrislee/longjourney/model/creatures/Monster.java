package com.lchrislee.longjourney.model.creatures;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Monster extends BaseCreature {

    private String name;

    // TODO: Something for images.

    public Monster()
    {
        super(5, 2, 2, 1, 1, 1);
        this.name = "";
    }

    public @NonNull String name() {
        return name;
    }

    @Nullable
    @Override
    public Monster fromJSONString(@NonNull String inputData) {
        if (super.fromJSONString(inputData) == null)
        {
            return null;
        }

        JSONObject monsterObject;

        try {
            monsterObject = new JSONObject(inputData);
            this.name = monsterObject.getString("name");
        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), "Could not parse file input.");
            return null;
        }

        return this;
    }

    @NonNull
    @Override
    public JSONObject toJSON() {
        JSONObject monsterObject = super.toJSON();
        if (!monsterObject.has("gold"))
        {
            return monsterObject;
        }

        try {
            monsterObject.put("name", name);
        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), "Could not create JSON output.");
            return new JSONObject();
        }

        return monsterObject;
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
