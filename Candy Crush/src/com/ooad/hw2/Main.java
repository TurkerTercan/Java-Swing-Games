package com.ooad.hw2;

import com.ooad.hw2.engine.GamePanel;
import javax.swing.*;

/**
 * Main class starts the game
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(Helper.gameName);
            JPanel jPanel = new GamePanel();
            frame.add(jPanel);
            frame.setResizable(false);
            frame.pack();


            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
