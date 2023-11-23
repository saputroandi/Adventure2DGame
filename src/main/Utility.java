package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
        try ( InputStream inputStream = getClass().getResourceAsStream(imagePath) ) {
            if ( inputStream != null ) {
                originalImage = ImageIO.read(inputStream);
            } else {
                System.out.println("File not found: " + imagePath);
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return originalImage;
    }
}
