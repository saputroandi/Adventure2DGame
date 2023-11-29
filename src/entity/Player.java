package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public final int screenX;
    public final int screenY;
    public boolean attackCancel = false;

    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 28;
        solidArea.width = 28;

//        attackArea.width = 36;
//        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {

//        worldX = gamePanel.tileSize * 23;
//        worldY = gamePanel.tileSize * 21;

        gamePanel.currentMap = 1;
        worldX = gamePanel.tileSize * 12;
        worldY = gamePanel.tileSize * 10;
        speed = 3;
        direction = "down";

        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;

        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new SwordNormal(gamePanel);
        currentShield = new ShieldWood(gamePanel);

        attack = getAttack();
        defense = getDefense();

        projectile = new Fireball(gamePanel);
//        projectile = new Rock(gamePanel);

    }

    public void setDefaultPosition() {

        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        direction = "down";
    }

    public void restoreLifeAndMana() {

        life = maxLife;
        mana = maxMana;
        invisible = false;
    }

    public void setItems() {

        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new Key(gamePanel));
    }

    public int getAttack() {

        attackArea = currentWeapon.attackArea;
        return strength * currentWeapon.attackValue;
    }

    public int getDefense() {

        return dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {

        up1 = getScaledImage("/player/boy_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = getScaledImage("/player/boy_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = getScaledImage("/player/boy_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = getScaledImage("/player/boy_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = getScaledImage("/player/boy_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = getScaledImage("/player/boy_right_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = getScaledImage("/player/boy_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = getScaledImage("/player/boy_left_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void getPlayerAttackImage() {

        if ( currentWeapon.type == typeSword ) {
            attackUp1 = getScaledImage("/player/boy_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = getScaledImage("/player/boy_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = getScaledImage("/player/boy_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = getScaledImage("/player/boy_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackRight1 = getScaledImage("/player/boy_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = getScaledImage("/player/boy_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft1 = getScaledImage("/player/boy_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = getScaledImage("/player/boy_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        }

        if ( currentWeapon.type == typeAxe ) {
            attackUp1 = getScaledImage("/player/boy_axe_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = getScaledImage("/player/boy_axe_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = getScaledImage("/player/boy_axe_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = getScaledImage("/player/boy_axe_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackRight1 = getScaledImage("/player/boy_axe_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = getScaledImage("/player/boy_axe_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft1 = getScaledImage("/player/boy_axe_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = getScaledImage("/player/boy_axe_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        }
    }

    public void update() {

        if ( attacking ) {
            attacking();
        } else if ( keyHandler.upPressed || keyHandler.downPressed || keyHandler.rightPressed || keyHandler.leftPressed || keyHandler.enterPressed || keyHandler.spacePressed ) {

            if ( keyHandler.upPressed ) {
                direction = "up";
            } else if ( keyHandler.downPressed ) {
                direction = "down";
            } else if ( keyHandler.rightPressed ) {
                direction = "right";
            } else if ( keyHandler.leftPressed ) {
                direction = "left";
            }

            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            int indexItem = gamePanel.collisionChecker.checkObject(this, true);
            int indexNpc = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            int indexMonster = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
            gamePanel.collisionChecker.checkEntity(this, gamePanel.interactiveTiles);

            gamePanel.eventHandler.checkEvent();

            objectInteraction(indexItem);
            npcInteraction(indexNpc);
            monsterInteraction(indexMonster);

            if ( !collisionOn && !keyHandler.enterPressed ) {
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

            if ( (keyHandler.enterPressed || keyHandler.spacePressed) && !attackCancel ) {
                gamePanel.playSoundEffect(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCancel = false;
            keyHandler.enterPressed = false;

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

        if ( keyHandler.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveResource(this) ) {
            projectile.set(worldX, worldY, direction, true, this);

            projectile.subtractResource(this);

            gamePanel.projectiles.add(projectile);

            shotAvailableCounter = 0;

            gamePanel.playSoundEffect(10);
        }

        if ( invisible ) {
            invisibleCounter++;
            if ( invisibleCounter > 60 ) {
                invisible = false;
                invisibleCounter = 0;
            }
        }

        if ( shotAvailableCounter < 30 ) {
            shotAvailableCounter++;
        }

        if ( life > maxLife ) {
            life = maxLife;
        }

        if ( mana > maxMana ) {
            mana = maxMana;
        }

        if ( life <= 0 ) {
            gamePanel.gameState = gamePanel.gameOverState;
            gamePanel.playSoundEffect(12);
        }
    }

    public void attacking() {

        spriteCounter++;

        if ( spriteCounter <= 5 ) {
            spriteNum = 1;
        } else if ( spriteCounter <= 25 ) {
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch ( direction ) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int indexMonster = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
            int indexInteractiveTile = gamePanel.collisionChecker.checkEntity(this, gamePanel.interactiveTiles);

            damageMonster(indexMonster, attack);
            damageInteractiveTile(indexInteractiveTile);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        } else {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void damageMonster(int indexMonster, int attack) {

        if ( indexMonster != 999 ) {
            if ( !gamePanel.monsters[gamePanel.currentMap][indexMonster].invisible ) {

                gamePanel.playSoundEffect(5);
                int damage = attack - gamePanel.monsters[gamePanel.currentMap][indexMonster].defense;

                if ( damage < 0 ) {
                    damage = 0;
                }
                gamePanel.monsters[gamePanel.currentMap][indexMonster].life -= damage;

                gamePanel.userInterface.addMessage(damage + "damage!");

                gamePanel.monsters[gamePanel.currentMap][indexMonster].invisible = true;
                gamePanel.monsters[gamePanel.currentMap][indexMonster].damageReaction();

                if ( gamePanel.monsters[gamePanel.currentMap][indexMonster].life <= 0 ) {
//                    gamePanel.monsters[indexMonster] = null;
                    gamePanel.monsters[gamePanel.currentMap][indexMonster].dying = true;
                    gamePanel.userInterface.addMessage("Killed the " + gamePanel.monsters[gamePanel.currentMap][indexMonster].name + "!");
                    exp += gamePanel.monsters[gamePanel.currentMap][indexMonster].exp;
                    gamePanel.userInterface.addMessage("Exp + " + gamePanel.monsters[gamePanel.currentMap][indexMonster].exp);
                    checkLevelUp();
                }
            }
        }
    }

    public void checkLevelUp() {

        if ( exp >= nextLevelExp ) {

            level++;
            exp = exp - nextLevelExp;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gamePanel.playSoundEffect(8);
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.userInterface.currentDialogue = "You are level " + level + " now\n"
                    + "You become stronger";
        }
    }

    public void monsterInteraction(int indexMonster) {

        if ( indexMonster != 999 ) {
            if ( !invisible && !gamePanel.monsters[gamePanel.currentMap][indexMonster].dying ) {
                gamePanel.playSoundEffect(6);

                int damage = gamePanel.monsters[gamePanel.currentMap][indexMonster].attack - defense;

                if ( damage < 0 ) {
                    damage = 0;
                }

                life -= damage;
                invisible = true;
            }
        }
    }

    public void damageInteractiveTile(int indexInteractiveTile) {

        if ( indexInteractiveTile != 999 && gamePanel.interactiveTiles[gamePanel.currentMap][indexInteractiveTile].destructible && gamePanel.interactiveTiles[gamePanel.currentMap][indexInteractiveTile].isCorrectItem(this) && !gamePanel.interactiveTiles[gamePanel.currentMap][indexInteractiveTile].invisible ) {
            gamePanel.interactiveTiles[gamePanel.currentMap][indexInteractiveTile].playSoundEffect();
            gamePanel.interactiveTiles[gamePanel.currentMap][indexInteractiveTile].life--;
            gamePanel.interactiveTiles[gamePanel.currentMap][indexInteractiveTile].invisible = true;

            generateParticle(gamePanel.interactiveTiles[gamePanel.currentMap][indexInteractiveTile], gamePanel.interactiveTiles[gamePanel.currentMap][indexInteractiveTile]);

            if ( gamePanel.interactiveTiles[gamePanel.currentMap][indexInteractiveTile].life == 0 ) {
                gamePanel.interactiveTiles[gamePanel.currentMap][indexInteractiveTile] = gamePanel.interactiveTiles[gamePanel.currentMap][indexInteractiveTile].getDestroyedForm();
            }
        }
    }

    private void npcInteraction(int indexNpc) {

        if ( gamePanel.keyHandler.enterPressed || gamePanel.keyHandler.spacePressed ) {
            if ( indexNpc != 999 ) {
                attackCancel = true;
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[gamePanel.currentMap][indexNpc].speak();
            }
        }

        gamePanel.keyHandler.enterPressed = false;
    }

    public void objectInteraction(int indexItem) {

        if ( indexItem != 999 ) {

            if ( gamePanel.objects[gamePanel.currentMap][indexItem].type == typePickupOnly ) {
                gamePanel.objects[gamePanel.currentMap][indexItem].use(this);
                gamePanel.objects[gamePanel.currentMap][indexItem] = null;
            } else {
                String text;
                if ( inventory.size() != maxInventorySize ) {
                    inventory.add(gamePanel.objects[gamePanel.currentMap][indexItem]);
                    gamePanel.playSoundEffect(1);

                    text = "Got a " + gamePanel.objects[gamePanel.currentMap][indexItem].name;
                } else {
                    text = "You cannot carry anymore!";
                }

                gamePanel.userInterface.addMessage(text);
                gamePanel.objects[gamePanel.currentMap][indexItem] = null;
            }
        }
    }

    public void selectItem() {

        int indexItem = gamePanel.userInterface.getItemIndexOnInventory(gamePanel.userInterface.playerSlotCol, gamePanel.userInterface.playerSlotRow);

        if ( indexItem < inventory.size() ) {

            Entity selectedItem = inventory.get(indexItem);

            if ( selectedItem.type == typeSword || selectedItem.type == typeAxe ) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }

            if ( selectedItem.type == typeShield ) {
                currentShield = selectedItem;
                defense = getDefense();
            }

            if ( selectedItem.type == typeConsumable ) {
                selectedItem.use(this);

                inventory.remove(indexItem);
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
//        graphics2D.setColor(Color.white);
//        graphics2D.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);

        BufferedImage image = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch ( direction ) {
            case "up":
                if ( !attacking ) {
                    if ( spriteNum == 1 ) {
                        image = up1;
                    }
                    if ( spriteNum == 2 ) {
                        image = up2;
                    }
                }

                if ( attacking ) {
                    tempScreenY = screenY - gamePanel.tileSize;
                    if ( spriteNum == 1 ) {
                        image = attackUp1;
                    }
                    if ( spriteNum == 2 ) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if ( !attacking ) {
                    if ( spriteNum == 1 ) {
                        image = down1;
                    }
                    if ( spriteNum == 2 ) {
                        image = down2;
                    }
                }
                if ( attacking ) {
                    if ( spriteNum == 1 ) {
                        image = attackDown1;
                    }
                    if ( spriteNum == 2 ) {
                        image = attackDown2;
                    }
                }
                break;
            case "right":
                if ( !attacking ) {
                    if ( spriteNum == 1 ) {
                        image = right1;
                    }
                    if ( spriteNum == 2 ) {
                        image = right2;
                    }
                }

                if ( attacking ) {
                    if ( spriteNum == 1 ) {
                        image = attackRight1;
                    }
                    if ( spriteNum == 2 ) {
                        image = attackRight2;
                    }
                }
                break;
            case "left":
                if ( !attacking ) {
                    if ( spriteNum == 1 ) {
                        image = left1;
                    }
                    if ( spriteNum == 2 ) {
                        image = left2;
                    }
                }

                if ( attacking ) {
                    tempScreenX = screenX - gamePanel.tileSize;
                    if ( spriteNum == 1 ) {
                        image = attackLeft1;
                    }
                    if ( spriteNum == 2 ) {
                        image = attackLeft2;
                    }
                }
                break;
        }

        if ( invisible ) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        graphics2D.drawImage(image, tempScreenX, tempScreenY, null);

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
