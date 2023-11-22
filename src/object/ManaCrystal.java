package object;

import entity.Entity;
import main.GamePanel;

public class ManaCrystal extends Entity {

    public ManaCrystal(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        name = "Mana Crystal";
        image = getScaledImage("/objects/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = getScaledImage("/objects/manacrystal_blank", gamePanel.tileSize, gamePanel.tileSize);
    }
}
