package entity;

import main.GamePanel;
import main.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Entity {

    public GamePanel gamePanel;
    public int worldX, worldY;

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackRight1, attackRight2, attackLeft1, attackLeft2;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public Entity attacker;

    public int actionLockCounter = 0;
    public boolean invisible = false;
    public int invisibleCounter = 0;
    public int shotAvailableCounter = 0;
    boolean hpBarOn = false;
    int hpBarCounter = 0;
    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;

    public boolean attacking = false;

    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;
    public int knockBackCounter = 0;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public int dialogueIndex = 0;
    String[] dialogues = new String[20];

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision;

    public int defaultSpeed;
    public int speed;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int level;
    public int nextLevelExp;
    public int coin;
    public int motionDuration1;
    public int motionDuration2;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;

    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;

    public int type;
    public final int typePlayer = 0;
    public final int typeNpc = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeAxe = 4;
    public final int typeShield = 5;
    public final int typeConsumable = 6;
    public final int typePickupOnly = 7;
    public final int typeObstacle = 8;
    public final int typeLight = 9;

    public Entity(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    public int getLeftX() {

        return worldX + solidArea.x;
    }

    public int getRightX() {

        return worldX + solidArea.x + solidArea.width;
    }

    public int getTopY() {

        return worldY + solidArea.y;
    }

    public int getBottomY() {

        return worldY + solidArea.y + solidArea.height;
    }

    public int getCol() {

        return (worldX + solidArea.x) / gamePanel.tileSize;
    }

    public int getRow() {

        return (worldY + solidArea.y) / gamePanel.tileSize;
    }

    public int getXDistance(Entity target){
        return Math.abs(worldX - target.worldX);
    }

    public int getYDistance(Entity target){
        return Math.abs(worldY - target.worldY);
    }

    public int getTileDistance(Entity target){
        return (getXDistance(target) + getYDistance(target)) / gamePanel.tileSize ;
    }

    public int getGoalCol(Entity target){
        return (target.worldX + target.solidArea.x) / gamePanel.tileSize;
    }

    public int getGoalRow(Entity target){
        return (target.worldY + target.solidArea.y) / gamePanel.tileSize;
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

    public void interaction() {

    }

    public boolean use(Entity entity) {

        return false;
    }

    public void damageReaction() {

    }

    public void setAction() {

    }

    public void checkDrop() {

    }

    public int getDetected(Entity user, Entity[][] targets, String targetName) {

        int index = 999;

        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch ( user.direction ) {
            case "up":
                nextWorldY = user.getTopY() - 1;
                break;
            case "down":
                nextWorldY = user.getBottomY() + 1;
                break;
            case "left":
                nextWorldX = user.getLeftX() - 1;
                break;
            case "right":
                nextWorldX = user.getRightX() + 1;
                break;
        }

        int col = nextWorldX / gamePanel.tileSize;
        int row = nextWorldY / gamePanel.tileSize;

        for ( int i = 0; i < targets[0].length; i++ ) {
            if ( targets[gamePanel.currentMap][i] != null ) {
                if ( targets[gamePanel.currentMap][i].getCol() == col && targets[gamePanel.currentMap][i].getRow() == row && targets[gamePanel.currentMap][i].name.equals(targetName) ) {
                    index = i;
                    break;
                }
            }
        }

        return index;
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

        if ( knockBack ) {

            checkCollision();
            if ( collisionOn ) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else if ( !collisionOn ) {
                switch ( knockBackDirection ) {
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

            knockBackCounter++;
            if ( knockBackCounter == 10 ) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        } else if ( attacking ) {
            attacking();
        } else {
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

    public void checkChasing(Entity target, int distance, int rate, boolean startChasing) {
        int tileDistance = getTileDistance(target);
        boolean condition = (startChasing) ? (tileDistance < distance) : (tileDistance > distance);

        int randomInt = new Random().nextInt(rate);
        if (randomInt == 0 && condition) {
            onPath = startChasing;
        }
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal){

        boolean targetInRange = false;
        int xDistance = getXDistance(gamePanel.player);
        int yDistance = getYDistance(gamePanel.player);

        switch ( direction ){
            case "up":
                if ( gamePanel.player.worldY < worldY && yDistance < straight && xDistance < horizontal ){
                    targetInRange = true;
                }
                break;
            case "down":
                if ( gamePanel.player.worldY > worldY && yDistance < straight && xDistance < horizontal ){
                    targetInRange = true;
                }
                break;
            case "right":
                if ( gamePanel.player.worldX > worldX && xDistance < straight && yDistance < horizontal ){
                    targetInRange = true;
                }
                break;
            case "left":
                if ( gamePanel.player.worldX < worldX && xDistance < straight && yDistance < horizontal ){
                    targetInRange = true;
                }
                break;
        }

        if ( targetInRange ){
            int i = new Random().nextInt(rate);

            if ( i == 0 ){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }

    public void checkShootOrNot(int rate, int setInterval){
        int i = new Random().nextInt(rate);

        if ( i == 0 && !projectile.alive && shotAvailableCounter == setInterval ) {
            projectile.set(worldX, worldY, direction, true, this);
            for ( int j = 0; j < gamePanel.projectiles[0].length; j++ ) {
                if ( gamePanel.projectiles[gamePanel.currentMap][j] == null ) {
                    gamePanel.projectiles[gamePanel.currentMap][j] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }

    public void getRandomDirection(){

        actionLockCounter++;
        if ( actionLockCounter > 120 ) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if ( i <= 25 ) {
                direction = "up";
            } else if ( i <= 50 ) {
                direction = "down";
            } else if ( i <= 75 ) {
                direction = "left";
            } else {
                direction = "right";
            }

            actionLockCounter = 0;
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

    public void attacking() {

        spriteCounter++;

        if ( spriteCounter <= motionDuration1 ) {
            spriteNum = 1;
        } else if ( spriteCounter <= motionDuration2 ) {
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

            if ( type == typeMonster ){

                if ( gamePanel.collisionChecker.checkPlayer(this) ){
                    damagePlayer(attack);
                }

            } else {
                int indexMonster = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
                int indexInteractiveTile = gamePanel.collisionChecker.checkEntity(this, gamePanel.interactiveTiles);
                int indexProjectile = gamePanel.collisionChecker.checkEntity(this, gamePanel.projectiles);

                gamePanel.player.damageMonster(indexMonster, this, attack, currentWeapon.knockBackPower);
                gamePanel.player.damageInteractiveTile(indexInteractiveTile);
                gamePanel.player.damageProjectile(indexProjectile);
            }



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

    public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {

        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }

    public void draw(Graphics2D graphics2D) {

        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if ( worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY ) {

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

            graphics2D.drawImage(image, tempScreenX, tempScreenY, null);

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

        if ( !gamePanel.tileManager.drawPath ) {
            gamePanel.tileManager.drawPath = true;
        }

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

//            int nextCol = gamePanel.pathFinder.pathList.get(0).col;
//            int nextRow = gamePanel.pathFinder.pathList.get(0).row;
//
//            if ( nextCol == goalCol && nextRow == goalRow ) {
//                onPath = false;
//                if ( gamePanel.tileManager.drawPath ) {
//                    gamePanel.tileManager.drawPath = false;
//                }
//            }
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
