package com.ooad.hw2.engine;
import com.ooad.hw2.Helper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Abstract class used for keeping track of images
 * ,setting their locations and drawing images
 */
public class Sprite {
    private int x;
    private int y;
    private final int image_width, image_height;
    protected Image image;

    /**
     * It used to draw images and string to the screen.
     * @param g2D Graphics2D object that will help to draw images, string to the panel.
     */
    protected void draw(Graphics2D g2D) {
        if (image != null)
            g2D.drawImage(image, x, y, null);
    }

    /**
     * Public constructor for Sprite class
     * @param x x-axis of image that you want to locate
     * @param y y-axis of image that you want to locate
     * @param image image of the object
     */
    public Sprite(int x, int y, int image_width, int image_height, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.image_height = image_height;
        this.image_width = image_width;
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

    public int getImage_width() {
        return image_width;
    }

    public int getImage_height() {
        return image_height;
    }

    public boolean mouseDetectionForMouseInput(int x, int y) {
        return (x >= getX() && x <= getX() + getImage_width() && y >= getY() && y <= getY() + getImage_height());
    }
}
