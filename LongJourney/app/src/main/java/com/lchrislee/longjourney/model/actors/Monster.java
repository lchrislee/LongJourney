package com.lchrislee.longjourney.model.actors;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.items.Weapon;

import java.io.Serializable;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class Monster extends Actor implements Serializable{
    private @NonNull String name;
    private @NonNull String description;
    @DrawableRes private int image;

    private Monster(long level, int health, long gold,
                    long strength, long defense,
                    @NonNull String name, @NonNull String description,
                    @DrawableRes int image,
                    int xp) {
        super(level, health, gold, strength, defense, xp);
        this.name = name;
        this.description = description;
        this.image = image;
        buildWeapon();
    }

    public @NonNull String getName() {
        return name;
    }

    public @NonNull String getDescription() {
        return description;
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
        private String description;
        @DrawableRes private int image;

        public Builder name(@NonNull String name) {
            this.name = name;
            return this;
        }

        public Builder description(@NonNull String description) {
            this.description = description;
            return this;
        }

        public Builder image(@DrawableRes int image){
            this.image = image;
            return this;
        }

        public Monster build(){
            return new Monster(level, health, gold, strength, defense, name, description, image, xp);
        }

        public Monster buildDefault(){
            return new Monster(1, 0, 10, 1, 1, "Slime", "It's something.", R.drawable.slime, 10);
        }
    }
}
