package main;

import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInterface {

    public String message = "";

    public boolean gameFinished = false;

    GamePanel gamePanel;

    Graphics2D graphics2D;

    Font font_40, font_80B;

//    BufferedImage keyImage;

    boolean messageOn = false;

    int messageCounter = 0;

    public UserInterface(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        this.font_40 = new Font("Arial", Font.PLAIN, 40);
        this.font_80B = new Font("Arial", Font.BOLD, 80);

//        Key key = new Key(gamePanel);
//        this.keyImage = key.image;
    }

    public void drawMessage(String text) {

        message = text;

        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {

        this.graphics2D = graphics2D;

        graphics2D.setFont(font_40);
        graphics2D.setColor(Color.WHITE);

        if ( gamePanel.gameState == gamePanel.playState ) {

        } else if ( gamePanel.gameState == gamePanel.pauseState ) {
            drawPauseScreen();
        }
    }

    public void drawPauseScreen() {

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getCenterOfText(text);
        int y = gamePanel.maxScreenHeight / 2;

        graphics2D.drawString(text, x, y);
    }

    public int getCenterOfText(String text) {

        int length = ( int ) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gamePanel.maxScreenWidth / 2 - length / 2;
    }
}
