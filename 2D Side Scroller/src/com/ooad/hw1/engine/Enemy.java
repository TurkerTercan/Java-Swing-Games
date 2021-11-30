package com.ooad.hw1.engine;

/**
 * Enemy class used to represent obstacles/monsters.
 * It extends Collidable class, so it can be collided with any other collidable objects.
 */
public class Enemy extends Collidable{
    private boolean collected = false;

    /**
     * Public constructor for Enemy object
     * @param x image's x-axis location
     * @param y image's x-axis location
     * @param speed speed of the object when it moves
     * @param height pixel height of the image
     * @param width pixel width of the image
     * @param imagePath image's path
     */
    public Enemy(int x, int y, int speed, int height, int width, String imagePath) {
        super(x, y, speed, height, width, imagePath);
    }


    @Override
    protected void CollisionAction(Character character) {
        character.decreaseHealth();
    }


    /**
     * Getter for collected variable. Used to check if the main character collected it's score from this object.
     * @return collected boolean
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * Setter for collected variable. When main character collects score from this object, it is set to false.
     * @param collected new collected status
     */
    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
