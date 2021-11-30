package com.ooad.hw1.utility;

import java.awt.*;

/**
 * Helper interface defines all the constants is used in our game.
 * Screen width, height, images' width, height and their paths' defined here.
 */
public interface Helper {
    int WIDTH = 1280, HEIGHT = 1000;
    long MS = 1000L;
    int FPS = 60;
    int MOVEMENT_SPEED = 8;
    int CHAR_START_Y_LOC = 502;

    int girlWidth = 65;
    int girlHeight = 100;
    int sansWidth = 61;
    int sansHeight = 80;
    int obstacleHeight = 64;
    int obstacleWidth = 43;

    int powerUpSize = 64;

    String gameName = "Ucan TEKME - OOAD HW1";
    String backgroundPath = "/com/ooad/hw1/assets/background.png";
    String sansPath = "/com/ooad/hw1/assets/SansSprite.png";
    String girlFace = "/com/ooad/hw1/assets/UndertaleGirlFace.png";
    String girlSide = "/com/ooad/hw1/assets/UndertaleGirlSide.png";
    String obstaclePath = "/com/ooad/hw1/assets/obstacleSprite.png";
    String x2Path = "/com/ooad/hw1/assets/sprite2x.png";
    String x5Path = "/com/ooad/hw1/assets/sprite5x.png";
    String x10Path = "/com/ooad/hw1/assets/sprite10x.png";
    String jumpPowerUpPath = "/com/ooad/hw1/assets/spritejump.png";
    String heartPath = "/com/ooad/hw1/assets/spriteHeart.png";
    String smallPausePath = "/com/ooad/hw1/assets/smallPauseButton.png";
    String newGameButtonPath = "/com/ooad/hw1/assets/newGame.png";
    String closeButtonPath = "/com/ooad/hw1/assets/closeButton.png";
    String gameOverPath = "/com/ooad/hw1/assets/gameOver.png";
    String logLayout = "/com/ooad/hw1/assets/logLayout.png";

    Font font = new Font("SansSerif", Font.BOLD, 24);
    Font fontLog = new Font("Tahoma", Font.BOLD, 18);
}
