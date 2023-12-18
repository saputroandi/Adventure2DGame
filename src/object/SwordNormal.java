package object;

import entity.Entity;
import main.GamePanel;

public class SwordNormal extends Entity {
    public SwordNormal(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        type = typeSword;
        name = "NormalSword";
        down1 = getScaledImage("/objects/sword_normal", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old sword.";
        price = 75;
        knockBackPower = 2;
        motionDuration1 = 5;
        motionDuration2 = 25;
    }
}
