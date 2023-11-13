package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {

    public Heart(GamePanel gamePanel) {

        super(gamePanel);
        init();
    }

    public void init() {

        name = "Heart";

        image = getScaledImage("/objects/heart_full");
        image2 = getScaledImage("/objects/heart_half");
        image3 = getScaledImage("/objects/heart_blank");
    }
}
