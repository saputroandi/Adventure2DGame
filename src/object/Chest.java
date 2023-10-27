package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends Object {

    public Chest() {

        init();
    }

    public void init() {

        name = "Chest";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
        } catch ( IOException error ) {
            error.printStackTrace();
        }
    }
}
