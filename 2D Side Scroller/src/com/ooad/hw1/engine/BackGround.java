package com.ooad.hw1.engine;

import com.ooad.hw1.utility.Helper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Background object extended from Sprite
 * that will display image of the selected background from assets folder
 */
public class BackGround extends Sprite{
    private final Image image;

    /**
     * Public constructor for Background object
     * @param x image's x-axis location
     * @param y image's y-axis location
     * @param speed speed of the object when it moves
     */
    public BackGround(int x, int y, int speed) {
        super(x, y, speed);
        this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.backgroundPath))).getImage();
    }

    @Override
    protected void draw(Graphics2D g2D) {
        g2D.drawImage(this.image, getX(), getY(), null);
    }
}
