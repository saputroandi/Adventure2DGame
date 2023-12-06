package object;

import entity.Entity;
import main.GamePanel;

public class Key extends Entity {

    public Key(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typeConsumable;
        name = "Key";
        down1 = getScaledImage("/objects/key", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nMade to open the door.";
        price = 10;
        stackable = true;
    }

    @Override
    public boolean use(Entity entity) {

        gamePanel.gameState = gamePanel.dialogueState;

        int indexObject = getDetected(entity, gamePanel.objects, "Door");
        boolean used = false;

        if ( indexObject != 999 ) {
            gamePanel.userInterface.currentDialogue = "U use the " + name + " to open the door.";
            gamePanel.playSoundEffect(3);
            gamePanel.objects[gamePanel.currentMap][indexObject] = null;
            used = true;
        } else {
            gamePanel.userInterface.currentDialogue = "What r u doing";
        }

        return used;
    }
}
