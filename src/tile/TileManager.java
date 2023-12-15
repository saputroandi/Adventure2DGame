package tile;

import main.GamePanel;
import main.Utility;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {

    public Tile[] tiles;

    public int[][][] mapTileNum;
    public boolean drawPath = false;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    GamePanel gamePanel;

    public TileManager(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        init();
    }

    public void init() {

        try(InputStream inputStream = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ( ( line = bufferedReader.readLine()) != null ){

                fileNames.add(line);
                collisionStatus.add(bufferedReader.readLine());
            }
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }

        tiles = new Tile[fileNames.size()];
        getTileImage();

        try(InputStream inputStream = getClass().getResourceAsStream("/maps/worldmap.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line = bufferedReader.readLine();
            String[] maxTile = line.split(" ");

            gamePanel.maxWorldCol = maxTile.length;
            gamePanel.maxWorldRow = maxTile.length;

            mapTileNum = new int[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }


        loadMaps("/maps/worldmap.txt", 0);
        loadMaps("/maps/indoor01.txt", 1);
//        Tile tile = getTile("road00", false);
//        System.out.println(tile);

    }

    public void getTileImage() {

        for ( int i = 0; i < fileNames.size(); i++){
            String fileName = fileNames.get(i);
            boolean collision = false;

            if ( collisionStatus.get(i).equals("true") ){
                collision = true;
            }
            tiles[i] = getTile(fileName, collision);
        }

    }

    public Tile getTile(String ImageName, boolean collision) {

        Utility utility = new Utility();
        Tile tile = new Tile();

        tile.image = utility.loadImage("/tiles/" + ImageName);
        tile.image = utility.scaleImage(tile.image, gamePanel.tileSize, gamePanel.tileSize);
        tile.collision = collision;

        return tile;
    }

    public void loadMaps(String mapPath, int map) {

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

                    mapTileNum[map][col][row] = num;
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
            int tileNum = mapTileNum[gamePanel.currentMap][worldCol][worldRow];

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

        if ( drawPath ) {
            graphics2D.setColor(new Color(255, 0, 0, 70));

            for ( int i = 0; i < gamePanel.pathFinder.pathList.size(); i++ ) {

                int worldX = gamePanel.pathFinder.pathList.get(i).col * gamePanel.tileSize;
                int worldY = gamePanel.pathFinder.pathList.get(i).row * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                graphics2D.fillRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
            }
        }
    }
}
