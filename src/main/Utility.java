package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Utility {

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaleImage = new BufferedImage(width, height, original.getType());

        Graphics2D graphics2D = scaleImage.createGraphics();
        graphics2D.drawImage(original, 0, 0, width, height, null);
        graphics2D.dispose();

        return scaleImage;
    }

    public BufferedImage loadImage(String imagePath) {

        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));

        } catch ( IOException error ) {
            error.printStackTrace();
        }

        return originalImage;
    }
}
