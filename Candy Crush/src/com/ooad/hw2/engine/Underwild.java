package com.ooad.hw2.engine;

/**
 * Underwild style for collidable objects
 * it only be created with componentfactories
 */
public class Underwild extends Style{
    /**
     * Public constructor
     */
    public Underwild() {
        super();
        description = "Underwild";
        strength_multiplier = 0.8;
        agility_multiplier = 1.2;
        health_multiplier = 0.8;
    }
}
