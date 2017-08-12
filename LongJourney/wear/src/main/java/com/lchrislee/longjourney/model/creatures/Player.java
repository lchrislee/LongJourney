package com.lchrislee.longjourney.model.creatures;

import android.support.annotation.Nullable;

public class Player extends CreatureBase {

    private int experienceForNextLevel;

    public Player()
    {
        super(50, 4, 10, 1, 1, 1);
        experienceForNextLevel = 10;
    }

    private Player(
            int maxHealth,
            int currentHealth,
            int currentExperience,
            int goldCarried,
            int level,
            int strength,
            int defense,
            int experienceForNextLevel
    ) {
        super(maxHealth, currentExperience, goldCarried, level, strength, defense);
        this.setCurrentHealth(currentHealth);
        this.experienceForNextLevel = experienceForNextLevel;
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

    /*
     * (0) current experience | exp for next level (int, int)
     * (1) gold (int)
     * (2) level (int)
     * (3) strength | defense (int, int)
     * (4) current health | max health (int, int)
     */

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getCurrentExperience()).append('|')
                .append(getExperienceForNextLevel()).append(' ');
        builder.append(getGoldCarried()).append(' ');
        builder.append(getLevel()).append(' ');
        builder.append(getStrength()).append('|')
                .append(getDefense()).append(' ');
        builder.append(getCurrentHealth()).append('|')
                .append(getMaxHealth());
        return builder.toString();
    }

    public @Nullable static Player loadFromString(@Nullable String stringifiedPlayer)
    {
        if (stringifiedPlayer == null || stringifiedPlayer.length() == 0)
        {
            return null;
        }
        String[] lines = stringifiedPlayer.split(" ");
        int currentExperience = Integer.parseInt(lines[0].substring(0, lines[0].indexOf('|')));
        int requiredExperience = Integer.parseInt(lines[0].substring(lines[0].indexOf('|') + 1));
        int gold = Integer.parseInt(lines[1]);
        int level = Integer.parseInt(lines[2]);
        int strength = Integer.parseInt(lines[3].substring(0, lines[3].indexOf('|')));
        int defense = Integer.parseInt(lines[3].substring(lines[3].indexOf('|') + 1));
        int curHP = Integer.parseInt(lines[4].substring(0, lines[4].indexOf('|')));
        int maxHP = Integer.parseInt(lines[4].substring(lines[4].indexOf('|') + 1));

        return new Player(
            maxHP,
            curHP,
            currentExperience,
            gold,
            level,
            strength,
            defense,
            requiredExperience
        );
    }
}
