package main;

import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInterface {

    public String message = "";

    public boolean gameFinished = false;

    GamePanel gamePanel;

    Font font_40, font_80B;

    BufferedImage keyImage;

    boolean messageOn = false;

    int messageCounter = 0;

    public UserInterface(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        this.font_40 = new Font("Arial", Font.PLAIN, 40);
        this.font_80B = new Font("Arial", Font.BOLD, 80);

        Key key = new Key(gamePanel);

        this.keyImage = key.image;
    }

    public void drawMessage(String text) {

        message = text;

        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {

//        need to add messages when finished the game
        if ( gameFinished ) {
            String text;
            int textLength;
            int x;
            int y;

            graphics2D.setFont(this.font_40);
            graphics2D.setColor(Color.white);

            text = "You found the treasure!";
            textLength = ( int ) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

            x = gamePanel.maxScreenWidth / 2 - textLength / 2;
            y = gamePanel.maxScreenHeight / 2 - (gamePanel.tileSize * 3);
            graphics2D.drawString(text, x, y);

            graphics2D.setFont(this.font_80B);
            graphics2D.setColor(Color.yellow);

            text = "Congratulation";
            textLength = ( int ) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

            x = gamePanel.maxScreenWidth / 2 - textLength / 2;
            y = gamePanel.maxScreenHeight / 2 + (gamePanel.tileSize * 3);

            graphics2D.drawString(text, x, y);

            gamePanel.gameThread = null;

        } else {
            graphics2D.setFont(this.font_40);
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
