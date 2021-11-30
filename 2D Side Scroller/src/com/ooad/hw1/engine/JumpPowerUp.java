package com.ooad.hw1.engine;

/**
 * D Type PowerUp
 * Extends from PowerUp object, but it doesn't have any multiplier. It is set to 1.
 * It is simply changes the JumpBehavior of character
 */
public class JumpPowerUp extends PowerUp{

    /**
     * Public constructor for JumpBehavior
     * @param x x-axis location of the image
     * @param y y-axis location of the image
     * @param speed speed of the object when it moves
     * @param height image's pixel height
     * @param width image's pixel width
     * @param imagePath image's path
     */
    public JumpPowerUp(int x, int y, int speed, int height, int width, String imagePath) {
        super(x, y, speed, height, width, imagePath);
        this.setMultiplier(1);
    }

    @Override
    public String getDescription() {
        return "Type D: Jump Changer PowerUp";
    }

    @Override
    public long calculateMultiplier() {
        return this.getMultiplier();
    }

    @Override
    protected void CollisionAction(Character character) {
        JumpBehavior jumpBehavior = character.getJumpBehavior();
        character.addLogMessage(getDescription() + " acquired");
        if (jumpBehavior instanceof ShortJump)
            character.setJumpBehavior(new LongJump(((ShortJump) jumpBehavior).isRising()));
        else if (jumpBehavior instanceof LongJump)
            character.setJumpBehavior(new ShortJump(((LongJump) jumpBehavior).isRising()));
    }

}
