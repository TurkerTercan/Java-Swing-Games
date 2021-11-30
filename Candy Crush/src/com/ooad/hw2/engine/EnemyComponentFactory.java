package com.ooad.hw2.engine;

import java.util.Random;

/**
 * EnemyComponentFactory is an abstract factory implementation to create
 * character objects' components
 */
public class EnemyComponentFactory implements ComponentFactory{
    private final Random random = new Random();

    @Override
    public Style createStyle() {
        int choice = random.nextInt(3);
        return switch (choice) {
            case 0 -> new Atlantis();
            case 1 -> new Valhalla();
            case 2 -> new Underwild();
            default -> throw new IllegalArgumentException("Illegal Choice");
        };
    }

    @Override
    public Type createType() {
        int choice = random.nextInt(3);
        return switch (choice) {
            case 0 -> new Red();
            case 1 -> new Blue();
            case 2 -> new Green();
            default -> throw new IllegalArgumentException("Illegal Choice");
        };
    }
}
