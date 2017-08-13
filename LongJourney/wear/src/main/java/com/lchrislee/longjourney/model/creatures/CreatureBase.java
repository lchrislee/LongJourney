package com.lchrislee.longjourney.model.creatures;

import com.lchrislee.longjourney.model.LongJourneyBaseModel;

class CreatureBase extends LongJourneyBaseModel {
    private int maxHealth;
    private int currentHealth;
    private int currentExperience;
    private int goldCarried;

    private int level;
    private int strength;
    private int defense;

    private CreatureBase()
    {

    }

    CreatureBase(
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

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getCurrentExperience() {
        return currentExperience;
    }

    public int getGoldCarried() {
        return goldCarried;
    }

    int getLevel() {
        return level;
    }

    int getStrength() {
        return strength;
    }

    int getDefense() {
        return defense;
    }

    void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    protected void setCurrentExperience(int currentExperience) {
        this.currentExperience = currentExperience;
    }

    void setGoldCarried(int goldCarried) {
        this.goldCarried = goldCarried;
    }

    protected void setLevel(int level) {
        this.level = level;
    }

    void setStrength(int strength) {
        this.strength = strength;
    }

    void setDefense(int defense) {
        this.defense = defense;
    }
}
