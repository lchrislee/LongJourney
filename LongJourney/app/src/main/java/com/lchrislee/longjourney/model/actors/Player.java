package com.lchrislee.longjourney.model.actors;

import com.lchrislee.longjourney.model.items.UsableItem;
import com.lchrislee.longjourney.model.items.Weapon;

import java.util.ArrayList;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class Player extends Actor {
    private ArrayList<UsableItem> items;
    private Weapon weapon;
    private long stepCount;
    private long stepReference;

    public Player(long level, long health, long gold, long strength, long defense, long stepCount, long stepReference) {
        super(level, health, gold, strength, defense);
        this.stepCount = stepCount;
        this.stepReference = stepReference;
    }

    public ArrayList<UsableItem> getItems() {
        return items;
    }

    public void addItem(UsableItem inItem){
        items.add(inItem);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public long getStepCount() {
        return stepCount;
    }

    public void increaseStepCount(long stepDifference) {
        this.stepCount += stepDifference;
    }

    public long getStepReference() {
        return stepReference;
    }

    public void setStepReference(long stepReference) {
        this.stepReference = stepReference;
    }

    public static class Builder{
        private long level;
        private long health;
        private long gold;
        private long strength;
        private long defense;
        private long stepCount;
        private long stepReference;

        public Builder level(long level) {
            this.level = level;
            return this;
        }

        public Builder health(long health) {
            this.health = health;
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

        public Builder stepCount(long stepCount) {
            this.stepCount = stepCount;
            return this;
        }

        public Builder stepReference(long stepReference) {
            this.stepReference = stepReference;
            return this;
        }

        public Player build(){
            return new Player(level, health, gold, strength, defense, stepReference, stepCount);
        }
    }
}
