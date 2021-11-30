package com.ooad.hw1.engine;

/**
 * B-Type Power Up
 * x5Multiplier decorator class extends PowerUpDecorator.
 * It's multiplier set to 5
 */
public class x5Multiplier extends PowerUpDecorator{

    /**
     * Public x5Multiplier constructor
     * @param x x-axis of the image
     * @param y y-axis of the image
     * @param speed speed of the image when it moves
     * @param height height of the image
     * @param width width of the image
     * @param imagePath image's path
     */
    public x5Multiplier(int x, int y, int speed, int height, int width, String imagePath) {
        super(x, y, speed, height, width, imagePath);
        this.setMultiplier(5);
    }

    @Override
    public String getDescription() {
        return "Type B: x5 Multiplier";
    }

    @Override
    public long calculateMultiplier() {
        return this.getMultiplier() * decorated.calculateMultiplier();
    }

    @Override
    protected void CollisionAction(Character character) {
        decorated = character.getPowerUp();
        character.setPowerUp(this);
        character.addLogMessage(getDescription() + " acquired");
    }
}
