package main;

import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInterface {

    public String message = "";

    public boolean gameFinished = false;

    GamePanel gamePanel;

    Font font;

    BufferedImage keyImage;

    boolean messageOn = false;

    int messageCounter = 0;

    public UserInterface(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        this.font = new Font("Arial", Font.PLAIN, 40);

        Key key = new Key();

        this.keyImage = key.image;
    }

    public void drawMessage(String text) {

        message = text;

        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {

//        need to add messages when finished the game
        if ( gameFinished ) {
            int centerX = gamePanel.maxScreenWidth / 2;
        } else {
            graphics2D.setFont(this.font);
            graphics2D.setColor(Color.white);
            graphics2D.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);
            graphics2D.drawString("x = " + gamePanel.player.keys, 72, 65);

            if ( messageOn ) {

                graphics2D.setFont(gamePanel.getFont().deriveFont(30F));
                graphics2D.drawString(message, gamePanel.tileSize / 2, gamePanel.tileSize * 5);

                messageCounter++;

                if ( messageCounter > 120 ) {
                    messageOn = false;
                    messageCounter = 0;
                }
            }
        }


    }
}
