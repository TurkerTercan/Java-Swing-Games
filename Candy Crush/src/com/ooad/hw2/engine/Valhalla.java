package com.ooad.hw2.engine;

/**
 * Valhalla style for collidable objects
 * it only be created with componentfactories
 */
public class Valhalla extends Style{
    /**
     * Public constructor
     */
    public Valhalla() {
        super();
        description = "Valhalla";
        strength_multiplier = 1.3;
        agility_multiplier = 0.4;
        health_multiplier = 1.3;
    }
}
