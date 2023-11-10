package entity;

import main.GamePanel;
import main.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int dialogueIndex = 0;

    public int worldX, worldY;

    public int speed;

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;

    public String direction;

    public int spriteCounter = 0;

    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public int solidAreaDefaultX, solidAreaDefaultY;

    public boolean collisionOn = false;

    public int actionLockCounter = 0;

    public int maxLife;

    public int life;

    String[] dialogues = new String[20];

    GamePanel gamePanel;

    public Entity(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    public void speak() {

        if ( dialogues[dialogueIndex] == null ) {
            dialogueIndex = 0;
        }
        gamePanel.userInterface.currentDialogue = dialogues[0];
        dialogueIndex++;

        switch ( gamePanel.player.direction ) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void setAction() {

    }

    public void update() {

        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkPlayer(this);

        if ( !collisionOn ) {
            switch ( direction ) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
            }
        }

        spriteCounter++;
        if ( spriteCounter > 10 ) {
            if ( spriteNum == 1 ) {
                spriteNum = 2;
            } else if ( spriteNum == 2 ) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D graphics2D) {

        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if ( worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY ) {

            switch ( direction ) {
                case "up":
                    if ( spriteNum == 1 ) {
                        image = up1;
                    }
                    if ( spriteNum == 2 ) {
                        image = up2;
                    }
                    break;
                case "down":
                    if ( spriteNum == 1 ) {
                        image = down1;
                    }
                    if ( spriteNum == 2 ) {
                        image = down2;
                    }
                    break;
                case "right":
                    if ( spriteNum == 1 ) {
                        image = right1;
                    }
                    if ( spriteNum == 2 ) {
                        image = right2;
                    }
                    break;
                case "left":
                    if ( spriteNum == 1 ) {
                        image = left1;
                    }
                    if ( spriteNum == 2 ) {
                        image = left2;
                    }
                    break;
            }

            graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

    public BufferedImage getScaledImage(String imagePath) {

        Utility utility = new Utility();
        BufferedImage scaledImage = null;

        BufferedImage originalImage = utility.loadImage(imagePath + ".png");
        scaledImage = utility.scaleImage(originalImage, gamePanel.tileSize, gamePanel.tileSize);

        return scaledImage;
    }
}
