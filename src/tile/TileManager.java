package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    public Tile[] tiles;

    public int[][] mapTileNum;

    GamePanel gamePanel;

    public TileManager(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        tiles = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMaps("/maps/world01.txt");
    }

    public void getTileImage() {

        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tiles[1].collision = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth.png")));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tiles[4].collision = true;

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));
        } catch ( IOException error ) {
            error.printStackTrace();
        }
    }

    public void loadMaps(String mapPath) {

        try {
            InputStream resourceAsStream = getClass().getResourceAsStream(mapPath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(resourceAsStream)));

            int col = 0;
            int row = 0;

            while ( col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow ) {
                String line = bufferedReader.readLine();

                while ( col < gamePanel.maxWorldCol ) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if ( col == gamePanel.maxWorldCol ) {
                    col = 0;
                    row++;
                }
            }

            bufferedReader.close();

        } catch ( Exception error ) {
            error.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {

        int worldCol = 0;
        int worldRow = 0;

        while ( worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow ) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if ( worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                    && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                    && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                    && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY ) {
                graphics2D.drawImage(tiles[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
            worldCol++;

            if ( worldCol == gamePanel.maxWorldCol ) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
