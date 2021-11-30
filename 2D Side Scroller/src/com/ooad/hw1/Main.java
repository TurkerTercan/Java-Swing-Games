package com.ooad.hw1;

import com.ooad.hw1.engine.GamePanel;
import com.ooad.hw1.utility.Helper;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * Main class to start our game. It simply creates JFrame object and adds our GamePanel to it.
 */
public class Main {
    /**
     * main function
     * @param args no args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(Helper.gameName);
            GamePanel gamePanel = new GamePanel();
            frame.add(gamePanel);
            frame.setResizable(false);
            frame.pack();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {

                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    gamePanel.PauseGame();
                }
            });
        });
    }
}
