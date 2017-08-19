package com.lchrislee.longjourney.model.creatures;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Player extends BaseCreature {

    private int experienceForNextLevel;

    public Player()
    {
        super(50, 4, 10, 1, 1, 1);
        experienceForNextLevel = 10;
    }

    public void gainExperience(int experienceGained)
    {
        this.currentExperience += experienceGained;
        while (currentExperience() > getExperienceForNextLevel())
        {
            this.level += 1;
            increaseHealth();
            increaseStrength();
            increaseDefense();
            increaseExperienceForNextLevel();
        }
    }

    public int getExperienceForNextLevel() {
        return experienceForNextLevel;
    }

    private void setExperienceForNextLevel(int change)
    {
        experienceForNextLevel += change;
    }

    private void increaseExperienceForNextLevel()
    {
        this.currentExperience -= getExperienceForNextLevel();
        setExperienceForNextLevel((int) (getExperienceForNextLevel() * 1.25));
    }

    public void gainGold(int goldGained)
    {
        this.goldCarried += goldGained;
    }

    public void loseGold(int goldLost)
    {
        int newGoldValue = goldCarried() - goldLost;
        if (newGoldValue < 0)
        {
            newGoldValue = 0;
        }

        this.goldCarried = newGoldValue;
    }

    public void increaseStrength()
    {
        ++this.strength;
    }

    public void increaseDefense()
    {
        ++this.defense;
    }

    public void increaseHealth()
    {
        this.maxHealth += 10;
        this.currentHealth = this.maxHealth;
    }

    @Nullable
    @Override
    public Player fromJSONString(@NonNull String inputData) {
        if (super.fromJSONString(inputData) == null)
        {
            return null;
        }

        JSONObject data;
        try {
            data = new JSONObject(inputData);

            final JSONObject experience = data.getJSONObject("experience");
            this.experienceForNextLevel = experience.getInt("max");

        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), "Could not parse file input.");
            return null;
        }
        return this;
    }

    @NonNull
    @Override
    public JSONObject toJSON() {
        JSONObject playerJSON = super.toJSON();

        if (!playerJSON.has("gold"))
        {
            return playerJSON;
        }

        try {
            JSONObject experienceJSON = new JSONObject();
            experienceJSON.put("current", currentExperience);
            experienceJSON.put("max", experienceForNextLevel);
            playerJSON.put("experience", experienceJSON);

        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), "Could not create JSON output.");
            return new JSONObject();
        }

        return playerJSON;
    }
}
