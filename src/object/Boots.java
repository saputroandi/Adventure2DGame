package object;

import entity.Entity;
import main.GamePanel;

public class Boots extends Entity {

    public Boots(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        name = "Boots";
        down1 = getScaledImage("/objects/boots", gamePanel.tileSize, gamePanel.tileSize);
    }
}
