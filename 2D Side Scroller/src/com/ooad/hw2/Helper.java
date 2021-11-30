package com.ooad.hw2;

import com.ooad.hw2.engine.Tile;

import java.awt.*;

/**
 * Helper class is used to locate constants
 */
public interface Helper {
    String gameName = "Stardew Saga";
    int Window_Width = 800, Window_Height = 800;
    long MS = 1000L;
    int FPS = 60;
    int tileWidth = 70;
    int tileHeight = 70;
    int tileLocationX = (Window_Width - (tileWidth * 9)) / 2;
    int tileLocationY = 300;

    int tileMoveSpeed = 5;

    String backgroundPath = "/com/ooad/hw2/assets/background.png";
    String redPotionSpritePath = "/com/ooad/hw2/assets/redPotionSprite.png";
    String leafSpritePath = "/com/ooad/hw2/assets/leafSprıte.png";
    String diamondSpritePath = "/com/ooad/hw2/assets/diamondSpritePath";
    String blueAtlantis = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\BlueAtlantis.png";
    String blueUnderwild = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\BlueUnderwild.png";
    String blueValhalla= "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\BlueAtlantis.png";
    String redAtlantis = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\RedAtlantis.png";
    String redUnderwild = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\RedUnderwild.png";
    String redValhalla= "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\RedAtlantis.png";
    String greenAtlantis = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\GreenAtlantis.png";
    String greenUnderwild = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\GreenUnderwild.png";
    String greenValhalla= "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\GreenAtlantis.png";
    String tileBoardPath = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\tileboard.png";
    String logBoardPath = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\logBoard.png";
    String exitButtonPath = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\closeButton.png";
    String gameOverPath = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\gameOver.png";
    String newGameButtonPath = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\newGame.png";
    String pauseButtonPath = "C:\\Users\\bjktr\\IdeaProjects\\hw1\\src\\com\\ooad\\hw2\\assets\\smallPauseButton.png";

    Font loggerFont = new Font("Tahoma",Font.BOLD, 18);


    int[][] PIXEL_LOC = {
            {93, 330}, {163, 330}, {233, 330}, {303, 330}, {373, 330}, {443, 330}, {513, 330}, {583, 330}, {653, 330},
            {93, 395}, {163, 395}, {233, 395}, {303, 395}, {373, 395}, {443, 395}, {513, 395}, {583, 395}, {653, 395},
            {93, 460}, {163, 460}, {233, 460}, {303, 460}, {373, 460}, {443, 460}, {513, 460}, {583, 460}, {653, 460},
            {93, 525}, {163, 525}, {233, 525}, {303, 525}, {373, 525}, {443, 525}, {513, 525}, {583, 525}, {653, 525},
            {93, 590}, {163, 590}, {233, 590}, {303, 590}, {373, 590}, {443, 590}, {513, 590}, {583, 590}, {653, 590},
            {93, 655}, {163, 655}, {233, 655}, {303, 655}, {373, 655}, {443, 655}, {513, 655}, {583, 655}, {653, 655},
    };

    Tile.Color[] ColorsForNoMatch = {
            Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN,
            Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE,
            Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED,
            Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN,
            Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE,
            Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED,
            Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN,
            Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE,
            Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED, Tile.Color.GREEN, Tile.Color.BLUE, Tile.Color.RED,
    };
}
