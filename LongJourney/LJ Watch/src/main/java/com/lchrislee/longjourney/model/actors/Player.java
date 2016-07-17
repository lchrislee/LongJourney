package com.lchrislee.longjourney.model.actors;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.items.UsableItem;
import com.lchrislee.longjourney.model.items.Weapon;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class Player extends Actor implements Serializable{
    private ArrayList<UsableItem> items;
    private long stepCount;
    private long stepReference;
    private int xpNeeded;

    private Player(long level, int health, long gold, long strength, long defense, long stepCount, long stepReference, int xp) {
        super(level, health, gold, strength, defense, xp);
        this.stepCount = stepCount;
        this.stepReference = stepReference;
        items = new ArrayList<>();
        generateXpNeeded(getLevel());
        buildWeapon();
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

    public void increaseStepCountBy(long stepDifference) {
        this.stepCount += stepDifference;
    }

    public long getStepReference() {
        return stepReference;
    }

    public int getXpNeeded() {
        return xpNeeded;
    }

    private void generateXpNeeded(long level){
        if (level == 0){
            xpNeeded = 10;
        }else{
            generateXpNeeded(level - 1);
            xpNeeded += (int) level;
        }
    }

    public boolean gainXPAndCheckLevelUp(int xpGain){
        increaseXpBy(xpGain);
        if (getXp() >= getXpNeeded()){
            levelUp();
            return true;
        }
        return false;
    }

    private void levelUp(){
        increaseLevelBy(1);
        generateXpNeeded(getLevel());
        increaseStats();
    }

    protected void increaseStats() {
        // TODO FILL
    }

    @Override
    protected void buildWeapon() {
        Weapon.Builder builder = new Weapon.Builder();
        builder.name("Fists");
        builder.description("Your hands.");
        builder.attack(1);
        builder.image(R.drawable.rubber_chicken);
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
            return new Player(level, health, gold, strength, defense, stepReference, stepCount, xp);
        }
    }
}
