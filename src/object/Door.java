package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Door extends Object {

    public Door(GamePanel gamePanel) {

        init(gamePanel);
    }

    public void init(GamePanel gamePanel) {

        name = "Door";

        collision = true;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));
            utility.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch ( IOException error ) {
            error.printStackTrace();
        }
    }
}
