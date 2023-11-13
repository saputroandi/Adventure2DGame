package object;

import entity.Entity;
import main.GamePanel;

public class Key extends Entity {


    public Key(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        name = "Key";
        down1 = getScaledImage("/objects/key");
    }
}
