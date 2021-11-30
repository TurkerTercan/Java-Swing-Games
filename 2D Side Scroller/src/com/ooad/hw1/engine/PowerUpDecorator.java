package com.ooad.hw1.engine;

/**
 * PowerUpDecorator class extended from PowerUp object.
 * Decorator pattern.
 * It has a PowerUp object to know which object did it decorated.
 */
public abstract class PowerUpDecorator extends PowerUp{
    /**
     * Public PowerUpDecorator constructor
     * @param x x-axis of the image
     * @param y y-axis of the image
     * @param speed speed of the image when it moves
     * @param height height of the image
     * @param width width of the image
     * @param imagePath image's path
     */
    public PowerUpDecorator(int x, int y, int speed, int height, int width, String imagePath) {
        super(x, y, speed, height, width, imagePath);
    }

    /**
     * Decorated PowerUp object
     */
    protected PowerUp decorated;
}
