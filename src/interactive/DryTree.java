package interactive;

import main.GamePanel;

import java.awt.*;

public class DryTree extends Tile {
    public DryTree(GamePanel gamePanel, int col, int row) {

        super(gamePanel, col, row);
        worldX = gamePanel.tileSize * col;
        worldY = gamePanel.tileSize * row;
        init();
    }

    public void init() {

        down1 = getScaledImage("/interactive-tiles/drytree", gamePanel.tileSize, gamePanel.tileSize);
        destructible = true;
        life = 4;
    }

    @Override
    public void playSoundEffect() {

        gamePanel.playSoundEffect(11);
    }

    @Override
    public Tile getDestroyedForm() {

        return new Trunk(gamePanel, worldX / gamePanel.tileSize, worldY / gamePanel.tileSize);
    }

    @Override
    public Color getParticleColor() {

        return new Color(65, 50, 30);
    }

    @Override
    public int getParticleSize() {

        return 6;
    }

    @Override
    public int getParticleSpeed() {

        return 1;
    }

    @Override
    public int getParticleMaxLife() {

        return 20;
    }
}
