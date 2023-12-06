package object;

import entity.Entity;
import main.GamePanel;

public class Chest extends Entity {

    Entity loot;
    boolean opened = false;

    public Chest(GamePanel gamePanel, Entity loot) {

        super(gamePanel);
        this.loot = loot;
        init();
    }

    public void init() {

        type = typeObstacle;
        name = "Chest";
        image = getScaledImage("/objects/chest", gamePanel.tileSize, gamePanel.tileSize);
        image2 = getScaledImage("/objects/chest_opened", gamePanel.tileSize, gamePanel.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void interaction() {

        gamePanel.gameState = gamePanel.dialogueState;

        if ( !opened ) {
            gamePanel.playSoundEffect(3);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("U open the chest and find a ").append(loot.name).append("!");

            if ( !gamePanel.player.canObtainItem(loot) ) {
                stringBuilder.append("\nBut u cannot carry anymore!");
            } else {
                stringBuilder.append("\nU obtain the ").append(loot.name).append("!");
                down1 = image2;
                opened = true;
            }

            gamePanel.userInterface.currentDialogue = stringBuilder.toString();
        } else {
            gamePanel.userInterface.currentDialogue = "It's empty";
        }
    }
}
