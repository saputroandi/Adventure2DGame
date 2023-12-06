package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {

    public Heart(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typePickupOnly;
        name = "Heart";
        value = 2;
        down1 = getScaledImage("/objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);

        image = getScaledImage("/objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = getScaledImage("/objects/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        image3 = getScaledImage("/objects/heart_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public boolean use(Entity entity) {

        gamePanel.playSoundEffect(2);
        gamePanel.userInterface.addMessage("Life + " + value);
        entity.life += value;
        return true;
    }
}
