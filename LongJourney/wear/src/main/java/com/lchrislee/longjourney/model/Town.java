package com.lchrislee.longjourney.model;

import android.support.annotation.NonNull;

public class Town extends LongJourneyBaseModel {
    private String name;

    private int strengthCost;
    private int defenseCost;
    private int healthCost;

    public static @NonNull Town generateRandomTown(
            String[] possibleNames,
            String[] possibleSuffixes
    )
    {
        Town generatedTown = new Town();
        String townName = possibleNames[(int)(Math.random() * possibleNames.length)]
                + " "
                + possibleSuffixes[(int)(Math.random() * possibleSuffixes.length)];
        generatedTown.setName(townName);
        return generatedTown;
    }

    private Town()
    {
        name = "";
        strengthCost = 1;
        defenseCost = 3;
        healthCost = 5;
    }

    public @NonNull String getName() {
        return name;
    }

    public int getStrengthCost() {
        return strengthCost;
    }

    public int getDefenseCost() {
        return defenseCost;
    }

    public int getHealthCost() {
        return healthCost;
    }

    private void setName(@NonNull String name) {
        this.name = name;
    }

    private void setStrengthCost(int strengthCost) {
        this.strengthCost = strengthCost;
    }

    private void setDefenseCost(int defenseCost) {
        this.defenseCost = defenseCost;
    }

    private void setHealthCost(int healthCost) {
        this.healthCost = healthCost;
    }

    public void increaseStrengthCost()
    {
        setStrengthCost(getStrengthCost() + 3);
    }

    public void increaseDefenseCost()
    {
        setDefenseCost(getDefenseCost() + 3);
    }

    public void increaseHealthCost()
    {
        setHealthCost(getHealthCost() + 5);
    }

}
