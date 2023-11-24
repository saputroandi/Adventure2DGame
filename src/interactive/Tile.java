package interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends Entity {

    public boolean destructible = false;

    public Tile(GamePanel gamePanel, int col, int row) {

        super(gamePanel);
    }

    public boolean isCorrectItem(Entity entity) {

        return entity.currentWeapon.type == typeAxe;
    }

    public void playSoundEffect() {

    }

    public Tile getDestroyedForm() {

        return null;
    }

    public void update() {

        if ( invisible ) {
            invisibleCounter++;
            if ( invisibleCounter > 20 ) {
                invisible = false;
                invisibleCounter = 0;
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {

        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if ( worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY ) {

            graphics2D.drawImage(down1, screenX, screenY, null);
        }
    }
}
