package tile;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
        init();
    }

    public void init() {

        tiles = new Tile[50];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMaps("/maps/worldV2.txt");
//        Tile tile = getTile("road00", false);
//        System.out.println(tile);

    }

    public void getTileImage() {

//        placeholder
        tiles[0] = getTile("grass00", false);
        tiles[1] = getTile("grass00", false);
        tiles[2] = getTile("grass00", false);
        tiles[3] = getTile("grass00", false);
        tiles[4] = getTile("grass00", false);
        tiles[5] = getTile("grass00", false);
        tiles[6] = getTile("grass00", false);
        tiles[7] = getTile("grass00", false);
        tiles[8] = getTile("grass00", false);
        tiles[9] = getTile("grass00", false);

//        used tiles
        tiles[10] = getTile("grass00", false);
        tiles[11] = getTile("grass01", false);
        tiles[12] = getTile("water00", true);
        tiles[13] = getTile("water01", true);
        tiles[14] = getTile("water02", true);
        tiles[15] = getTile("water03", true);
        tiles[16] = getTile("water04", true);
        tiles[17] = getTile("water05", true);
        tiles[18] = getTile("water06", true);
        tiles[19] = getTile("water07", true);
        tiles[20] = getTile("water08", true);
        tiles[21] = getTile("water09", true);
        tiles[22] = getTile("water10", true);
        tiles[23] = getTile("water11", true);
        tiles[24] = getTile("water12", true);
        tiles[25] = getTile("water13", true);
        tiles[26] = getTile("road00", false);
        tiles[27] = getTile("road01", false);
        tiles[28] = getTile("road02", false);
        tiles[29] = getTile("road03", false);
        tiles[30] = getTile("road04", false);
        tiles[31] = getTile("road05", false);
        tiles[32] = getTile("road06", false);
        tiles[33] = getTile("road07", false);
        tiles[34] = getTile("road08", false);
        tiles[35] = getTile("road09", false);
        tiles[36] = getTile("road10", false);
        tiles[37] = getTile("road11", false);
        tiles[38] = getTile("road12", false);
        tiles[39] = getTile("earth", false);
        tiles[40] = getTile("wall", true);
        tiles[41] = getTile("tree", true);


    }

    public Tile getTile(String ImageName, boolean collision) {

        Utility utility = new Utility();
        Tile tile = new Tile();
        try {
            tile.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + ImageName + ".png")));
            tile.image = utility.scaleImage(tile.image, gamePanel.tileSize, gamePanel.tileSize);
            tile.collision = collision;

        } catch ( IOException error ) {
            error.printStackTrace();
        }

        return tile;
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
                graphics2D.drawImage(tiles[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if ( worldCol == gamePanel.maxWorldCol ) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
