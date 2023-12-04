package ai;

import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gamePanel;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, currentNode, goalNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    public void initNodes(){

        node = new Node[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;

        while ( col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow ){

            node[col][row] = new Node(col, row);

            col++;
            if ( col == gamePanel.maxWorldCol ){
                row++;
                col = 0;
            }
        }
    }

    public void resetNodes(){

        int col = 0;
        int row = 0;

        while ( col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow ){

            node[col][row].open = false;
            node[col][row].solid = false;
            node[col][row].checked = false;

            col++;
            if ( col == gamePanel.maxWorldCol ){
                row++;
                col = 0;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){

        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];

        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while ( col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow ){

            int tileNum = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][col][row];
            if ( gamePanel.tileManager.tiles[tileNum].collision ){
                node[col][row].solid = true;
            }

            for ( int i = 0; i < gamePanel.interactiveTiles[1].length; i++ ){
                if ( gamePanel.interactiveTiles[gamePanel.currentMap][i] != null && gamePanel.interactiveTiles[gamePanel.currentMap][i].destructible ){
                    int interactiveCol = gamePanel.interactiveTiles[gamePanel.currentMap][i].worldX / gamePanel.tileSize;
                    int interactiveRow = gamePanel.interactiveTiles[gamePanel.currentMap][i].worldY / gamePanel.tileSize;

                    node[interactiveCol][interactiveRow].solid = true;
                }
            }

            getCost(node[col][row]);

            col++;
            if ( col == gamePanel.maxWorldCol ){
                row++;
                col = 0;
            }
        }
    }

    public void getCost(Node node){


    }
}
