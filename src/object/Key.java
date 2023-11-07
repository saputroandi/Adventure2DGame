package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Key extends Object {

    public Key(GamePanel gamePanel) {

        init(gamePanel);
    }

    public void init(GamePanel gamePanel) {

        name = "Key";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
            utility.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch ( IOException error ) {
            error.printStackTrace();
        }
    }
}
