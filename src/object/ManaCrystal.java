package object;

import entity.Entity;
import main.GamePanel;

public class ManaCrystal extends Entity {

    public ManaCrystal(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typePickupOnly;
        name = "Mana Crystal";
        value = 2;
        down1 = getScaledImage("/objects/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image = getScaledImage("/objects/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = getScaledImage("/objects/manacrystal_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public void use(Entity entity) {

        gamePanel.playSoundEffect(2);
        gamePanel.userInterface.addMessage("Mana + " + value);
        entity.mana += value;
    }
}
