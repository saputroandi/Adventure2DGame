package entity;

import main.GamePanel;
import main.KeyHandler;
import main.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public final int screenX;

    public final int screenY;

    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.keyHandler = keyHandler;

        screenX = gamePanel.maxScreenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.maxScreenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 28;
        solidArea.width = 28;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage() {

        up1 = getScaledImage("/player/boy_up_1");
        up2 = getScaledImage("/player/boy_up_2");
        down1 = getScaledImage("/player/boy_down_1");
        down2 = getScaledImage("/player/boy_down_2");
        right1 = getScaledImage("/player/boy_right_1");
        right2 = getScaledImage("/player/boy_right_2");
        left1 = getScaledImage("/player/boy_left_1");
        left2 = getScaledImage("/player/boy_left_2");
    }

    public void update() {

        if ( keyHandler.upPressed ) {
            direction = "up";
        } else if ( keyHandler.downPressed ) {
            direction = "down";
        } else if ( keyHandler.rightPressed ) {
            direction = "right";
        } else if ( keyHandler.leftPressed ) {
            direction = "left";
        }

        if ( keyHandler.upPressed || keyHandler.downPressed || keyHandler.rightPressed || keyHandler.leftPressed ) {
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            int indexTile = gamePanel.collisionChecker.checkObject(this, true);
            int indexNpc = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);

            objectInteraction(indexTile);
            npcInteraction(indexNpc);

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
    }

    private void npcInteraction(int indexNpc) {

        if ( indexNpc != 999 ) {
            if ( gamePanel.keyHandler.enterPressed ) {
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[indexNpc].speak();
            }
        }
        gamePanel.keyHandler.enterPressed = false;
    }

    public void objectInteraction(int indexTile) {

        if ( indexTile != 999 ) {
//
        }
    }

    public void draw(Graphics2D graphics2D) {
//        graphics2D.setColor(Color.white);
//        graphics2D.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);

        BufferedImage image = null;

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

        graphics2D.drawImage(image, screenX, screenY, null);

    }
}
