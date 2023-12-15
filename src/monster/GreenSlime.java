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

    @Override
    public void setAction() {

        if ( onPath ){
            checkChasing(gamePanel.player, 8, 100, false);

            searchPath(getGoalCol(gamePanel.player), getGoalRow(gamePanel.player));

            checkShootOrNot(200, 30);
        } else {
            checkChasing(gamePanel.player, 5, 100, true);

            getRandomDirection();
        }
    }

    @Override
    public void damageReaction() {

        actionLockCounter = 0;
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
