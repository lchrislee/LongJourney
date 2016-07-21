package com.lchrislee.longjourney.model.actors;

import android.support.annotation.NonNull;

import com.lchrislee.longjourney.model.items.Weapon;

/**
 * Created by Chris Lee on 7/15/16.
 */
public abstract class Actor {

    private Weapon weapon;
    private long level;
    private int maxHealth;
    private int currentHealth;
    private long gold;
    private int strength;
    private int defense;
    private int xp;

    Actor(long level, int maxHealth, int currentHealth, long gold, int strength, int defense, int xp) {
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.gold = gold;
        this.level = level;
        this.strength = strength;
        this.defense = defense;
        this.xp = xp;
        this.currentHealth = this.maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void changeCurrentHealthBy(int change) {
        this.currentHealth += change;
        if (this.currentHealth > this.maxHealth){
            this.currentHealth = this.maxHealth;
        }
    }

    public long getGold() {
        return gold;
    }

    public long getLevel() {
        return level;
    }

    void increaseLevelBy(long levelIncrease){
        this.level += levelIncrease;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefense() {
        return defense;
    }

    public int getXp() {
        return xp;
    }

    void increaseXpBy(int change) {
        this.xp += change;
    }

    public @NonNull Weapon getWeapon() {
        return weapon;
    }

    void setWeapon(@NonNull Weapon weapon) {
        this.weapon = weapon;
    }

    protected abstract void buildWeapon();

    public int getTotalDamage(){
        return strength + weapon.getAttack();
    }

    public static abstract class Builder{
        long level;
        int health;
        int currentHealth;
        long gold;
        int strength;
        int defense;
        int xp;

        public Builder level(long level) {
            this.level = level;
            return this;
        }

        public Builder maxHealth(int maxHealth) {
            this.health = maxHealth;
            return this;
        }

        public Builder currentHealth(int currentHealth) {
            this.currentHealth = currentHealth;
            return this;
        }

        public Builder gold(long gold) {
            this.gold = gold;
            return this;
        }

        public Builder strength(int strength) {
            this.strength = strength;
            return this;
        }

        public Builder defense(int defense) {
            this.defense = defense;
            return this;
        }

        public Builder xp(int xp) {
            this.xp = xp;
            return this;
        }
    }
}
