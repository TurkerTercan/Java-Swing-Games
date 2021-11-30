package com.ooad.hw1.engine;

import com.ooad.hw1.utility.Helper;

/**
 * LongJump simply does make main character jump longer.
 * Character jumps 240 pixel and after that it falls.
 */
public class LongJump implements JumpBehavior{
    /**
     * Public constructor for LongJump
     * @param rising If main character intersects with D-Type PowerUp while its jumping.
     *               The other generated object wouldn't know it should jump or fall.
     *               So, I managed it with this boolean
     */
    public LongJump(boolean rising) {
        this.rising = rising;
    }

    private boolean rising;
    @Override
    public void jump(Sprite sprite) {
        int howMuchPixelCanJump = 240;
        int currentHeight = sprite.getY();
        if (currentHeight <= Helper.CHAR_START_Y_LOC - howMuchPixelCanJump)
            rising = false;
        if (!rising && currentHeight == Helper.CHAR_START_Y_LOC)
            rising = true;
        if (rising)
            sprite.setY(sprite.getY() - raiseSpeed * 2);
        else
            sprite.setY(sprite.getY() + fallSpeed * 2);
    }

    /**
     * Getter for rising variable
     * @return rising
     */
    public boolean isRising() {
        return rising;
    }

    @Override
    public String getDescription() {
        return "Long Jump";
    }
}
