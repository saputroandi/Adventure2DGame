package monster;

import entity.Entity;
import main.GamePanel;
import object.CoinBronze;
import object.Heart;
import object.ManaCrystal;
import object.Rock;

import java.util.Random;

public class Orc extends Entity {
    public Orc(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typeMonster;
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 10;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;

        getImage();
        getAttackImage();
    }

    public void getImage() {

        up1 = getScaledImage("/monster/orc_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = getScaledImage("/monster/orc_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = getScaledImage("/monster/orc_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = getScaledImage("/monster/orc_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = getScaledImage("/monster/orc_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = getScaledImage("/monster/orc_right_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = getScaledImage("/monster/orc_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = getScaledImage("/monster/orc_left_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void getAttackImage() {

        attackUp1 = getScaledImage("/monster/orc_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2 = getScaledImage("/monster/orc_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown1 = getScaledImage("/monster/orc_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2 = getScaledImage("/monster/orc_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackRight1 = getScaledImage("/monster/orc_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = getScaledImage("/monster/orc_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft1 = getScaledImage("/monster/orc_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2 = getScaledImage("/monster/orc_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
    }

    @Override
    public void setAction() {

        if ( onPath ){
            checkChasing(gamePanel.player, 8, 100, false);

            searchPath(getGoalCol(gamePanel.player), getGoalRow(gamePanel.player));
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
