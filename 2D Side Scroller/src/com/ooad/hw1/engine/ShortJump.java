package com.ooad.hw1.engine;

import com.ooad.hw1.utility.Helper;

/**
 * ShortJump simply does make main character jump Shorter.
 * Character jumps 160 pixel and after that it falls.
 */
public class ShortJump implements JumpBehavior{

    /**
     * Public constructor for ShortJump
     * @param rising If main character intersects with D-Type PowerUp while its jumping.
     *               The other generated object wouldn't know it should jump or fall.
     *               So, I managed it with this boolean
     */
    public ShortJump(boolean rising) {
        this.rising = rising;
    }
    private boolean rising;
    @Override
    public void jump(Sprite sprite) {
        int howMuchPixelCanJump = 160;
        int currentHeight = sprite.getY();
        if (currentHeight <= Helper.CHAR_START_Y_LOC - howMuchPixelCanJump)
            rising = false;
        if (!rising && currentHeight == Helper.CHAR_START_Y_LOC)
            rising = true;
        if (rising)
            sprite.setY(sprite.getY() - raiseSpeed);
        else
            sprite.setY(sprite.getY() + fallSpeed);
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
        return "Short Jump";
    }
}
