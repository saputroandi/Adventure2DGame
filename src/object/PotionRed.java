package object;

import entity.Entity;
import main.GamePanel;

public class PotionRed extends Entity {

    public PotionRed(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typeConsumable;
        name = "Red Potion";
        value = 5;
        down1 = getScaledImage("/objects/potion_red", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nRed Potion\nHeals your life by " + value + ".";
    }

    @Override
    public void use(Entity entity) {

        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.userInterface.currentDialogue = "You drink the " + name + "!\n"
                + "Your life has been recovered by " + value + ".";

        entity.life += value;

        if ( gamePanel.player.life > gamePanel.player.maxLife ) {
            gamePanel.player.life = gamePanel.player.maxLife;
        }
        gamePanel.playSoundEffect(2);
    }
}
