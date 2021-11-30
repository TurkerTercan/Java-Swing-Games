package com.ooad.hw2.engine;

import java.awt.*;

/**
 * Collidable class is an super class of products.
 * It can be thought like pizza in the Head-First Design Patterns book (Abstract Factory)
 */
public abstract class Collidable extends Sprite{
    protected Type type;

    /**
     * Public constructor for collidable object. It instantiates sprite
     * @param x x-axis location
     * @param y y-axis location
     * @param image_width image's width size
     * @param image_height image's height size
     * @param image image content
     */
    public Collidable(int x, int y, int image_width, int image_height, Image image) {
        super(x, y, image_width, image_height, image);
    }

    /**
     * GetDamage method is used to decrease characters' and enemies' health.
     * @param hitColor HitColor is representing what color is collided with our object. It'll cause damage accordingly.
     */
    public abstract void getDamage(Tile.Color hitColor);

    /**
     * Checking for collidable is dead or not
     * @return health is below zero or not
     */
    public boolean isDead() {
        return this.type.health <= 0;
    }

    /**
     * It's used to create components of the object like Type and Style. Further implementations can be added to this method.
     * (like prepare method in pizza class in our course.)
     */
    public abstract void createComponents();

    @Override
    public abstract String toString();

}
