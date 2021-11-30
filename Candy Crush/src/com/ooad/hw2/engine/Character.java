package com.ooad.hw2.engine;

import com.ooad.hw2.Helper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Character class is an implementation product for the abstract factory.
 * its components should be created with CharacterComponent factory which given by parameter within its constructor.
 */
public class Character extends Collidable{
    private final CharacterComponentFactory componentFactory;

    /**
     * Public constructor for
     * @param x x-axis location
     * @param y y-axis location
     * @param image_width image's width
     * @param image_height image's height
     * @param image image content
     * @param componentFactory CharacterComponentFactory to create its components
     */
    public Character(int x, int y, int image_width, int image_height, Image image, CharacterComponentFactory componentFactory) {
        super(x, y, image_width, image_height, image);
        this.componentFactory = componentFactory;
        createComponents();
        Logger.setSingleLog("New (" + this + ") is created");
    }

    @Override
    public void getDamage(Tile.Color hitColor) {
        double damage;
        if (type.color == hitColor)
            damage = (100 * Math.pow(((double)(type.strength) / (double)(type.agility)), 1.35 ));
        else if ((Math.abs(type.color.compareTo(hitColor))) == 1)
            damage = (200 * Math.pow(((double)(type.strength) / (double)(type.agility)), 1.35 ));
        else
            damage =  (50 * Math.pow(((double)(type.strength) / (double)(type.agility)), 1.35 ));
        String damageStr = String.format("%,.2f", damage);
        Logger.setSingleLog("" + hitColor + " tile is damaged "+ damageStr + " to the " + "Character: " + type.color);
        type.health = type.health - damage;

    }

    @Override
    public void createComponents() {
        this.type = componentFactory.createType();
        if (type instanceof Red) {
            this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.redPortrait))).getImage();
        }
        if (type instanceof Blue) {
            this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.bluePortrait))).getImage();
        }
        if (type instanceof Green) {
            this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.greenPortrait))).getImage();
        }
    }

    @Override
    public String toString() {
        return "Character: " + this.type.color;
    }
}
