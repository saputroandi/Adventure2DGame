package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends Object {

    public Chest(GamePanel gamePanel) {

        init(gamePanel);
    }

    public void init(GamePanel gamePanel) {

        name = "Chest";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
            utility.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch ( IOException error ) {
            error.printStackTrace();
        }
    }
}
