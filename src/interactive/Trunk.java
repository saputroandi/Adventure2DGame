package interactive;

import main.GamePanel;

public class Trunk extends Tile {
    public Trunk(GamePanel gamePanel, int col, int row) {

        super(gamePanel, col, row);
        worldX = gamePanel.tileSize * col;
        worldY = gamePanel.tileSize * row;
        init();
    }

    public void init() {

        down1 = getScaledImage("/interactive-tiles/trunk", gamePanel.tileSize, gamePanel.tileSize);
        destructible = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
