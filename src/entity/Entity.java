package entity;

import main.GamePanel;
import main.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public GamePanel gamePanel;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackRight1, attackRight2, attackLeft1, attackLeft2;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public int actionLockCounter = 0;
    public boolean invisible = false;
    public int invisibleCounter = 0;
    boolean hpBarOn = false;
    int hpBarCounter = 0;

    public boolean attacking = false;

    public int maxLife;
    public int life;
    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;

    public int dialogueIndex = 0;
    String[] dialogues = new String[20];

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision;

    public int type;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int level;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;

    public int attackValue;
    public int defenseValue;

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

    public void damageReaction(){

    }

    public void setAction() {

    }

    public void update() {

        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if ( this.type == 2 && contactPlayer ) {
            if ( !gamePanel.player.invisible ) {
                gamePanel.playSoundEffect(6);
                gamePanel.player.life -= 1;
                gamePanel.player.invisible = true;
            }
        }

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

        if ( invisible ) {
            invisibleCounter++;
            if ( invisibleCounter > 40 ) {
                invisible = false;
                invisibleCounter = 0;
            }
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

            if ( type == 2 && hpBarOn ){
                double oneScale = (double ) gamePanel.tileSize / maxLife;
                double hpValue = oneScale * life;

                graphics2D.setColor(new Color(35, 35, 35));
                graphics2D.fillRect(screenX - 1, screenY - 16, gamePanel.tileSize - 2, 12);

                graphics2D.setColor(new Color(255, 0, 30));
                graphics2D.fillRect(screenX, screenY - 15, (int) hpValue, 10);

                hpBarCounter++;

                if ( hpBarCounter > 600 ){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if ( invisible ) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(graphics2D, 0.4f);
            }

            if ( dying ) {
                dyingAnimation(graphics2D);
            }

            graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

            changeAlpha(graphics2D, 1f);
        }
    }

    public void dyingAnimation(Graphics2D graphics2D) {

        dyingCounter++;

        int i = 5;
        if ( dyingCounter <= i ) {
            changeAlpha(graphics2D, 0f);
        }
        if ( dyingCounter > i && dyingCounter <= i * 2 ) {
            changeAlpha(graphics2D, 1f);
        }
        if ( dyingCounter > i * 2 && dyingCounter <= i * 3 ) {
            changeAlpha(graphics2D, 0f);
        }
        if ( dyingCounter > i * 3 && dyingCounter <= i * 4 ) {
            changeAlpha(graphics2D, 1f);
        }
        if ( dyingCounter > i * 4 && dyingCounter <= i * 5 ) {
            changeAlpha(graphics2D, 0f);
        }
        if ( dyingCounter > i * 5 && dyingCounter <= i * 6 ) {
            changeAlpha(graphics2D, 1f);
        }
        if ( dyingCounter > i * 6 && dyingCounter <= i * 7 ) {
            changeAlpha(graphics2D, 0f);
        }
        if ( dyingCounter > i * 7 && dyingCounter <= i * 8 ) {
            changeAlpha(graphics2D, 1f);
        }
        if ( dyingCounter > i * 8 ) {
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D graphics2D, float alphaValue) {

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage getScaledImage(String imagePath, int width, int height) {

        Utility utility = new Utility();
        BufferedImage scaledImage = null;

        BufferedImage originalImage = utility.loadImage(imagePath + ".png");
        scaledImage = utility.scaleImage(originalImage, width, height);

        return scaledImage;
    }
}
