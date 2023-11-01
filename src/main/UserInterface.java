package main;

import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInterface {

    GamePanel gamePanel;

    Font font;

    BufferedImage keyImage;

    public UserInterface(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        this.font = new Font("Arial", Font.PLAIN, 40);

        Key key = new Key();

        this.keyImage = key.image;
    }

    public void draw(Graphics2D graphics2D) {

        graphics2D.setFont(this.font);
        graphics2D.setColor(Color.white);
        graphics2D.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);
        graphics2D.drawString("x = " + gamePanel.player.keys, 72, 65);
    }
}
