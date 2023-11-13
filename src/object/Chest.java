package object;

import entity.Entity;
import main.GamePanel;

public class Chest extends Entity {

    public Chest(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        name = "Chest";
        down1 = getScaledImage("/objects/chest");
    }
}
