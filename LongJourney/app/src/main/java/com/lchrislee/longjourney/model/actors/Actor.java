package com.lchrislee.longjourney.model.actors;

import android.support.annotation.NonNull;

import com.lchrislee.longjourney.model.items.Weapon;

/**
 * Created by Chris Lee on 7/15/16.
 */
public abstract class Actor {

    private @NonNull Weapon weapon;
    private long level;
    private int maxHealth;
    private int currentHealth;
    private long gold;
    private long strength;
    private long defense;
    private int xp;

    public Actor(long level, int maxHealth, long gold, long strength, long defense, int xp) {
        this.maxHealth = maxHealth;
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

    protected void changeCurrentHealthBy(int change) {
        this.currentHealth += change;
    }

    public long getGold() {
        return gold;
    }

    public long getLevel() {
        return level;
    }

    protected void increaseLevelBy(long levelIncrease){
        this.level += levelIncrease;
        increaseStats();
    }

    public long getStrength() {
        return strength;
    }

    public long getDefense() {
        return defense;
    }

    public int getXp() {
        return xp;
    }

    protected void increaseXpBy(int change) {
        this.xp += change;
    }

    protected void increaseStats(){
        // TODO FILL
    }

    public @NonNull Weapon getWeapon() {
        return weapon;
    }

    protected void setWeapon(@NonNull Weapon weapon) {
        this.weapon = weapon;
    }

    protected abstract void buildWeapon();

    public static abstract class Builder{
        protected long level;
        protected int health;
        protected long gold;
        protected long strength;
        protected long defense;
        protected int xp;

        public Builder level(long level) {
            this.level = level;
            return this;
        }

        public Builder maxHealth(int maxHealth) {
            this.health = maxHealth;
            return this;
        }

        public Builder gold(long gold) {
            this.gold = gold;
            return this;
        }

        public Builder strength(long strength) {
            this.strength = strength;
            return this;
        }

        public Builder defense(long defense) {
            this.defense = defense;
            return this;
        }

        public Builder xp(int xp) {
            this.xp = xp;
            return this;
        }
    }
}
