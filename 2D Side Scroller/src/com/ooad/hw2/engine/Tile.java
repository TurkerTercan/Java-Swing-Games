package com.ooad.hw2.engine;

import com.ooad.hw2.Helper;

import javax.swing.*;
import java.awt.*;


/**
 * Tile objects helps to represent a tile in our board.
 * Every tile it has own color. If you match 3 tiles in a row, they will dispatch from the board and, they'll cause damage to collidable objects.
 */
public class Tile{
    /**
     * Enum class to represent colors
     */
    public enum Color{
        RED,
        BLUE,
        GREEN,
    }

    private final Sprite sprite;
    private final Color color;

    /**
     * Public constructor for Tile class
     *
     * @param x            x-axis of image that you want to locate
     * @param y            y-axis of image that you want to locate
     * @param image_width  image width
     * @param image_height image height
     */
    public Tile(int x, int y, int image_width, int image_height, Color color) {
        this.color = color;
        if (color == Color.RED) {
            sprite = new Sprite(x, y, image_width, image_height,
                    new ImageIcon("C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\redPotionSprite.png").getImage());
        } else if (color == Color.BLUE) {
            sprite = new Sprite(x, y, image_width, image_height,
                    new ImageIcon("C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\diamondSprite.png").getImage());
        } else {
            sprite = new Sprite(x, y, image_width, image_height,
                    new ImageIcon("C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\leafSprıte.png").getImage());
        }
    }

    /**
     * To draw the sprite within the tile class
     * @param g2D Graphics2D to render the sprite
     */
    public void draw(Graphics2D g2D) {
        sprite.draw(g2D);
    }

    /**
     * IsMatched method controls the selection of the tiles if a mouse input click signal arrives
     * @param x x-location of the mouse input
     * @param y y-location of the mouse input
     * @return is selected or not
     */
    public boolean isMatched(int x, int y) {
        return (x >= sprite.getX() && x <= sprite.getX() + sprite.getImage_width()) && (y >= sprite.getY() && y <= sprite.getY() + sprite.getImage_height());
    }

    /**
     * To animate the tiles to the desired row or column
     * @param x_dimension desired column
     * @param y_dimension desired row
     */
    public void moveTile(int x_dimension, int y_dimension) {
        if (x_dimension != 0)
            sprite.setX(sprite.getX() + (x_dimension * Helper.tileMoveSpeed));
        if (y_dimension != 0)
            sprite.setY(sprite.getY() + (y_dimension * Helper.tileMoveSpeed));
    }

    /**
     * When the tiles should be move up to the first empty tile or new generated tiles should be animated
     * to the empty tiles, this method is used.
     * @param rowCount which row this tile should be
     * @return is animation finished
     */
    public boolean moveToSpecificRow(int rowCount) {
        if (sprite.getY() > Helper.PIXEL_LOC[rowCount * 9][1]) {
            sprite.setY(sprite.getY() - Helper.tileMoveSpeed);
            return true;
        }
        return false;
    }

    /**
     * To animate shuffle in our board this method is used. It animates the sprite to the desired row and column
     * @param column desired column
     * @param row desired row
     * @return is animation finished
     */
    public boolean moveToSpecificColumnAndRow(int column, int row) {
        boolean y_anim = false, x_anim = false;
        if (sprite.getY() != Helper.PIXEL_LOC[row * 9 + column][1]) {
            if (sprite.getY() > Helper.PIXEL_LOC[row * 9 + column][1])
                sprite.setY(sprite.getY() - Helper.tileMoveSpeed);
            else
                sprite.setY(sprite.getY() + Helper.tileMoveSpeed);
            y_anim = true;
        }
        if (sprite.getX() != Helper.PIXEL_LOC[row * 9 + column][0]) {
            if (sprite.getX() > Helper.PIXEL_LOC[row * 9 + column][0])
                sprite.setX(sprite.getX() - Helper.tileMoveSpeed);
            else
                sprite.setX(sprite.getX() + Helper.tileMoveSpeed);
            x_anim = true;
        }
        return y_anim || x_anim;
    }

    /**
     * Getter for sprite
     * @return sprite
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Getter for color
     * @return tile's color
     */
    public Color getColor() {
        return color;
    }
}
