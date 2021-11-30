package com.ooad.hw1.engine;

import com.ooad.hw1.utility.Helper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Character objects represents main character class in our game.
 * It has some variables that is used in game logic.
 * Also, it has two images. One of them is presented while you are not moving and the other one is presented while you are moving.
 * It has a concrete decorator object x1Multiplier powerUp that will help you to track of the multiplier powerups.
 * It has 3 health and default JumpBehavior is ShortJump
 * PS: There should be just one Character object in the game.
 */
public class Character extends Collidable {
    private final Image FaceImage, SideImage;
    private boolean isMoving = false;
    private boolean isJumping = false;
    private boolean JumpSignal = false;
    private JumpBehavior jumpBehavior = new ShortJump(false);
    private long score = 0;
    private int health = 3;
    private boolean isAlive = true;
    private PowerUp powerUp = new x1Multiplier();
    private final ArrayList<String> strings = new ArrayList<>();

    /**
     * I used an ArrayList to keep track of log messages that has been sent by Character object
     * Since almost every log message is sent from this class, I located here.
     * @return ArrayList of log messages
     */
    public ArrayList<String> getStrings() {
        return strings;
    }


    @Override
    protected void draw(Graphics2D g2D) {
        if (isMoving)
            g2D.drawImage(this.SideImage, getX(), getY(), null);
        else
            g2D.drawImage(this.FaceImage, getX(), getY(), null);
    }

    @Override
    protected void CollisionAction(Character character) {

    }

    /**
     * Getter for PowerUp object. It is used for decorating the PowerUp.
     * @return current PowerUp
     */
    public PowerUp getPowerUp() {
        return powerUp;
    }

    /**
     * Setter for PowerUp object. Used for to set new decorated object.
     * @param newPowerUp new decorated PowerUp object
     */
    public void setPowerUp(PowerUp newPowerUp) {
        this.powerUp = newPowerUp;
    }

    /**
     * When Character objects passes successfully an enemy, it will be rewarded with a score.
     * This method recursively calculates all multipliers that inside of the decorated multiplier object,
     * and adds this multiplier to the score.
     */
    public void increaseScore() {
        long newScore = powerUp.calculateMultiplier();
        score += newScore;
        addLogMessage("Player earned " + newScore + " points.");
    }

    /**
     * Getter for current score of Character object
     * @return current score
     */
    public long getScore() {
        return score;
    }

    /**
     * Method for adding log messages to ArrayList of messages.
     * If ArrayList's size is greater or equal to 10, it removes last message that has been added,
     * and adds new message.
     * @param message Log message
     */
    public void addLogMessage(String message) {
        if (strings.size() >= 10) {
            strings.remove(0);
        }
        strings.add(message);
    }

    /**
     * Decreases health of the Character object.
     * If its health equal to 0, stops the game.
     */
    public void decreaseHealth() {
        health--;
        addLogMessage("Collided with an enemy. Decrease health.");
        if (health <= 0) {
            addLogMessage("Your Score: " + score);
            addLogMessage("GAME OVER! YOU MAY RESTART THE GAME.");
            isAlive = false;
        }
    }

    /**
     * Getter for isAlive. Used to check game is over or not.
     * @return current status of isAlive
     */
    public boolean isCharacterAlive() {
        return isAlive;
    }

    /**
     * Getter for health.
     * @return current health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Method for checking if two object is intersected or not.
     * @param collidable object to check if it collided with this object.
     * @return If this object and parameter object is intersected or not.
     */
    public boolean isCollided(Collidable collidable) {
        if (collidable.getX() > this.getX()) {
            return collidable.getX() - this.getX() < Helper.girlWidth && this.getY() + Helper.girlHeight > collidable.getY();
        } else {
            return this.getX() - collidable.getX() < collidable.getWidth() && this.getY() + Helper.girlHeight > collidable.getY();
        }
    }

    /**
     * Jump logic of the Character
     */
    public void jump() {
        if (this.getY() == Helper.CHAR_START_Y_LOC && !isJumping) {
            isJumping = true;
            if (jumpBehavior instanceof ShortJump)
                addLogMessage("Character is short jumping now.");
            else
                addLogMessage("Character is long jumping now.");
        }
        else if (this.getY() >= Helper.CHAR_START_Y_LOC && isJumping) {
            this.setY(Helper.CHAR_START_Y_LOC);
            isJumping = false;
            addLogMessage("Jump finished.");
        }
        if (isJumping)
            jumpBehavior.jump(this);
    }

    /**
     * Public constructor for Character class
     * @param x x-axis location of the image
     * @param y y-axis location of the image
     * @param speed speed of the object when it moves
     */
    public Character(int x, int y, int speed) {
        super(x, y, speed, Helper.girlHeight, Helper.girlWidth, Helper.girlFace);
        this.FaceImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.girlFace))).getImage();
        this.SideImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.girlSide))).getImage();
    }

    /**
     *  Getter for isMoving variable
     * @return If the character is currently moving or not
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Setter for isMoving variable
     * @param moving new moving status
     */
    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    /**
     * Getter for JumpBehavior
     * @return current JumpBehavior
     */
    public JumpBehavior getJumpBehavior() {
        return jumpBehavior;
    }

    /**
     * Setter for JumpBehavior
     * @param jumpBehavior new JumpBehavior
     */
    public void setJumpBehavior(JumpBehavior jumpBehavior) {
        this.jumpBehavior = jumpBehavior;
        addLogMessage(jumpBehavior.getDescription() + " is new jump behavior.");
    }

    /**
     * Getter for isJumping
     * @return current jumping status
     */
    public boolean isJumping() {
        return isJumping;
    }

    /**
     * Getter for JumpSignal
     * @return JumpSignal
     */
    public boolean isJumpSignal() {
        return JumpSignal;
    }

    /**
     * Setter for JumpSignal
     * @param jumpSignal new JumpSignal status
     */
    public void setJumpSignal(boolean jumpSignal) {
        JumpSignal = jumpSignal;
    }

    /**
     * Used to generate new game, it resets all the variables.
     */
    public void resetScore() {
        strings.clear();
        strings.add("New Game started.");
        score = 0;
        isMoving = false;
        isJumping = false;
        isAlive = true;
        this.jumpBehavior = new ShortJump(false);
        health = 3;
        JumpSignal = false;
        setY(Helper.CHAR_START_Y_LOC);
        powerUp = new x1Multiplier();
    }

}
