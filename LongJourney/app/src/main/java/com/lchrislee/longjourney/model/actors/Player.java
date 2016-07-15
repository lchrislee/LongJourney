package com.lchrislee.longjourney.model.actors;

import android.support.annotation.NonNull;

import com.lchrislee.longjourney.model.items.UsableItem;
import com.lchrislee.longjourney.model.items.Weapon;

import java.util.ArrayList;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class Player extends Actor {
    private ArrayList<UsableItem> items;
    private long stepCount;
    private long stepReference;

    public Player(long level, long health, long gold, long strength, long defense, long stepCount, long stepReference) {
        super(level, health, gold, strength, defense);
        this.stepCount = stepCount;
        this.stepReference = stepReference;
        items = new ArrayList<>();
    }

    public ArrayList<UsableItem> getItems() {
        return items;
    }

    public void addItem(UsableItem inItem){
        items.add(inItem);
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

    @Override
    protected void buildWeapon() {
        Weapon.Builder builder = new Weapon.Builder();
        builder.name("Fists");
        builder.description("Your hands.");
        builder.attack(1);
        setWeapon(builder.build());
    }


    public static class Builder extends Actor.Builder{
        private long stepCount;
        private long stepReference;

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
