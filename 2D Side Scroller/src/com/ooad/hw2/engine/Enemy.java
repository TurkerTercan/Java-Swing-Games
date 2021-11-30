package com.ooad.hw2.engine;

import com.ooad.hw2.Helper;

import javax.swing.*;
import java.awt.*;

/**
 * Enemy class is an implementation product for the abstract factory.
 * its components should be created with CharacterComponent factory which given by parameter within its constructor.
 */
public class Enemy extends Collidable{
    private final EnemyComponentFactory componentFactory;

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
    }

    @Override
    public void createComponents() {
        this.style = componentFactory.createStyle();
        this.type = componentFactory.createType();
        if (type instanceof Red) {
            if (style instanceof Atlantis)
                this.image = new ImageIcon(Helper.redAtlantis).getImage();
            if (style instanceof Valhalla)
                this.image = new ImageIcon(Helper.redValhalla).getImage();
            if (style instanceof Underwild)
                this.image = new ImageIcon(Helper.redUnderwild).getImage();
        }
        if (type instanceof Blue) {
            if (style instanceof Atlantis)
                this.image = new ImageIcon(Helper.blueAtlantis).getImage();
            if (style instanceof Valhalla)
                this.image = new ImageIcon(Helper.blueValhalla).getImage();
            if (style instanceof Underwild)
                this.image = new ImageIcon(Helper.blueUnderwild).getImage();
        }
        if (type instanceof Green) {
            if (style instanceof Atlantis)
                this.image = new ImageIcon(Helper.greenAtlantis).getImage();
            if (style instanceof Valhalla)
                this.image = new ImageIcon(Helper.greenValhalla).getImage();
            if (style instanceof Underwild)
                this.image = new ImageIcon(Helper.greenUnderwild).getImage();
        }
    }
}
