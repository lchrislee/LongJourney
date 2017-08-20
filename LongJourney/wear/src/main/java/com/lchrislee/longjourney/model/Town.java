package com.lchrislee.longjourney.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Town
    extends BaseModel
{
    private String name;

    private int strengthCost;
    private int defenseCost;
    private int healthCost;

    public static @NonNull Town generateRandomTown(
        String[] possibleNames,
        String[] possibleSuffixes
    ) {
        return new Town(
            possibleNames[(int)(Math.random() * possibleNames.length)]
            + " "
            + possibleSuffixes[(int)(Math.random() * possibleSuffixes.length)]
        );
    }

    private Town(@NonNull String name)
    {
        this.name = name;
        strengthCost = 1;
        defenseCost = 3;
        healthCost = 5;
    }

    public Town()
    {
        this.name = "";
        strengthCost = 1;
        defenseCost = 3;
        healthCost = 5;
    }

    public @NonNull String name() {
        return name;
    }

    public int strengthCost() {
        return strengthCost;
    }

    public int defenseCost() {
        return defenseCost;
    }

    public int healthCost() {
        return healthCost;
    }

    public void purchaseStrength()
    {
        this.strengthCost += 3;
    }

    public void purchaseDefense()
    {
        this.defenseCost += 3;
    }

    public void purchaseHealth()
    {
        this.healthCost += 5;
    }

    @Nullable
    @Override
    public Town fromJSONString(@NonNull String inputData) {
        JSONObject townJSON;

        try {
            townJSON = new JSONObject(inputData);
            this.name = townJSON.getString("name");
            this.strengthCost = townJSON.getInt("strength");
            this.defenseCost = townJSON.getInt("defense");
            this.healthCost = townJSON.getInt("health");
        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), "Could not parse file input.");
            return null;
        }

        return this;
    }

    @NonNull
    @Override
    public JSONObject toJSON() {
        JSONObject townObject = new JSONObject();
        try {
            townObject.put("name", this.name);
            townObject.put("strength", this.strengthCost);
            townObject.put("defense", this.defenseCost);
            townObject.put("health", this.healthCost);
        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), "Could not output JSON data.");
            return new JSONObject();
        }
        return townObject;
    }

}
