package com.ooad.hw2.engine;

/**
 * Red type for collidable objects
 * it only be created with componentfactories
 */
public class Red extends Type{
    /**
     * Public constructor
     */
    public Red() {
        super();
        this.color = Tile.Color.RED;
        this.strength = 100;
        this.agility = 125;
        this.health = 75;
    }
}
