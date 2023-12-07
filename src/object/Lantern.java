package object;

import entity.Entity;
import main.GamePanel;

public class Lantern extends Entity {
    public Lantern(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typeLight;
        name = "Lantern";
        down1 = getScaledImage("/objects/lantern", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nIlluminate ur\nsurroundings.";
        price = 100;
        lightRadius = 380;
    }
}
