package com.lchrislee.longjourney.model.creatures;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lchrislee.longjourney.model.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

abstract class BaseCreature
    extends BaseModel
{
    int maxHealth;
    int currentHealth;
    int currentExperience;
    int goldCarried;

    int level;
    int strength;
    int defense;

    BaseCreature(
        int maxHealth,
        int currentExperience,
        int goldCarried,
        int level,
        int strength,
        int defense
    ) {
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
        this.currentExperience = currentExperience;
        this.goldCarried = goldCarried;
        this.level = level;
        this.strength = strength;
        this.defense = defense;
    }

    public int maxHealth() {
        return maxHealth;
    }

    public int currentHealth() {
        return currentHealth;
    }

    public int currentExperience() {
        return currentExperience;
    }

    public int goldCarried() {
        return goldCarried;
    }

    int level() {
        return level;
    }

    public int strength() {
        return strength;
    }

    public int defense() {
        return defense;
    }

    @Nullable
    @Override
    public BaseModel fromJSONString(@NonNull String inputData) {
        JSONObject data;
        try {
            data = new JSONObject(inputData);

            this.goldCarried = data.getInt("gold");
            this.level = data.getInt("level");
            this.strength = data.getInt("strength");
            this.defense = data.getInt("defense");

            final JSONObject experience = data.getJSONObject("experience");
            this.currentExperience = experience.getInt("current");

            final JSONObject health = data.getJSONObject("health");
            this.maxHealth = health.getInt("max");
            this.currentHealth = health.getInt("current");

        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), "Could not parse file input.");
            return null;
        }
        return this;
    }

    @NonNull
    @Override
    public JSONObject toJSON() {
        JSONObject modelJSON = new JSONObject();
        try {
            modelJSON.put("gold", goldCarried);
            modelJSON.put("level", level);
            modelJSON.put("strength", strength);
            modelJSON.put("defense", defense);

            JSONObject experienceJSON = new JSONObject();
            experienceJSON.put("current", currentExperience);
            modelJSON.put("experience", experienceJSON);

            JSONObject healthJSON = new JSONObject();
            healthJSON.put("current", currentHealth);
            healthJSON.put("max", maxHealth);
            modelJSON.put("health", healthJSON);
        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), "Could not create JSON output.");
            return new JSONObject();
        }

        return modelJSON;
    }
}
