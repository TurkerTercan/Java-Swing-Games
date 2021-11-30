package com.ooad.hw1.engine;

/**
 * PowerUp abstract class do certain power ups to the main character.
 * If main character collides with it, these methods take action.
 * It is decorator design pattern. This is above abstract class and there is JumpPowerUp and x1Multiplier concrete classes.
 * And to decorate this concrete classes there is PowerUpDecorator abstract class. And below that there is A, B, C type decorator classes
 */
public abstract class PowerUp extends Collidable{
    /**
     * Public PowerUp constructor
     * @param x x-axis of the image
     * @param y y-axis of the image
     * @param speed speed of the image when it moves
     * @param height height of the image
     * @param width width of the image
     * @param imagePath image's path
     */
    public PowerUp(int x, int y, int speed, int height, int width, String imagePath) {
        super(x, y, speed, height, width, imagePath);
    }

    private long multiplier;

    /**
     * To calculate multiplied powerUps, i used decorator design pattern. Every decorator objects has decorator object that is collected before it.
     * So, it does recursively multiply decorators multipliers.
     * @return current multiplier
     */
    public abstract long calculateMultiplier();

    /**
     * Returns current decorator's multiplier.
     * @return current decorator's multiplier.
     */
    public long getMultiplier() {
        return multiplier;
    }

    /**
     * Return a description and type of the powerUp
     * @return String description
     */
    public abstract String getDescription();

    /**
     * Setter for multiplier,
     * @param multiplier new multiplier.
     */
    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
