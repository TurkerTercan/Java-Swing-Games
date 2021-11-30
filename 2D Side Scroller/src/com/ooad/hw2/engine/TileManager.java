package com.ooad.hw2.engine;

import com.ooad.hw2.Helper;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * TileManager class is responsible for manage creation, animation, shuffle, collision detection with collidable objects.
 * All these functionalities made in another thread.
 * Updates game status for every game objects and, it also runs at 60 FPS.
 */
public class TileManager extends JComponent implements Runnable{
    private static Tile[][] tiles = new Tile[6][9];
    private final Random random = new Random(System.currentTimeMillis());
    private final Sprite board;
    private final Sprite logBoard;
    private static final Sprite exitButton = new Sprite((Helper.Window_Width - 250) / 2,  410, 251, 100, new ImageIcon(Helper.exitButtonPath).getImage());
    private static final Sprite pauseButton = new Sprite( Helper.Window_Width - 80, 10, 70, 64, new ImageIcon(Helper.pauseButtonPath).getImage());
    private static final Sprite gameOverImage = new Sprite( (Helper.Window_Width - 420) / 2, 190, 420, 240, new ImageIcon(Helper.gameOverPath).getImage());
    private static final Sprite newGameButton = new Sprite((Helper.Window_Width - 400) / 2, 300, 400, 100, new ImageIcon(Helper.newGameButtonPath).getImage());
    private static boolean pauseFlag = false;
    private Thread animThread;

    private static boolean isAnyTileSelected = false;
    private static int selectedX = -1, selectedY = -1;

    private static int secondX = -1, secondY = -1;
    private static boolean isSecondTileSelected = false;
    private boolean isRunning;
    private static boolean isAnimationRunning;

    private int first_x, first_y;
    private int second_x, second_y;

    private HashMap<Integer, Integer> animMap = new HashMap<>();

    private ArrayList<Integer> x_array = new ArrayList<Integer>();
    private ArrayList<Integer> y_array = new ArrayList<Integer>();
    private Tile[] created_tiles = null;

    private boolean first_shuffle_anim = false;
    private boolean second_shuffle_anim = false;
    private boolean shuffle = false;
    private static boolean gameover = false;

    private static boolean player_turn = true;
    private Collidable[] enemies = new Collidable[3];
    private Collidable[] characters = new Collidable[3];

    private final EnemyComponentFactory enemyComponentFactory = new EnemyComponentFactory();
    private final CharacterComponentFactory characterComponentFactory = new CharacterComponentFactory();
    private static boolean resetSignal = false;

    /**
     * Public constructor
     */
    public TileManager() {
        board = new Sprite((Helper.Window_Width - 700) / 2, Helper.tileLocationY, 700, 452,
                new ImageIcon(Helper.tileBoardPath).getImage());
        logBoard = new Sprite(0, Helper.Window_Height - 70, 700, 452,
                new ImageIcon(Helper.logBoardPath).getImage());

        resetGame();
    }

    /**
     * Resets the game
     */
    private void resetGame() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
                Tile temp;
                Tile.Color color = Helper.ColorsForNoMatch[((i * 9) + j)];
                temp = new Tile(j * 70 + Helper.tileLocationX + 8, i * 65 + Helper.tileLocationY + 30, 64, 64, color);

