package com.lchrislee.longjourney.model.items;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class Weapon extends UsableItem {
    private int attack;

    private Weapon(@NonNull String name, @NonNull String description, @DrawableRes int image, int attack) {
        super(name, description, image);
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }

    public static class Builder{
        private String name;
        private String description;
        private int attack;
        private int image;

        public @NonNull Builder name(@NonNull String name){
            this.name = name;
            return this;
        }

        public @NonNull Builder description(@NonNull String description){
            this.description = description;
            return this;
        }

        public @NonNull Builder attack(int attack){
            this.attack = attack;
            return this;
        }

        public @NonNull Builder image(@DrawableRes int image){
            this.image = image;
            return this;
        }

        public Weapon build(){
            return new Weapon(name, description, attack, image);
        }
    }
}
