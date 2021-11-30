package com.ooad.hw1.engine;

import com.ooad.hw1.utility.Helper;

import java.awt.*;

/**
 * Abstract class used for keeping track of images
 * ,setting their locations and drawing images
 */
public abstract class Sprite {
    private int x;
    private int y;
    private final int speed;
    private boolean drawable = true;

    /**
     * It used to draw images and string to the screen.
     * All inherited classes should implement draw method since it is abstract,
     * and draw conditions are differs from each other.
     * @param g2D Graphics2D object that will help to draw images, string to the panel.
     */
    protected abstract void draw(Graphics2D g2D);

    /**
     * Used to change x-axis for an image with a speed int
     * There is just moveLeft method since all the objects
     * should be moveLeft except main character because this game is
     * 2D side scroller game
     */
    public void moveLeft() {
        x = x - speed;
    }

    /**
     * Public constructor for Sprite class
     * @param x x-axis of image that you want to locate
     * @param y y-axis of image that you want to locate
     * @param speed pixel speed of the image
     */
    public Sprite(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    /**
     * Getter for x value
     * @return Sprite's x-axis location
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for x value
     * @param x Sprite's new x-axis location
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for y value
     * @return Sprite's y-axis location
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for y value
     * @param y Sprite's new y-axis location
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * To check if the image drawable or not
     * @return current drawable boolean of the object
     */
    public boolean isDrawable() {
        return drawable;
    }

    /**
     * To set image's drawable status
     * @param drawable new drawable status of the object
     */
    public void setDrawable(boolean drawable) {
        this.drawable = drawable;
    }

}
