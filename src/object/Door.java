package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Door extends Object {

    public Door() {

        init();
    }

    public void init() {

        name = "Door";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));
        } catch ( IOException error ) {
            error.printStackTrace();
        }
    }
}
