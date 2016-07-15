package com.lchrislee.longjourney.model.items;

import android.support.annotation.NonNull;

/**
 * Created by Chris Lee on 7/15/16.
 */
public class Weapon extends UsableItem {
    private int attack;

    public Weapon(@NonNull String name, @NonNull String description, int attack) {
        super(name, description);
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }

    public static class Builder{
        private String name;
        private String description;
        private int attack;

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

        public Weapon build(){
            return new Weapon(name, description, attack);
        }
    }
}
