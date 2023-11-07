package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utility {

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaleImage = new BufferedImage(width, height, original.getType());

        Graphics2D graphics2D = scaleImage.createGraphics();
        graphics2D.drawImage(original, 0, 0, width, height, null);
        graphics2D.dispose();

        return scaleImage;
    }
}
