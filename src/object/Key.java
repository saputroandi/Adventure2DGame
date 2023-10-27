package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Key extends Object {

    public Key() {

        init();
    }

    public void init() {

        name = "Key";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
        } catch ( IOException error ) {
            error.printStackTrace();
        }
    }
}
