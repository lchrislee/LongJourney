package com.lchrislee.longjourney.model.actors;

/**
 * Created by Chris Lee on 7/15/16.
 */
public abstract class Actor {
    private long level;
    private long health;
    private long gold;
    private long strength;
    private long defense;

    public Actor(long level, long health, long gold, long strength, long defense) {
        this.health = health;
        this.gold = gold;
        this.level = level;
        this.strength = strength;
        this.defense = defense;
    }

    public long getHealth() {
        return health;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getStrength() {
        return strength;
    }

    public void setStrength(long strength) {
        this.strength = strength;
    }

    public long getDefense() {
        return defense;
    }

    public void setDefense(long defense) {
        this.defense = defense;
    }
}
