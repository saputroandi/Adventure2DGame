package object;

import entity.Entity;
import main.GamePanel;

public class Axe extends Entity {
    public Axe(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typeAxe;
        name = "Woodcutter's Axe";
        down1 = getScaledImage("/objects/axe", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nA bit rusty but still can\ncut trees.";
        price = 75;
    }
}
