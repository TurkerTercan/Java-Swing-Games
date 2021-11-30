package com.ooad.hw2.engine;

import java.awt.*;

/**
 * Collidable class is an super class of products.
 * It can be thought like pizza in the Head-First Design Patterns book (Abstract Factory)
 */
public abstract class Collidable extends Sprite{
    protected Type type;
    protected Style style;

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
    public void getDamage(Tile.Color hitColor) {
        if (type.color == hitColor)
            type.health = type.health - (100 * ((type.strength * style.strength_multiplier) / (type.agility * style.agility_multiplier)) * 1.35 );
        else if ((Math.abs(type.color.compareTo(hitColor))) == 1) {
            type.health = type.health - (200 * ((type.strength * style.strength_multiplier) / (type.agility * style.agility_multiplier)) * 1.35 );
        }
        else {
            type.health = type.health - (50 * ((type.strength * style.strength_multiplier) / (type.agility * style.agility_multiplier)) * 1.35 );
        }
    }

    /**
     * Checking for collidable is dead or not
     * @return health is below zero or not
     */
    public boolean isDead() {
        return this.type.health <= 0;
    }

    /**
     * It's used to create components of the object like Type and Style. Further implementations can be added to this method.
     */
    public abstract void createComponents();

    /**
     * Getter method to return collidable objects type's objects color
     * @return type.color
     */
    public Tile.Color getColor() {
        return type.color;
    }

    /**
     * Getter method to return collidable objects style's description
     * @return style.description
     */
    public String getStyleDescription() {
        return style.description;
    }

}
