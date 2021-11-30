package com.ooad.hw2.engine;

/**
 * Interface to generate abstract component factories.
 * You can generate your own abstract factories if you accept this aggrement.
 */
public interface ComponentFactory {
    /**
     * Crates a Style component for a collidable object
     * @return new created Style object
     */
    Style createStyle();

    /**
     * Creates a Type component for a collidable object
     * @return new created Type object
     */
    Type createType();
}
