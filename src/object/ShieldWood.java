package object;

import entity.Entity;
import main.GamePanel;

public class ShieldWood extends Entity {
    public ShieldWood(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init(){
        name = "Wood Shield";
        down1 = getScaledImage("/objects/shield_wood", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nAn old sword.";
    }
}
