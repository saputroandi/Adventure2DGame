package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class GreenSlime extends Entity {

    public GreenSlime(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = 2;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        up1 = getScaledImage("/monster/greenslime_down_1");
        up2 = getScaledImage("/monster/greenslime_down_2");
        down1 = getScaledImage("/monster/greenslime_down_1");
        down2 = getScaledImage("/monster/greenslime_down_2");
        right1 = getScaledImage("/monster/greenslime_down_1");
        right2 = getScaledImage("/monster/greenslime_down_2");
        left1 = getScaledImage("/monster/greenslime_down_1");
        left2 = getScaledImage("/monster/greenslime_down_2");
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
    }
}