                tiles[i][j] = temp;
            }
        }
        for (int i = 0; i < 10; i++) {
            int tile_selection = random.nextInt(tiles.length * tiles[0].length);
            int tile_type = random.nextInt(3);

            Tile temp;
            Tile.Color color;
            if (tile_type == 0) {
                color = Tile.Color.RED;
            } else if (tile_type == 1) {
                color = Tile.Color.BLUE;
            } else {
                color = Tile.Color.GREEN;
            }
            temp = new Tile((tile_selection % 9) * 70 + Helper.tileLocationX + 8, (tile_selection / 9) * 65 + Helper.tileLocationY + 30, 64, 64, color);
            tiles[tile_selection / 9][tile_selection % 9] = temp;
        }

        for (int i = 0; i < 3; i++) {
            enemies[i] = new Enemy(i * 210 + Helper.tileLocationX + 35, Helper.tileLocationY - 140, 150, 150, null, enemyComponentFactory);
            characters[i] = new Character(i * 210 + Helper.tileLocationX + 35, Helper.tileLocationY - 300, 150, 150, null, characterComponentFactory);
        }

        pauseFlag = false;
        isAnyTileSelected = false;
        selectedX = -1; selectedY = -1;

        secondX = -1; secondY = -1;
        isSecondTileSelected = false;
        isAnimationRunning = false;

        first_shuffle_anim = false;
        second_shuffle_anim = false;
        shuffle = false;
        gameover = false;
        player_turn = true;

        animMap = new HashMap<>();
        x_array = new ArrayList<Integer>();
        y_array = new ArrayList<Integer>();
        resetSignal = false;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (animThread == null){
            animThread = new Thread(TileManager.this);
        }
        animThread.start();
    }

    /**
     * Used to draw the object within the TileManager
     * @param g2D Graphics2D to visualize objects
     */
    public void onDraw(Graphics2D g2D) {
        for (Collidable enemy: enemies) {
            if (enemy != null)
                enemy.draw(g2D);
        }
        for (Collidable character: characters) {
            if (character != null)
                character.draw(g2D);
        }

        board.draw(g2D);

        for (Tile[] row: tiles) {
            for (Tile tile : row) {
                if (tile != null)
                    tile.draw(g2D);
            }
        }
        if (created_tiles != null) {
            for (Tile created: created_tiles)
                if (created != null)
                    created.draw(g2D);
        }

        logBoard.draw(g2D);
        g2D.setFont(Helper.loggerFont);
        g2D.drawString(Logger.getSingleLog(), 50, Helper.Window_Height - 15);

        if (!gameover) {
            pauseButton.draw(g2D);
        }

        if (pauseFlag || gameover) {
            newGameButton.draw(g2D);
            exitButton.draw(g2D);
            if (gameover)
                gameOverImage.draw(g2D);
        }
    }


    @Override
    public void run() {
        boolean first_step = true;
        boolean second_step = false;
        boolean third_step = false;
        boolean forth_step = false;
        boolean fifth_step = false;
        int[] new_tiles = new int[9];
        int[] created_row = null;
        int[] created_column = null;
        boolean rollback = false;
        init();
        Logger.setSingleLog("Game is started");
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis() - startTime;
            long waitTime = (Helper.MS / Helper.FPS) - endTime / Helper.MS;
            try {
                Thread.sleep(waitTime);
            } catch (Exception ignored) {}
            if (resetSignal) {
                resetGame();
            }

            if (gameover) {
                continue;
            }

            if (shuffle) {
                shuffleTileBoard();
                continue;
            }

            if (isSecondTileSelected && isAnyTileSelected && x_array.size() == 0 && first_step) {
                Tile first = tiles[selectedX][selectedY];
                Tile second = tiles[secondX][secondY];
                if (!isAnimationRunning) {
                    first_x = first.getSprite().getY();
                    first_y = first.getSprite().getX();
                    second_x = second.getSprite().getY();
                    second_y = second.getSprite().getX();
                    isAnimationRunning = true;
                    Logger.setSingleLog("(" + selectedY + ", " + selectedX + ", " + first.getColor() + ") and "
                            + "(" + secondY + ", " + secondX + ", " + second.getColor() + ") will be replaced");
                }

                //Firstly selected moves up
                if (selectedX > secondX && ((first.getSprite().getY() != second_x && second.getSprite().getY() != first_x)
                        || (rollback && first.getSprite().getY() != first_x && second.getSprite().getY() != second_x))) {
                    first.moveTile(0, -1);
                    second.moveTile(0, 1);
                }
                //Firstly selected moves down
                else if (selectedX < secondX && ((first.getSprite().getY() != second_x && second.getSprite().getY() != first_x)
                        || (rollback && first.getSprite().getY() != first_x && second.getSprite().getY() != second_x))) {
                    first.moveTile(0, 1);
                    second.moveTile(0, -1);
                }
                //Firstly selected moves left
                else if (selectedY > secondY && ((first.getSprite().getX() != second_y && second.getSprite().getX() != first_y)
                        || (rollback && first.getSprite().getX() != first_y && second.getSprite().getX() != second_y))) {
                    first.moveTile(-1, 0);
                    second.moveTile(1, 0);
                }
                else if (selectedY < secondY && ((first.getSprite().getX() != second_y && second.getSprite().getX() != first_y)
                        || (rollback && first.getSprite().getX() != first_y && second.getSprite().getX() != second_y))) {
                    first.moveTile(1, 0);
                    second.moveTile(-1, 0);
                }


                if (!rollback && (first.getSprite().getY() == second_x && second.getSprite().getY() == first_x)
                        && (first.getSprite().getX() == second_y && second.getSprite().getX() == first_y)) {
                    tiles[selectedX][selectedY] = second;
                    tiles[secondX][secondY] = first;

                    if(checkForAnyMatch()) {
                        isAnyTileSelected = false;
                        selectedX = -1;
                        selectedY = -1;

                        isSecondTileSelected = false;
                        secondX = -1;
                        secondY = -1;

                        first_step = false;
                        second_step = true;
                    } else {
                        Logger.setSingleLog("There is no matching tile. Reverting the change.");
                        rollback = true;
                        int temp = selectedX;
                        int temp2 = selectedY;
                        selectedX = secondX;
                        selectedY = secondY;
                        secondX = temp;
                        secondY = temp2;
                    }
                }
                if (rollback && (first.getSprite().getY() == first_x && second.getSprite().getY() == second_x)
                        && (first.getSprite().getX() == first_y && second.getSprite().getX() == second_y)) {
                    tiles[selectedX][selectedY] = second;
                    tiles[secondX][secondY] = first;

                    isAnyTileSelected = false;
                    selectedX = -1;
                    selectedY = -1;

                    isSecondTileSelected = false;
                    secondX = -1;
                    secondY = -1;

                    first_step = false;
                    second_step = true;
                    rollback = false;
                }
            }
            if (isAnimationRunning && second_step) {
                for (int i = 0; i < x_array.size(); i++) {
                    tiles[x_array.get(i)][y_array.get(i)].moveTile(0, -1);
                    //Collision Detection
                    if (player_turn) {
                        //enemies.add(new Enemy(i * 210 + Helper.tileLocationX + 35, Helper.tileLocationY - 140, 150, 150, null, enemyComponentFactory));
                        //characters.add(new Character(i * 210 + Helper.tileLocationX + 35, Helper.tileLocationY - 300, 150, 150, null, characterComponentFactory));
                        if (tiles[x_array.get(i)][y_array.get(i)].getSprite().getY() < Helper.tileLocationY) {
                            if (enemies[y_array.get(i) / 3] != null) {
                                enemies[y_array.get(i) / 3].getDamage(tiles[x_array.get(i)][y_array.get(i)].getColor());
                                Logger.setSingleLog(enemies[y_array.get(i) / 3].getColor() + " " + enemies[y_array.get(i) / 3].getStyleDescription()
                                        + " damaged by " + tiles[x_array.get(i)][y_array.get(i)].getColor());
                            }
                            tiles[x_array.get(i)][y_array.get(i)] = null;
                            new_tiles[y_array.get(i)] = new_tiles[y_array.get(i)] + 1;

                            if (enemies[y_array.get(i) / 3] != null && enemies[y_array.get(i) / 3].isDead()) {
                                Logger.setSingleLog(enemies[y_array.get(i) / 3].getColor() + " " + enemies[y_array.get(i) / 3].getStyleDescription() + " is dead.");
                                enemies[y_array.get(i) / 3] = null;
                            }
                            x_array.remove(i);
                            y_array.remove(i);
                        }
                    } else {
                        if (tiles[x_array.get(i)][y_array.get(i)].getSprite().getY() < Helper.tileLocationY - 150) {
                            if (characters[y_array.get(i) / 3] != null) {
                                characters[y_array.get(i) / 3].getDamage(tiles[x_array.get(i)][y_array.get(i)].getColor());
                                Logger.setSingleLog(characters[y_array.get(i) / 3].getColor() + " " + characters[y_array.get(i) / 3].getStyleDescription()
                                        + " damaged by " + tiles[x_array.get(i)][y_array.get(i)].getColor());
                            }
                            tiles[x_array.get(i)][y_array.get(i)] = null;
                            new_tiles[y_array.get(i)] = new_tiles[y_array.get(i)] + 1;
                            if (characters[y_array.get(i) / 3] != null && characters[y_array.get(i) / 3].isDead()) {
                                Logger.setSingleLog(characters[y_array.get(i) / 3].getColor() + " " + characters[y_array.get(i) / 3].getStyleDescription() + " is dead.");
                                characters[y_array.get(i) / 3] = null;
                            }
                            x_array.remove(i);
                            y_array.remove(i);
                        }
                    }
                }
                if (x_array.isEmpty()) {
                    second_step = false;
                    third_step = true;
                }
            }
            if (isAnimationRunning && animMap.isEmpty() && third_step)  {
                for (int j = 0; j < tiles[0].length; j++) {
                    for (int i = 1; i < tiles.length; i++) {
                        if (tiles[i][j] == null)
                            continue;
                        int count = 0;
                        for (int k = i - 1; k >= 0; k--) {
                            if (tiles[k][j] == null) {
                                count++;
                            }
                        }
                        if (count != 0) {
                            animMap.put((i * 9) + j, count);
                        }
                    }
                }
                third_step = false;
                forth_step = true;
                int temp_count = 0;
                for (int new_tile : new_tiles) {
                    temp_count += new_tile;
                }
                if (temp_count != 0) {
                    Logger.setSingleLog(temp_count + " tiles has been matched.");
                }
                created_tiles = new Tile[temp_count];
                created_row = new int[temp_count];
                created_column = new int[temp_count];
                temp_count = 0;
                for (int i = 0; i < new_tiles.length; i++) {
                    for (int j = 0; j < new_tiles[i]; j++) {
                        int choice = random.nextInt(3);
                        Tile temp;
                        Tile.Color color;
                        if (choice == 0) {
                            color = Tile.Color.RED;
                        } else if (choice == 1) {
                            color = Tile.Color.BLUE;
                        } else {
                            color = Tile.Color.GREEN;
                        }
                        temp = new Tile(i * 70 + Helper.tileLocationX + 8, j * 65 + Helper.tileLocationY + 30 + Helper.Window_Height - 200, 64, 64, color);
                        created_tiles[temp_count] = temp;
                        created_row[temp_count] = 6 - new_tiles[i] + j;
                        created_column[temp_count++] = i;
                    }
                }

            }
            else if (isAnimationRunning && forth_step) {
                int count = 0;
                for (Map.Entry<Integer, Integer> entry: animMap.entrySet()) {
                    if (count == animMap.size())
                        break;
                    boolean temp = tiles[entry.getKey() / 9][entry.getKey() % 9].moveToSpecificRow((entry.getKey() / 9) - entry.getValue());
                    if (!temp) {
                        count++;
                    } else{
                        count = 0;
                    }
                }
                int count_temp = 0;
                for (int i = 0; i < created_tiles.length; i++) {
                    boolean temp = created_tiles[i].moveToSpecificRow( created_row[i]);
                    if (!temp)
                        count_temp++;
                    else
                        count_temp = 0;
                }
                if (count == animMap.size() && count_temp == created_tiles.length) {
                    forth_step = false;
                    fifth_step = true;
                    animMap.clear();

                    for (int i = 0; i < tiles.length; i++) {
                        for (int j = 0; j < tiles[0].length; j++) {
                            if (tiles[i][j] == null) {
                                for (int k = i + 1; k < tiles.length; k++) {
                                    if (tiles[k][j] != null) {
                                        tiles[i][j] = tiles[k][j];
                                        tiles[k][j] = null;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < created_tiles.length; i++) {
                        tiles[created_row[i]][created_column[i]] = created_tiles[i];
                    }
                }

            }
            if (isAnimationRunning && fifth_step) {
                isAnimationRunning = false;
                fifth_step = false;
                first_step = true;
                animMap.clear();
                x_array = new ArrayList<>();
                y_array = new ArrayList<>();
                created_tiles = null;
                new_tiles = new int[9];;
                created_row = null;
                created_column = null;
                rollback = false;

                if(checkForAnyMatch()) {
                    isAnyTileSelected = false;
                    selectedX = -1;
                    selectedY = -1;

                    isSecondTileSelected = false;
                    secondX = -1;
                    secondY = -1;

                    first_step = false;
                    second_step = true;
                }
                else if (checkNoMatch()) {
                    Logger.setSingleLog("There is no matching tiles in the board. SHUFFLE!");
                    shuffle = true;
                    first_shuffle_anim = true;
                    fifth_step = true;
                    first_step = false;
                } else {
                    player_turn = !player_turn;
                    if (enemies[0] == null & enemies[1] == null && enemies[2] == null) {
                        for (int i = 0; i < 3; i++) {
                            enemies[i] = new Enemy(i * 210 + Helper.tileLocationX + 35, Helper.tileLocationY - 140, 150, 150, null, enemyComponentFactory);
                        }
                    }
                    if (characters[0] == null & characters[1] == null && characters[2] == null) {
                        gameover = true;
                        Logger.setSingleLog("GAME OVER");
                        continue;
                    }

                    if (!player_turn) {
                        Logger.setSingleLog("Computer's turn");
                        int choice = random.nextInt(tiles.length * tiles[0].length);
                        selectedX = choice / 9;
                        selectedY = choice % 9;
                        isAnyTileSelected = true;

                        choice = random.nextInt(4);
                        if (choice == 0) {
                            if (selectedX != 0)
                                secondX = selectedX - 1;
                            else
                                secondX = selectedX + 1;
                            secondY = selectedY;
                        } else if (choice == 1) {
                            if (selectedX != tiles.length - 1)
                                secondX = selectedX + 1;
                            else
                                secondX = selectedX - 1;
                            secondY = selectedY;
                        } else if (choice == 2) {
                            if (selectedY != 0)
                                secondY = selectedY - 1;
                            else
                                secondY = selectedY + 1;
                            secondX = selectedX;
                        } else {
                            if (selectedY != tiles[0].length - 1)
                                secondY = selectedY + 1;
                            else
                                secondY = selectedY - 1;
                            secondX = selectedX;
                        }
                        isSecondTileSelected = true;
                    } else {
                        Logger.setSingleLog("Your Turn");
                    }
                }
            }
        }
    }

    /**
     * It sets necessary variables
     */
    private void init() {
        isRunning = true;
        isAnimationRunning = false;
    }

    /**
     * If there is no match to be played, this method shuffles the board.
     */
    private void shuffleTileBoard(){
        int count = 0;
        if (first_shuffle_anim) {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[0].length; j++) {
                    boolean temp;
                    if (i < 3)
                        temp = tiles[i][j].moveToSpecificColumnAndRow(4, 2);
                    else
                        temp = tiles[i][j].moveToSpecificColumnAndRow(4, 3);
                    if (!temp)
                        count++;
                }
            }
        }
        if (count == tiles.length * tiles[0].length) {
            first_shuffle_anim = false;
            second_shuffle_anim = true;

            Tile[][] new_tiles = new Tile[6][9];
            boolean[] checking = new boolean[6 * 9];
            for (int i = 0; i < tiles.length * tiles[0].length; i++) {
                int choice = random.nextInt(tiles.length * tiles[0].length);
                if (checking[choice]){
                    i--;
                }
                else {
                    new_tiles[i / 9][i % 9] = tiles[choice / 9][choice % 9];
                    checking[choice] = true;
                }
            }
            tiles = new_tiles;
        }
        count = 0;
        if (second_shuffle_anim) {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[0].length; j++) {
                    boolean temp;
                    temp = tiles[i][j].moveToSpecificColumnAndRow(j, i);
                    if (!temp)
                        count++;
                }
            }
        }
        if (second_shuffle_anim && count == tiles.length * tiles[0].length) {
            second_shuffle_anim = false;
            shuffle = false;
            Logger.setSingleLog("Shuffle finished!");
            isAnimationRunning = true;

        }
    }

    /**
     * It is checking if there is a chance to match any tile.
     * @return if there is a chance to match any tile.
     */
    private boolean checkNoMatch() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                Tile selected = tiles[i][j];
                if (i != 0) {
                    tiles[i][j] = tiles[i - 1][j];
                    tiles[i - 1][j] = selected;
                    boolean temp = checkForAnyMatch();
                    x_array.clear();
                    y_array.clear();
                    tiles[i - 1][j] = tiles[i][j];
                    tiles[i][j] = selected;
                    isAnimationRunning = false;
                    if (temp)
                        return false;
                }
                if (i != tiles.length - 1) {
                    tiles[i][j] = tiles[i + 1][j];
                    tiles[i + 1][j] = selected;
                    boolean temp = checkForAnyMatch();
                    x_array.clear();
                    y_array.clear();
                    tiles[i + 1][j] = tiles[i][j];
                    tiles[i][j] = selected;
                    isAnimationRunning = false;
                    if (temp)
                        return false;
                }
                if (j != 0){
                    tiles[i][j] = tiles[i][j - 1];
                    tiles[i][j - 1] = selected;
                    boolean temp = checkForAnyMatch();
                    x_array.clear();
                    y_array.clear();
                    tiles[i][j - 1] = tiles[i][j];
                    tiles[i][j] = selected;
                    isAnimationRunning = false;
                    if (temp)
                        return false;
                }
                if (j != tiles[0].length - 1) {
                    tiles[i][j] = tiles[i][j + 1];
                    tiles[i][j + 1] = selected;
                    boolean temp = checkForAnyMatch();
                    x_array.clear();
                    y_array.clear();
                    tiles[i][j + 1] = tiles[i][j];
                    tiles[i][j] = selected;
                    isAnimationRunning = false;
                    if (temp)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * After pc or player is finished its move, this method checks if there is any matching in the board.
     * @return  if there is any matching in the board.
     */
    private boolean checkForAnyMatch() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (i == 0 || i == tiles.length -1 || j == 0 || j == tiles[0].length - 1) {
                    if ((i == 0 || i == tiles.length - 1) && j != 0 && j != tiles[0].length - 1){
                        Tile selected = tiles[i][j];
                        Tile left = tiles[i][j - 1];
                        Tile right = tiles[i][j + 1];
                        boolean isSelected = false, isLeft = false, isRight = false;
                        if (selected.getColor() == left.getColor() && selected.getColor() == right.getColor()) {
                            isSelected = true;
                            isLeft = true;
                            isRight = true;
                            for (int k = 0; k < x_array.size(); k++) {
                                int temp_x = x_array.get(k);
                                int temp_y = y_array.get(k);
                                if (temp_x == i && temp_y == j) {
                                    isSelected = false;
                                }
                                if (temp_x == i && temp_y == j - 1) {
                                    isLeft = false;
                                }
                                if (temp_x == i && temp_y == j + 1) {
                                    isRight = false;
                                }
                            }
                        }
                        if (isLeft) {
                            x_array.add(i);
                            y_array.add(j - 1);
                            isAnimationRunning = true;
                        }
                        if (isRight){
                            x_array.add(i);
                            y_array.add(j + 1);
                            isAnimationRunning = true;
                        }
                        if (isSelected) {
                            x_array.add(i);
                            y_array.add(j);
                            isAnimationRunning = true;
                        }
                    }
                    else if ((j == 0 || j == tiles[0].length - 1) && i != 0 && i != tiles.length - 1) {
                        Tile selected = tiles[i][j];
                        Tile up = tiles[i - 1][j];
                        Tile down = tiles[i + 1][j];
                        boolean isSelected = false, isUp = false, isDown = false;
                        if (selected.getColor() == up.getColor() && selected.getColor() == down.getColor()) {
                            isSelected = true;
                            isUp = true;
                            isDown = true;
                            for (int k = 0; k < x_array.size(); k++) {
                                int temp_x = x_array.get(k);
                                int temp_y = y_array.get(k);
                                if (temp_x == i && temp_y == j) {
                                    isSelected = false;
                                }
                                if (temp_x == i - 1 && temp_y == j) {
                                    isUp = false;
                                }
                                if (temp_x == i + 1 && temp_y == j) {
                                    isDown = false;
                                }
                            }
                        }
                        if (isUp) {
                            x_array.add(i - 1);
                            y_array.add(j);
                            isAnimationRunning = true;
                        }
                        if (isDown) {
                            x_array.add(i + 1);
                            y_array.add(j);
                            isAnimationRunning = true;
                        }
                        if (isSelected) {
                            x_array.add(i);
                            y_array.add(j);
                            isAnimationRunning = true;
                        }
                    }
                    continue;
                }
                Tile selected = tiles[i][j];
                Tile left = tiles[i][j - 1];
                Tile right = tiles[i][j + 1];
                Tile up = tiles[i - 1][j];
                Tile down = tiles[i + 1][j];

                boolean isSelected = false, isLeft = false, isRight = false, isUp = false, isDown = false;
                if (selected.getColor() == left.getColor() && selected.getColor() == right.getColor()) {
                    isSelected = true;
                    isLeft = true;
                    isRight = true;
                    for (int k = 0; k < x_array.size(); k++) {
                        int temp_x = x_array.get(k);
                        int temp_y = y_array.get(k);
                        if (temp_x == i && temp_y == j) {
                            isSelected = false;
                        }
                        if (temp_x == i && temp_y == j - 1) {
                            isLeft = false;
                        }
                        if (temp_x == i && temp_y == j + 1) {
                            isRight = false;
                        }

                    }
                }
                if (selected.getColor() == up.getColor() && selected.getColor() == down.getColor()) {
                    isSelected = true;
                    isUp = true;
                    isDown = true;
                    for (int k = 0; k < x_array.size(); k++) {
                        int temp_x = x_array.get(k);
                        int temp_y = y_array.get(k);
                        if (temp_x == i && temp_y == j) {
                            isSelected = false;
                        }
                        if (temp_x == i - 1 && temp_y == j) {
                            isUp = false;
                        }
                        if (temp_x == i + 1 && temp_y == j) {
                            isDown = false;
                        }
                    }
                }
                if (isLeft) {
                    x_array.add(i);
                    y_array.add(j - 1);
                    isAnimationRunning = true;
                }
                if (isRight){
                    x_array.add(i);
                    y_array.add(j + 1);
                    isAnimationRunning = true;
                }
                if (isUp) {
                    x_array.add(i - 1);
                    y_array.add(j);
                    isAnimationRunning = true;
                }
                if (isDown) {
                    x_array.add(i + 1);
                    y_array.add(j);
                    isAnimationRunning = true;
                }
                if (isSelected) {
                    x_array.add(i);
                    y_array.add(j);
                    isAnimationRunning = true;
                }
            }
        }

        return x_array.size() != 0 && y_array.size() != 0;
    }

    /**
     * It is responsible for mouse input
     * @param x x-location of mouse input
     * @param y y-location of mouse input
     */
    public static void checkForTiles(int x, int y) {
        if (pauseButton.mouseDetectionForMouseInput(x, y) && !gameover) {
            pauseFlag = !pauseFlag;
        } else if ((pauseFlag || gameover) && newGameButton.mouseDetectionForMouseInput(x, y)) {
            resetSignal = true;
        } else if ((pauseFlag || gameover) && exitButton.mouseDetectionForMouseInput(x, y)) {
            System.exit(0);
        }

        if (isAnimationRunning || !player_turn || gameover || pauseFlag)
            return;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] != null) {
                    boolean temp = tiles[i][j].isMatched(x, y);
                    if (temp) {
                        if (!isAnyTileSelected) {
                            selectedX = i;
                            selectedY = j;
                            isAnyTileSelected = true;
                            Logger.setSingleLog("Selection registered as first (" + j + ", " + i + ")");
                        } else if (!isSecondTileSelected
                                && ((Math.abs(selectedX - i) == 1 && selectedY - j == 0)
                                || (Math.abs(selectedY - j) == 1 && selectedX - i == 0))) {
                            secondX = i;
                            secondY = j;
                            isSecondTileSelected = true;
                            Logger.setSingleLog("Selection registered as second (" + j + ", " + i + ")");
                        } else {
                            selectedX = i;
                            selectedY = j;

                            Logger.setSingleLog("Selection registered as first (" + j + ", " + i + ")");

                            secondX = -1;
                            secondY = -1;
                            isSecondTileSelected = false;
                        }
                    }
                }
            }
        }
    }
}
