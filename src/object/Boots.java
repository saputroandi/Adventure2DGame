package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Boots extends Object {

    public Boots() {

        init();
    }

    public void init() {

        name = "Boots";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/boots.png")));
        } catch ( IOException error ) {
            error.printStackTrace();
        }
    }
}
