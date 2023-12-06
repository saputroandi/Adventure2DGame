package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class Door extends Entity {

    public Door(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typeObstacle;
        name = "Door";
        collision = true;
        down1 = getScaledImage("/objects/door", gamePanel.tileSize, gamePanel.tileSize);

        solidArea.x = 0;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 28;
        solidArea.width = 48;
    }

    @Override
    public void interaction() {

        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.userInterface.currentDialogue = "U need a key to open this.";
    }
}
