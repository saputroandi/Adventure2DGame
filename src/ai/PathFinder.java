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
        initNodes();
    }

    public void initNodes() {

        node = new Node[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;

        while ( col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow ) {

            node[col][row] = new Node(col, row);

            col++;
            if ( col == gamePanel.maxWorldCol ) {
                row++;
                col = 0;
            }
        }
    }

    public void resetNodes() {

        int col = 0;
        int row = 0;

        while ( col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow ) {

            node[col][row].open = false;
            node[col][row].solid = false;
            node[col][row].checked = false;

            col++;
            if ( col == gamePanel.maxWorldCol ) {
                row++;
                col = 0;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {

        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];

        openList.add(currentNode);

        int col = 0;
        int row = 0;

        for ( int i = 0; i < gamePanel.interactiveTiles[1].length; i++ ) {
            if ( gamePanel.interactiveTiles[gamePanel.currentMap][i] != null && gamePanel.interactiveTiles[gamePanel.currentMap][i].destructible ) {
                int interactiveCol = gamePanel.interactiveTiles[gamePanel.currentMap][i].worldX / gamePanel.tileSize;
                int interactiveRow = gamePanel.interactiveTiles[gamePanel.currentMap][i].worldY / gamePanel.tileSize;

                node[interactiveCol][interactiveRow].solid = true;
            }
        }

        while ( col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow ) {

            int tileNum = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][col][row];
            if ( gamePanel.tileManager.tiles[tileNum].collision ) {
                node[col][row].solid = true;
            }

            getCost(node[col][row]);

            col++;
            if ( col == gamePanel.maxWorldCol ) {
                row++;
                col = 0;
            }
        }
    }

    public void getCost(Node node) {

        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {

        while ( !goalReached && step < 600 ) {

            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            if ( row - 1 >= 0 ) {
                openNode(node[col][row - 1]);
            }

            if ( col - 1 >= 0 ) {
                openNode(node[col - 1][row]);
            }

            if ( row + 1 < gamePanel.maxWorldRow ) {
                openNode(node[col][row + 1]);
            }

            if ( col + 1 < gamePanel.maxWorldCol ) {
                openNode(node[col + 1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            if ( openList.isEmpty() ) {
                break;
            }

            for ( int i = 0; i < openList.size(); i++ ) {

                if ( openList.get(i).fCost < bestNodeFCost ) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                } else if ( openList.get(i).fCost == bestNodeFCost && openList.get(i).gCost < openList.get(bestNodeIndex).gCost ) {
                    bestNodeIndex = i;
                }
            }

            currentNode = openList.get(bestNodeIndex);
            if ( currentNode == goalNode ) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node) {

        if ( !node.open && !node.checked && !node.solid ) {

            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath() {

        Node current = goalNode;

        while ( current != startNode ) {

            pathList.add(0, current);
            current = current.parent;
        }
    }
}
