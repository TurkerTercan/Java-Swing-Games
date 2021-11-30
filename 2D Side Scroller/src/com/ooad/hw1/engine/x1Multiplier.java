package com.ooad.hw1.engine;

/**
 * Concrete PowerUp class.
 * it has x1 multiplier. Decorator objects can decorate this class.
 * It shouldn't be displayed in the game since there is no type of x1Multiplier
 */
public class x1Multiplier extends PowerUp{

    /**
     * Default Constructor for x1Multiplier
     */
    public x1Multiplier() {
        super(0, 0, 0, 0, 0, "");
        this.setMultiplier(1);
        setDrawable(false);
    }

    @Override
    protected void CollisionAction(Character character) {
        //Shouldn't be used
    }

    @Override
    public String getDescription() {
        return "Concrete 1x multiplier";
    }

    @Override
    public long calculateMultiplier() {
        return this.getMultiplier();
    }
}
