package com.ooad.hw2.engine;

import com.ooad.hw2.Helper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Enemy class is an implementation product for the abstract factory.
 * its components should be created with CharacterComponent factory which given by parameter within its constructor.
 */
public class Enemy extends Collidable{
    private final EnemyComponentFactory componentFactory;
    private Style style;

    /**
     * Public constructor for
     * @param x x-axis location
     * @param y y-axis location
     * @param image_width image's width
     * @param image_height image's height
     * @param image image content
     * @param componentFactory CharacterComponentFactory to create its components
     */
    public Enemy(int x, int y, int image_width, int image_height, Image image, EnemyComponentFactory componentFactory) {
        super(x, y, image_width, image_height, image);
        this.componentFactory = componentFactory;
        createComponents();
        Logger.setSingleLog("New (" + this.toString() + ") is created");
    }

    @Override
    public void getDamage(Tile.Color hitColor) {
        double damage;
        if (type.color == hitColor)
            damage = (100 * Math.pow(((type.strength * style.strength_multiplier) / (type.agility * style.agility_multiplier)), 1.35) );
        else if ((Math.abs(type.color.compareTo(hitColor))) == 1) {
            damage = (200 * Math.pow(((type.strength * style.strength_multiplier) / (type.agility * style.agility_multiplier)), 1.35) );
        }
        else {
            damage = (50 * Math.pow(((type.strength * style.strength_multiplier) / (type.agility * style.agility_multiplier)), 1.35) );
        }
        String damageStr = String.format("%,.2f", damage);
        Logger.setSingleLog("" + hitColor + " tile is damaged " + damageStr + " to the " + "Enemy: " + type.color + " " + style.description);
        type.health = type.health - damage;
    }

    @Override
    public void createComponents() {
        this.style = componentFactory.createStyle();
        this.type = componentFactory.createType();
        if (type instanceof Red) {
            if (style instanceof Atlantis)
                this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.redAtlantis))).getImage();
            if (style instanceof Valhalla)
                this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.redValhalla))).getImage();
            if (style instanceof Underwild)
                this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.redUnderwild))).getImage();
        }
        if (type instanceof Blue) {
            if (style instanceof Atlantis)
                this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.blueAtlantis))).getImage();
            if (style instanceof Valhalla)
                this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.blueValhalla))).getImage();
            if (style instanceof Underwild)
                this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.blueUnderwild))).getImage();
        }
        if (type instanceof Green) {
            if (style instanceof Atlantis)
                this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.greenAtlantis))).getImage();
            if (style instanceof Valhalla)
                this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.greenValhalla))).getImage();
            if (style instanceof Underwild)
                this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.greenUnderwild))).getImage();
        }
    }

    @Override
    public String toString() {
        return "Enemy: " + type.color + " " + style.description;
    }
}
