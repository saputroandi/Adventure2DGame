package main;

import entity.Entity;

public class EventHandler {

    GamePanel gamePanel;

    EventRect[][][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        init();
    }

    public void init() {

        eventRect = new EventRect[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while ( map < gamePanel.maxMap && col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow ) {

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if ( col == gamePanel.maxWorldCol ) {
                col = 0;
                row++;

                if ( row == gamePanel.maxWorldRow ) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {

        int distanceX = Math.abs(gamePanel.player.worldX - previousEventX);
        int distanceY = Math.abs(gamePanel.player.worldY - previousEventY);
        int distance = Math.max(distanceX, distanceY);

        if ( distance > gamePanel.tileSize ) {
            canTouchEvent = true;
        }

        if ( canTouchEvent ) {
            if ( hit(0, 23, 12, "any") ) {
                healingPool(gamePanel.dialogueState);
            } else if ( hit(0, 10, 39, "any") ) {
                teleport(1, 12, 13);
            } else if ( hit(1, 12, 13, "any") ) {
                teleport(0, 10, 39);
            } else if ( hit(1, 12, 9, "any") ) {
                speak(gamePanel.npc[1][0]);
            }
        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {

        boolean hit = false;

        if ( map == gamePanel.currentMap ) {
            gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
            gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

            eventRect[map][col][row].x = col * gamePanel.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gamePanel.tileSize + eventRect[map][col][row].y;

            if ( gamePanel.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone ) {
                if ( gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any") ) {
                    hit = true;

                    previousEventX = gamePanel.player.worldX;
                    previousEventY = gamePanel.player.worldY;
                }
            }

            gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
            gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    public void damagePit(int gameState) {

        gamePanel.gameState = gameState;
        gamePanel.userInterface.currentDialogue = "You fall into a pit";
        gamePanel.player.life -= 1;

//        eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int gameState) {

        if ( gamePanel.keyHandler.enterPressed ) {
            gamePanel.gameState = gameState;
            gamePanel.player.attackCancel = true;
            gamePanel.playSoundEffect(2);
            gamePanel.userInterface.currentDialogue = "Heal ur life and mana";
            gamePanel.player.life = gamePanel.player.maxLife;
            gamePanel.player.mana = gamePanel.player.maxMana;
        }
    }

    public void teleport(int map, int col, int row) {

        gamePanel.gameState = gamePanel.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gamePanel.playSoundEffect(13);
    }

    public void speak(Entity entity) {

        if ( gamePanel.keyHandler.enterPressed ) {
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.player.attackCancel = true;
            entity.speak();
        }
    }
}
