package com.lchrislee.longjourney.model.creatures;

import com.lchrislee.longjourney.model.BaseModel;

class BaseCreature extends BaseModel {
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
}
