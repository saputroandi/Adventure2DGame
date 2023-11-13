package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {

    public Heart(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        name = "Heart";

        image = getScaledImage("/objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = getScaledImage("/objects/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        image3 = getScaledImage("/objects/heart_blank", gamePanel.tileSize, gamePanel.tileSize);
    }
}
