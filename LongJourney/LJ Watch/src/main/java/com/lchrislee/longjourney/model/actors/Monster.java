package com.lchrislee.longjourney.model.actors;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.items.Weapon;
import com.lchrislee.longjourney.utility.ActorUtility;

import java.io.Serializable;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class Monster extends Actor implements Serializable{
    private @NonNull String name;
    @DrawableRes private int image;

    private Monster(long level, int health, long gold,
                    int strength, int defense,
                    @NonNull String name,
                    @DrawableRes int image,
                    int xp) {
        super(level, health, gold, strength, defense, xp);
        this.name = name;
        this.image = image;
        buildWeapon();
    }

    public @NonNull String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    @Override
    protected void buildWeapon() {
        Weapon.Builder builder = new Weapon.Builder();
        builder.name("Fists");
        builder.description("Your hands.");
        builder.attack(2);
        setWeapon(builder.build());
    }

    public static class Builder extends Actor.Builder{

        private String name;
        @DrawableRes private int image;

        public Builder name(@NonNull String name) {
            this.name = name;
            return this;
        }

        public @NonNull Builder image(@DrawableRes int image){
            this.image = image;
            return this;
        }

        public @NonNull Monster build(){
            return new Monster(level, health, gold, strength, defense, name, image, xp);
        }

        public static @NonNull Monster build(@NonNull Player p, int monsterType){
            int health = 0, image = 0, xp = 0, strength = 0, defense = 0;
            long level = 0, gold = 0;
            String name = "";
            switch(monsterType){
                case ActorUtility.MONSTER_TYPE_SLIME:
                    level = (long)(1.5 * Math.random() * p.getLevel()) + 1;
                    health = (int)(1.1 * Math.random() * level + 5);
                    image = R.drawable.slime;
                    xp = (int)(0.25 * health);
                    gold = (int)(0.1 * Math.random() * level) + 1;
                    strength = (int) (0.05 * Math.random() * level);
                    defense = (int) (0.05 * Math.random() * level);
                    name = "Slime";
                    break;
                case ActorUtility.MONSTER_TYPE_GOBLIN:
                    level = (long)(1.75 * Math.random() * p.getLevel()) + 1;
                    health = (int)(1.25 * Math.random() * level + 10);
                    image = R.drawable.goblin;
                    xp = (int)(0.4 * health);
                    gold = (int)(0.3 * Math.random() * level) + 1;
                    name = "Goblin";
                    strength = (int) (0.175 * Math.random() * level);
                    defense = (int) (0.2 * Math.random() * level);
                    break;
                case ActorUtility.MONSTER_TYPE_TROLL:
                    level = (long)(2.0 * Math.random() * p.getLevel()) + 1;
                    health = (int)(1.5 * Math.random() * level + 20);
                    image = R.drawable.troll;
                    xp = (int)(0.65 * health);
                    gold = (int)(1.2 * Math.random() * level) + 1;
                    name = "Troll";
                    strength = (int) (0.3 * Math.random() * level);
                    defense = (int) (0.3 * Math.random() * level);
                    break;
            }
            return new Monster(level, health, gold, strength, defense, name, image, xp);
        }

        public static @NonNull Monster buildDefault(){
            return new Monster(1, 0, 10, 1, 1, "Slime", R.drawable.slime, 10);
        }

    }
}
