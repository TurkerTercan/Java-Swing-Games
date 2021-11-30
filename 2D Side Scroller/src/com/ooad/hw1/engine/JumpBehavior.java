package com.ooad.hw1.engine;

/**
 * JumpBehavior is an interface, and it is simple example of Strategy Design Pattern.
 * It will enable to change object's jumpBehavior at runtime.
 */
public interface JumpBehavior {
    /**
     * FallSpeed of mainCharacter
     */
    int fallSpeed = 8;

    /**
     * RaiseSpeed of mainCharacter
     */
    int raiseSpeed = 5;

    /**
     * Jump method is used for making character to jump
     * @param sprite main character
     */
    void jump(Sprite sprite);

    /**
     * Description of new classes
     * @return description
     */
    String getDescription();
}
