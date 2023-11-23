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
        speed = 1;
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

    @Override
    public void setAction() {

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
        int i = new Random().nextInt(100) + 1;

        if ( i > 99 && !projectile.alive && shotAvailableCounter == 30 ) {
            projectile.set(worldX, worldY, direction, true, this);
            gamePanel.projectiles.add(projectile);
            shotAvailableCounter = 0;
        }
    }

    @Override
    public void damageReaction() {

        actionLockCounter = 0;
        direction = gamePanel.player.direction;
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
