package com.ooad.hw2.engine;

/**
 * Green type for collidable objects
 * it only be created with componentfactories
 */
public class Green extends Type{
    public Green() {
        super();
        this.color = Tile.Color.BLUE;
        this.strength = 75;
        this.agility = 100;
        this.health = 125;
    }
}
