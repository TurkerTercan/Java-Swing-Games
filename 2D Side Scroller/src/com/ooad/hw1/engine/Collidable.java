package com.ooad.hw1.engine;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Collidable class that will enable colliding with another
 * Collidable objects, and it extends from Sprite class
 */
public abstract class Collidable extends Sprite{
    private final Image image;
    private final int height;
    private final int width;

    /**
     * Public constructor for Collidable object
     * @param x x-axis location in the screen
     * @param y y-axis location in the screen
     * @param speed speed of the object when it is moving towards left
     * @param height pixel height of the image
     * @param width pixel width of the image
     * @param imagePath The image's location in your system
     */
    public Collidable(int x, int y, int speed, int height, int width, String imagePath) {
        super(x, y, speed);
        this.height = height;
        this.width = width;
        this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath))).getImage();
    }

    @Override
    protected void draw(Graphics2D g2D) {
        g2D.drawImage(this.image, getX(), getY(), null);
    }

    /**
     * When collision happens there must be some actions to take on.
     * Implement this method to do what function you want to enable on Character object
     * @param character object that collided with this current object.
     */
    protected abstract void CollisionAction(Character character);

    /**
     * Getter for image's pixel height
     * @return image's pixel height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter for image's pixel width
     * @return image's pixel width
     */
    public int getWidth() {
        return width;
    }
}
