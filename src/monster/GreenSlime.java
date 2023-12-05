package monster;

import entity.Entity;
import main.GamePanel;
import object.CoinBronze;
import object.Heart;
import object.ManaCrystal;
import object.Rock;

import java.util.Random;

public class GreenSlime extends Entity {

    public GreenSlime(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typeMonster;
        name = "Green Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new Rock(gamePanel);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        up1 = getScaledImage("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = getScaledImage("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = getScaledImage("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = getScaledImage("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = getScaledImage("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = getScaledImage("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = getScaledImage("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = getScaledImage("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void update() {

        super.update();

        int xDistance = Math.abs(worldX - gamePanel.player.worldX);
        int yDistance = Math.abs(worldY - gamePanel.player.worldY);
        int tileDistance = (xDistance + yDistance) / gamePanel.tileSize;

        if ( !onPath && tileDistance < 3 ) {

            int i = new Random().nextInt(100) + 1;
            if ( i > 50 ) {
                onPath = true;
            }
        }
        if ( onPath && tileDistance > 10 ) {
            onPath = false;
        }
    }

    @Override
    public void setAction() {

        if ( onPath ) {

//            int goalCol = 12;
//            int goalRow = 9;

            int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
            int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;
            ;

            searchPath(goalCol, goalRow);

            int i = new Random().nextInt(200) + 1;

            if ( i > 197 && !projectile.alive && shotAvailableCounter == 30 ) {
                projectile.set(worldX, worldY, direction, true, this);
//                gamePanel.projectiles.add(projectile);
                for ( int j = 0; j < gamePanel.projectiles[0].length; j++ ) {
                    if ( gamePanel.projectiles[gamePanel.currentMap][j] == null ) {
                        gamePanel.projectiles[gamePanel.currentMap][j] = projectile;
                        break;
                    }
                }
                shotAvailableCounter = 0;
            }
        } else {
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

    }

    @Override
    public void damageReaction() {

        actionLockCounter = 0;
//        direction = gamePanel.player.direction;
        onPath = true;
    }

    @Override
    public void checkDrop() {

        int i = new Random().nextInt(100) + 1;

        if ( i < 50 ) {
            dropItem(new CoinBronze(gamePanel));
        }

        if ( i >= 50 && i < 75 ) {
            dropItem(new Heart(gamePanel));
        }

        if ( i >= 75 && i < 100 ) {
            dropItem(new ManaCrystal(gamePanel));
        }
    }
}
