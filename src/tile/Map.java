package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{

    BufferedImage[] worldMap;
    public boolean minimapOn = false;

    public Map(GamePanel gamePanel) {

        super(gamePanel);
        createWorldMap();
    }

    public void createWorldMap(){

        worldMap = new BufferedImage[gamePanel.maxMap];
        int worldMapWidth = gamePanel.tileSize * gamePanel.maxWorldCol;
        int worldMapHeight = gamePanel.tileSize * gamePanel.maxWorldRow;

        for ( int i = 0; i < gamePanel.maxMap; i++ ){

            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = worldMap[i].createGraphics();

            int col = 0;
            int row = 0;

            while ( col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow ){

                int tileNum = mapTileNum[i][col][row];
                int x = gamePanel.tileSize * col;
                int y = gamePanel.tileSize * row;

                graphics2D.drawImage(tiles[tileNum].image, x, y, null);

                col++;
                if ( col == gamePanel.maxWorldCol ){
                    col = 0;
                    row++;
                }
            }

            graphics2D.dispose();
        }
    }

    public void drawMinimap(Graphics2D graphics2D){

        if ( minimapOn ){

            int width = 200;
            int height = 200;
            int x = gamePanel.screenWidth - width - 50;
            int y = 50;

            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            graphics2D.drawImage(worldMap[gamePanel.currentMap], x, y, width, height, null);

            double scale = (double ) (gamePanel.tileSize * gamePanel.maxWorldCol) / width;
            int playerX = (int) (x + gamePanel.player.worldX / scale);
            int playerY = (int) (y + gamePanel.player.worldY / scale);
            int playerSize = gamePanel.tileSize / 3;
            graphics2D.drawImage(gamePanel.player.down1, playerX - 6, playerY - 6, playerSize, playerSize, null);

            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        }
    }
}
