package com.ooad.hw1.engine;

import com.ooad.hw1.utility.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

/**
 * GamePanel is used to display whole panel of the game and log messages.
 * Also, it is thread. It sleeps after every frame to catch 60 frame per second.
 * Updating game general status of the game and displaying and re-rendering all the images implemented here.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {
    private Thread gameThread;
    private final ArrayList<Sprite> backGrounds = new ArrayList<>();
    private final Character character = new Character(70, Helper.CHAR_START_Y_LOC, 0);
    private final ArrayList<Collidable> enemies = new ArrayList<>();
    private boolean isRunning;
    private boolean isPaused = true;
    private double fps;
    private final Random random = new Random(System.currentTimeMillis());

    /**
     * Constructor for GamePanel object. It sets the size of the panel and adds a mouseListener
     */
    public GamePanel() {
        setPreferredSize(new Dimension(Helper.WIDTH, Helper.HEIGHT));
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        resetGame();
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

    /**
     * Resets the game. All the Enemy, PowerUp and Background objects re-instantiated with constant positions.
     */
    private void resetGame() {
        character.resetScore();
        backGrounds.clear();
        backGrounds.add(new BackGround(0, 0, Helper.MOVEMENT_SPEED));
        backGrounds.add(new BackGround(Helper.WIDTH, 0, Helper.MOVEMENT_SPEED));

        enemies.clear();
        enemies.add(new Enemy(Helper.WIDTH - 200, Helper.CHAR_START_Y_LOC + 20, Helper.MOVEMENT_SPEED, Helper.sansHeight, Helper.sansWidth, Helper.sansPath));
        enemies.add(new Enemy(Helper.WIDTH + 300, Helper.CHAR_START_Y_LOC + 20, Helper.MOVEMENT_SPEED, Helper.sansHeight, Helper.sansWidth, Helper.sansPath));
        enemies.add(new Enemy(Helper.WIDTH + 800, Helper.CHAR_START_Y_LOC + 20, Helper.MOVEMENT_SPEED, Helper.sansHeight, Helper.sansWidth, Helper.sansPath));
        enemies.add(new Enemy(Helper.WIDTH + 1300, Helper.CHAR_START_Y_LOC + 20, Helper.MOVEMENT_SPEED, Helper.sansHeight, Helper.sansWidth, Helper.sansPath));
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (gameThread == null) {
            gameThread = new Thread(GamePanel.this);
        }
        gameThread.start();
    }

    protected void onDraw(Graphics2D g2D) {
        for (Sprite backGround : backGrounds) {
            if (backGround.isDrawable())
                backGround.draw(g2D);
        }
        character.draw(g2D);
        for (Sprite object : enemies) {
            if (object.isDrawable())
                object.draw(g2D);
        }

        if (String.valueOf(fps).length() > 5) {
            g2D.setFont(Helper.font);
            g2D.drawString("FPS Counter: " + String.valueOf(fps).substring(0, 5), 17, 30);
        }
        else {
            g2D.drawString("FPS Counter: " + fps, 17, 30);
        }

        g2D.drawString("Score: " + character.getScore(), Helper.WIDTH - 300, 100);
        int hp = character.getHealth();
        for (int i = 0; i < hp; i++) {
            g2D.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.heartPath))).getImage(), Helper.WIDTH - 300 + (i * 64), 10, null);
        }
        if (character.isCharacterAlive())
            g2D.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.smallPausePath))).getImage(), Helper.WIDTH - 80, 10, null);

        //g2D.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.menuBGPath))).getImage(), (Helper.WIDTH / 2) - 320, 360 - 240, null);

        if (isPaused || !character.isCharacterAlive()) {
            if (!character.isCharacterAlive())
                g2D.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.gameOverPath))).getImage(), (Helper.WIDTH / 2) - 210, 360 - 260, null);
            g2D.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.newGameButtonPath))).getImage(), (Helper.WIDTH / 2) - 200, 360 - 110, null);
            g2D.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.closeButtonPath))).getImage(), (Helper.WIDTH / 2) - 125, 360, null);
        }
        g2D.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource(Helper.logLayout))).getImage(), 0, 720, null);

        if (character.getStrings().size() > 0) {
            g2D.setFont(Helper.fontLog);
            for (int i = character.getStrings().size() - 1; i >= 0; i--) {
                g2D.drawString(character.getStrings().get(i), 20, 767 + (i * 25));
            }
        }
    }

    @Override
    public void run() {
        init();
        long frames = 0;
        while(isRunning) {
            long startTime = System.currentTimeMillis();
            if (character.getHealth() > 0 && !isPaused) {
                updateGame();
            }
            renderGame();
            long endTime = System.currentTimeMillis() - startTime;
            long waitTime = (Helper.MS / Helper.FPS) - endTime / Helper.MS;
            long lastTime;
            lastTime = System.nanoTime();
            try {
                Thread.sleep(waitTime);
            } catch (Exception ignored) {}
            if (frames >= 30) {
                fps = 1000000000.0 / (System.nanoTime() - lastTime);
                frames = 0;
            }
            frames++;
        }
    }

    /**
     * It repaints the game
     */
    private void renderGame() {
        repaint();
    }

    /**
     * Game Logic is implemented here. If main character is moving, all other objects will be moved to the left.
     * If an object passes out on the left side the panel, it will be removed and another object will be initialized randomly.
     * If a collidable objects collides with main character, also it will be removed and another object will be initialized randomly.
     * After that collided object's collided action method will take place.
     * If our main character successfully jumps over an Enemy, it will be rewarded with its current score multiplier.
     */
    private void updateGame() {
        if (character.isMoving()) {
            boolean removed = false;
            int removedLocationX = 10;
            for (Iterator<Sprite> iterator = backGrounds.iterator(); iterator.hasNext(); ) {
                Sprite bg = iterator.next();
                bg.moveLeft();
                if (bg.getX() < Helper.WIDTH * -1) {
                    removedLocationX = bg.getX();
                    iterator.remove();
                    removed = true;
                }
            }
            if (removed && removedLocationX != 10) {
                backGrounds.add(new BackGround(Helper.WIDTH - ((removedLocationX * -1) - Helper.WIDTH), 0, Helper.MOVEMENT_SPEED));
            }

            removed = false;
            removedLocationX = 10;
            for (Iterator<Collidable> iterator = enemies.iterator(); iterator.hasNext(); ){
                Sprite object = iterator.next();
                object.moveLeft();
                if (object.getX() < -200) {
                    removedLocationX = object.getX();
                    iterator.remove();
                    removed = true;
                }
            }
            if (removed && removedLocationX != 10) {
                addNewCollidable();
            }

        }
        boolean removed = false;

        if (character.isJumping() || character.isJumpSignal()) {
            character.jump();
        }

        for (Iterator<Collidable> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Collidable collidable = iterator.next();
            if (character.isCollided(collidable)) {
                iterator.remove();
                collidable.CollisionAction(character);
                removed = true;
            }
        }
        if (removed) {
            addNewCollidable();
        }
        for (Collidable passed : enemies) {
            if (passed.getX() + passed.getWidth() <= character.getX() && passed instanceof Enemy && !((Enemy) passed).isCollected()) {
                character.increaseScore();
                ((Enemy) passed).setCollected(true);
            }
        }
    }

    /**
     * Method to initialize objects randomly.
     * %60 Enemy, %10 A, %10 B, %10 C, %10 D type PowerUp will be initialized
     */
    private void addNewCollidable() {
        Collidable lastCollidable = enemies.get(enemies.size() - 1);
        int choice = random.nextInt(100);
        int startPos;
        if (lastCollidable.getX() + 300 < Helper.WIDTH)
            startPos = Helper.WIDTH + random.nextInt(800);
        else
            startPos = lastCollidable.getX() + 300 + random.nextInt(500);

        if (choice <= 30) {
            enemies.add(new Enemy(startPos, Helper.CHAR_START_Y_LOC + 20, Helper.MOVEMENT_SPEED, Helper.sansHeight, Helper.sansWidth, Helper.sansPath));
        } else if ( choice <= 60) {
            enemies.add(new Enemy(startPos, Helper.CHAR_START_Y_LOC + 36, Helper.MOVEMENT_SPEED, Helper.obstacleHeight, Helper.obstacleWidth, Helper.obstaclePath));
        } else if ( choice <= 70 ) {
            enemies.add(new x2Multiplier(startPos, Helper.CHAR_START_Y_LOC + 25, Helper.MOVEMENT_SPEED, Helper.powerUpSize, Helper.powerUpSize, Helper.x2Path));
        } else if ( choice <= 80 ) {
            enemies.add(new x5Multiplier(startPos, Helper.CHAR_START_Y_LOC + 25, Helper.MOVEMENT_SPEED, Helper.powerUpSize, Helper.powerUpSize, Helper.x5Path));
        } else if ( choice <= 90 ) {
            enemies.add(new x10Multiplier(startPos, Helper.CHAR_START_Y_LOC + 25, Helper.MOVEMENT_SPEED, Helper.powerUpSize, Helper.powerUpSize, Helper.x10Path));
        } else {
            enemies.add(new JumpPowerUp(startPos, Helper.CHAR_START_Y_LOC + 25, Helper.MOVEMENT_SPEED, Helper.powerUpSize, Helper.powerUpSize, Helper.jumpPowerUpPath));
        }
    }

    /**
     * initialize certain variables
     */
    private void init() {
        isRunning = true;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            if (character.isCharacterAlive() && !isPaused) {
                character.setMoving(true);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (character.isCharacterAlive() && !isPaused)
                character.setJumpSignal(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            isPaused = !isPaused;
            character.setMoving(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            if (character.isCharacterAlive()) {
                character.setMoving(false);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (character.isCharacterAlive()) {
                character.setJumpSignal(false);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int posX = e.getX();
        int posY = e.getY();
        //Helper.WIDTH - 80, 10
        if (posX >= Helper.WIDTH - 80 && posX <= Helper.WIDTH - 10 && posY >= 10 && posY <= 74) {
            isPaused = !isPaused;
        }
        if (isPaused || !character.isCharacterAlive()) {
            if (posX >= (Helper.WIDTH / 2) - 200 && posX <= (Helper.WIDTH / 2) + 200 && posY >= 250 && posY <= 250 +97 ) {
                isPaused = false;
                resetGame();
            }
            if (posX >= (Helper.WIDTH / 2) - 125 && posX <= (Helper.WIDTH / 2) - 125 + 251 && posY >= 360 && posY <= 460) {
                System.exit(0);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void PauseGame() {
        character.setMoving(false);
        character.setJumpSignal(false);
        isPaused = true;
    }

}
