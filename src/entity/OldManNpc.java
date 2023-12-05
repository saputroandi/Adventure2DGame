package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class OldManNpc extends Entity {

    public OldManNpc(GamePanel gamePanel) {

        super(gamePanel);

        init();
    }

    public void init() {

        direction = "down";
        speed = 1;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 28;
        solidArea.width = 28;

        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = getScaledImage("/npc/oldman_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = getScaledImage("/npc/oldman_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = getScaledImage("/npc/oldman_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = getScaledImage("/npc/oldman_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = getScaledImage("/npc/oldman_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = getScaledImage("/npc/oldman_right_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = getScaledImage("/npc/oldman_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = getScaledImage("/npc/oldman_left_2", gamePanel.tileSize, gamePanel.tileSize);
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

    public void setDialogue() {

        dialogues[0] = "Hello, Andi.";
    }

    public void speak() {

        super.speak();

        onPath = true;
    }
}
