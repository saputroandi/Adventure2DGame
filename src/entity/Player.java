package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    public final int screenX;

    public final int screenY;

    GamePanel gamePanel;

    KeyHandler keyHandler;

    int keys = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        this.gamePanel = gamePanel;
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

        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png"))));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png")));

        } catch ( IOException error ) {
            error.printStackTrace();
        }
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

            int index = gamePanel.collisionChecker.checkObject(this, true);

            objectInteraction(index);

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

    public void objectInteraction(int index) {

        if ( index != 999 ) {
            String objectName = gamePanel.objects[index].name;

            switch ( objectName ) {
                case "Key":
                    gamePanel.objects[index] = null;
                    keys++;
                    break;
                case "Door":
                    if ( keys > 0 ) {
                        gamePanel.objects[index] = null;
                        keys--;
                    }
                    break;
            }
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

        graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

    }
}
