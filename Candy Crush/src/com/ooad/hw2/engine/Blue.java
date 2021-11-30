package com.ooad.hw2.engine;

/**
 * Blue type for collidable objects
 * it only be created with componentfactories
 */
public class Blue extends Type {
    /**
     * public constructor
     */
    public Blue() {
        super();
        this.color = Tile.Color.BLUE;
        this.strength = 125;
        this.agility = 75;
        this.health = 100;
    }
}
