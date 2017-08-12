package com.lchrislee.longjourney.model.creatures;

public class Player extends CreatureBase {

    private long totalDistanceTraveled;
    private int currentDistanceTraveled;
    private int currentDistanceToTown;

    private int experienceForNextLevel;

    public Player()
    {
        super(50, 2, 4, 1, 1, 1);
        currentDistanceToTown = 10;
        experienceForNextLevel = 10;
    }

    public Player(
            int maxHealth,
            int currentExperience,
            int goldCarried,
            int level,
            int strength,
            int defense,
            long totalDistanceTraveled,
            int currentDistanceTraveled,
            int currentDistanceToTown,
            int experienceForNextLevel
    ) {
        super(maxHealth, currentExperience, goldCarried, level, strength, defense);
        this.totalDistanceTraveled = totalDistanceTraveled;
        this.currentDistanceTraveled = currentDistanceTraveled;
        this.currentDistanceToTown = currentDistanceToTown;;
        this.experienceForNextLevel = experienceForNextLevel;
    }

    public long getTotalDistanceTraveled() {
        return totalDistanceTraveled;
    }

    public int getCurrentDistanceTraveled() {
        return currentDistanceTraveled;
    }

    public int getCurrentDistanceToTown() {
        return currentDistanceToTown;
    }

    public int getExperienceForNextLevel() {
        return experienceForNextLevel;
    }

    public void loseGold(int goldLost)
    {
        int newGoldValue = getGoldCarried() - goldLost;
        if (newGoldValue < 0)
        {
            newGoldValue = 0;
        }

        setGoldCarried(newGoldValue);
    }

    public void increaseStrength()
    {
        setStrength(getStrength() + 1);
    }

    public void increaseDefense()
    {
        setDefense(getDefense() + 1);
    }

    public void increaseHealth()
    {
        setMaxHealth(getMaxHealth() + 10);
        setCurrentHealth(getMaxHealth());
    }

}
