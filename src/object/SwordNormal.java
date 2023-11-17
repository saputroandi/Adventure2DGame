package object;

import entity.Entity;
import main.GamePanel;

public class SwordNormal extends Entity {
    public SwordNormal(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init(){
        name = "NormalSword";
        down1 = getScaledImage("/objects/sword_normal", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
    }
}
