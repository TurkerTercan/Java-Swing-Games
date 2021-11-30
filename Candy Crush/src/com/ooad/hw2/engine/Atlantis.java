package com.ooad.hw2.engine;

/**
 * Atlantis style for collidable objects
 * it only be created with componentfactories
 */
public class Atlantis extends Style{
    /**
     * public constructor
     */
    public Atlantis() {
        super();
        description = "Atlantis";
        strength_multiplier = 0.8;
        agility_multiplier = 1.2;
        health_multiplier = 0.8;
    }
}
