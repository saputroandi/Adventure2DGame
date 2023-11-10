package object;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Heart extends Object {

    public Heart(GamePanel gamePanel) {

        init(gamePanel);
    }

    public void init(GamePanel gamePanel) {

        name = "Heart";

        BufferedImage bufferedImage = utility.loadImage("/objects/heart_full.png");
        BufferedImage bufferedImage2 = utility.loadImage("/objects/heart_half.png");
        BufferedImage bufferedImage3 = utility.loadImage("/objects/heart_blank.png");

        image = utility.scaleImage(bufferedImage, gamePanel.tileSize, gamePanel.tileSize);
        image2 = utility.scaleImage(bufferedImage2, gamePanel.tileSize, gamePanel.tileSize);
        image3 = utility.scaleImage(bufferedImage3, gamePanel.tileSize, gamePanel.tileSize);
    }
}
