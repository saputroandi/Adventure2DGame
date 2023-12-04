package entity;

import main.GamePanel;
import main.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    public int shotAvailableCounter = 0;
    boolean hpBarOn = false;
    int hpBarCounter = 0;
    public boolean onPath = false;

    public boolean attacking = false;

    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public int dialogueIndex = 0;
    String[] dialogues = new String[20];

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision;

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
    public Projectile projectile;

    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;

    public int type;
    public final int typePlayer = 0;
    public final int typeNpc = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeAxe = 4;
    public final int typeShield = 5;
    public final int typeConsumable = 6;
    public final int typePickupOnly = 7;

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

    public void use(Entity entity) {

    }

    public void damageReaction() {

    }

    public void setAction() {

    }

    public void checkDrop() {

    }

    public void dropItem(Entity droppedItem) {

        for ( int i = 0; i < gamePanel.objects[1].length; i++ ) {
            if ( gamePanel.objects[gamePanel.currentMap][i] == null ) {
                gamePanel.objects[gamePanel.currentMap][i] = droppedItem;
                gamePanel.objects[gamePanel.currentMap][i].worldX = worldX; // the X of dead monster position
                gamePanel.objects[gamePanel.currentMap][i].worldY = worldY; // the Y of dead monster position
                break;
            }
        }
    }

    public Color getParticleColor() {

        return null;
    }

    public int getParticleSize() {

        return 0;
    }

    public int getParticleSpeed() {

        return 0;
    }

    public int getParticleMaxLife() {

        return 0;
    }

    public void generateParticle(Entity generator, Entity target) {

        Color particleColor = generator.getParticleColor();
        int particleSize = generator.getParticleSize();
        int particleSpeed = generator.getParticleSpeed();
        int particleMaxLife = generator.getParticleMaxLife();

        Particle particle1 = new Particle(gamePanel, target, particleColor, particleSize, particleSpeed, particleMaxLife, -2, -1);
        Particle particle2 = new Particle(gamePanel, target, particleColor, particleSize, particleSpeed, particleMaxLife, 2, -1);
        Particle particle3 = new Particle(gamePanel, target, particleColor, particleSize, particleSpeed, particleMaxLife, -2, 1);
        Particle particle4 = new Particle(gamePanel, target, particleColor, particleSize, particleSpeed, particleMaxLife, 2, 1);

        gamePanel.particles.add(particle1);
        gamePanel.particles.add(particle2);
        gamePanel.particles.add(particle3);
        gamePanel.particles.add(particle4);
    }

    public void update() {

        setAction();
        checkCollision();

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
        if ( spriteCounter > 24 ) {
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

        if ( shotAvailableCounter < 30 ) {
            shotAvailableCounter++;
        }
    }

    public void checkCollision() {

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.interactiveTiles);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if ( this.type == typeMonster && contactPlayer ) {
            damagePlayer(attack);
        }
    }

    public void damagePlayer(int attack) {

        if ( !gamePanel.player.invisible ) {
            gamePanel.playSoundEffect(6);

            int damage = attack - gamePanel.player.defense;

            if ( damage < 0 ) {
                damage = 0;
            }
            gamePanel.player.life -= damage;
            gamePanel.player.invisible = true;
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

            if ( type == 2 && hpBarOn ) {
                double oneScale = ( double ) gamePanel.tileSize / maxLife;
                double hpValue = oneScale * life;

                graphics2D.setColor(new Color(35, 35, 35));
                graphics2D.fillRect(screenX - 1, screenY - 16, gamePanel.tileSize - 2, 12);

                graphics2D.setColor(new Color(255, 0, 30));
                graphics2D.fillRect(screenX, screenY - 15, ( int ) hpValue, 10);

                hpBarCounter++;

                if ( hpBarCounter > 600 ) {
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

            graphics2D.drawImage(image, screenX, screenY, null);

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
            alive = false;
        }
    }

    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gamePanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamePanel.tileSize;

        gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if ( gamePanel.pathFinder.search() ) {

            int nextX = gamePanel.pathFinder.pathList.get(0).col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.get(0).row * gamePanel.tileSize;

            int entityLeftX = worldX + solidArea.x;
            int entityRightX = worldX + solidArea.x + solidArea.width;
            int entityTopY = worldY + solidArea.y;
            int entityBottomY = worldY + solidArea.y + solidArea.height;

            if ( entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX + gamePanel.tileSize ) {
                direction = "up";
            } else if ( entityTopY < nextY && entityLeftX >= nextX && entityRightX < nextX + gamePanel.tileSize ) {
                direction = "down";
            } else if ( entityTopY >= nextY && entityBottomY < nextY + gamePanel.tileSize ) {
                if ( entityLeftX > nextX ) {
                    direction = "left";
                }

                if ( entityLeftX < nextX ) {
                    direction = "right";
                }
            } else if ( entityTopY > nextY && entityLeftX > nextX ) {
                direction = "up";
                checkCollision();
                if ( collisionOn ) {
                    direction = "left";
                }
            } else if ( entityTopY > nextY && entityLeftX < nextX ) {
                direction = "up";
                checkCollision();
                if ( collisionOn ) {
                    direction = "right";
                }
            } else if ( entityTopY < nextY && entityLeftX > nextX ) {
                direction = "down";
                checkCollision();
                if ( collisionOn ) {
                    direction = "left";
                }
            } else if ( entityTopY < nextY && entityLeftX < nextX ) {
                direction = "down";
                checkCollision();
                if ( collisionOn ) {
                    direction = "right";
                }
            }

            int nextCol = gamePanel.pathFinder.pathList.get(0).col;
            int nextRow = gamePanel.pathFinder.pathList.get(0).row;

            if ( nextCol == goalCol && nextRow == goalRow ) {
                onPath = false;
                gamePanel.tileManager.drawPath = false;
            }
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
