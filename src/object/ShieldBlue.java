package object;

import entity.Entity;
import main.GamePanel;

public class ShieldBlue extends Entity {

    public ShieldBlue(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typeShield;
        name = "Blue Shield";
        down1 = getScaledImage("/objects/shield_blue", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nShiny blue shield.";
        price = 80;
    }
}
