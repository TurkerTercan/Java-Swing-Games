package com.ooad.hw1.engine;

/**
 * A-Type Power Up
 * x2Multiplier decorator class extends PowerUpDecorator.
 * It's multiplier set to 2
 */
public class x2Multiplier extends PowerUpDecorator{

    /**
     * Public x2Multiplier constructor
     * @param x x-axis of the image
     * @param y y-axis of the image
     * @param speed speed of the image when it moves
     * @param height height of the image
     * @param width width of the image
     * @param imagePath image's path
     */
    public x2Multiplier(int x, int y, int speed, int height, int width, String imagePath) {
        super(x, y, speed, height, width, imagePath);
        this.setMultiplier(2);
    }

    @Override
    public String getDescription() {
        return "Type A: x2 Multiplier";
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
