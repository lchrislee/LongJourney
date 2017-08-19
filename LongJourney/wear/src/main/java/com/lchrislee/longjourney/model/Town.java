package com.lchrislee.longjourney.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.utility.DataPersistence;

public class Town {
    private String name;

    private int strengthCost;
    private int defenseCost;
    private int healthCost;

    public static @NonNull Town generateRandomTown(
        String[] possibleNames,
        String[] possibleSuffixes
    ) {
        return new Town(possibleNames[(int)(Math.random() * possibleNames.length)]
                + " "
                + possibleSuffixes[(int)(Math.random() * possibleSuffixes.length)]);
    }

    public Town(@NonNull String name, int sCost, int dCost, int hCost)
    {
        this.name = name;
        this.strengthCost = sCost;
        this.defenseCost = dCost;
        this.healthCost = hCost;
    }

    private Town(@NonNull String name)
    {
        this.name = name;
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

    public void save(@NonNull Context context)
    {
        DataPersistence.saveTown(context, this);
    }

}
