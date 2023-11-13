package object;

import entity.Entity;
import main.GamePanel;

public class Door extends Entity {

    public Door(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        name = "Door";
        collision = true;
        down1 = getScaledImage("/objects/door");
    }
}
