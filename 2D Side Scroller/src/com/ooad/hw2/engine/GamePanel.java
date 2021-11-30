package com.ooad.hw2.engine;

import com.ooad.hw2.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

/**
 * GamePanel is used to display whole panel of the game and, log messages.
 * Also, it is thread. It sleeps after every frame to catch 60 frame per second.
 * It renders the game, actually.
 */
public class GamePanel extends JPanel implements Runnable{
    private Thread gameThread;
    private boolean isRunning;
    private double current_fps;
    private Sprite background = new Sprite(0, 0, 600, 800,
            new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.backgroundPath))).getImage());
    private TileManager tileManager = new TileManager();


    /**
     * Public constructor
     */
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(Helper.Window_Width, Helper.Window_Height));
        setFocusable(true);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TileManager.checkForTiles(e.getX(), e.getY());
            }
        });
        this.add(tileManager);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        onDraw(g2D);
        g2D.dispose();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (gameThread == null) {
            gameThread = new Thread(GamePanel.this);
        }
        gameThread.start();
    }

    @Override
    public void run() {
        init();
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            renderGame();
            long endTime = System.currentTimeMillis() - startTime;
            long waitTime = (Helper.MS / Helper.FPS) - endTime / Helper.MS;
            long lastTime;
            lastTime = System.nanoTime();
            try {
                Thread.sleep(waitTime);
            } catch (Exception ignored) {}
            current_fps = 1000000000.0 / (System.nanoTime() - lastTime);
        }
    }

    /**
     * Initializes necessary variables here.
     */
    private void init() {
        isRunning = true;
    }

    /**
     * It is used to render the game with 2D graphics
     * @param g2D Graphics2D variable to render JComponents and Sprites
     */
    private void onDraw(Graphics2D g2D) {
        background.draw(g2D);
        tileManager.onDraw(g2D);
    }

    /**
     * It re-renders the game.
     */
    private void renderGame() {
        repaint();
    }

}
