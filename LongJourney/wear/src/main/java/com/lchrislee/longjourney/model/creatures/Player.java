package com.lchrislee.longjourney.model.creatures;

import android.support.annotation.Nullable;

public class Player extends BaseCreature {

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
        this.currentHealth = currentHealth;
        this.experienceForNextLevel = experienceForNextLevel;
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
        builder.append(currentExperience()).append('|')
                .append(getExperienceForNextLevel()).append(' ');
        builder.append(goldCarried()).append(' ');
        builder.append(level()).append(' ');
        builder.append(strength()).append('|')
                .append(defense()).append(' ');
        builder.append(currentHealth()).append('|')
                .append(maxHealth());
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
