package main;

public class EventHandler {

    GamePanel gamePanel;

    EventRect[][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        init();
    }

    public void init() {

        eventRect = new EventRect[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;

        while ( col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow ) {

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if ( col == gamePanel.maxWorldCol ) {
                col = 0;
                row++;
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
//            if ( hit(23, 16, "any") ) {
//                damagePit(23, 16, gamePanel.dialogueState);
//            }
            if ( hit(23, 12, "any") ) {
                healingPool(23, 12, gamePanel.dialogueState);
            }
        }
    }

    public boolean hit(int col, int row, String reqDirection) {

        boolean hit = false;

        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        eventRect[col][row].x = col * gamePanel.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gamePanel.tileSize + eventRect[col][row].y;

        if ( gamePanel.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone ) {
            if ( gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any") ) {
                hit = true;

                previousEventX = gamePanel.player.worldX;
                previousEventY = gamePanel.player.worldY;
            }
        }

        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int col, int row, int gameState) {

        gamePanel.gameState = gameState;
        gamePanel.userInterface.currentDialogue = "You fall into a pit";
        gamePanel.player.life -= 1;

//        eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int col, int row, int gameState){

        if ( gamePanel.keyHandler.enterPressed ){
            gamePanel.gameState = gameState;
            gamePanel.player.attackCancel = true;
            gamePanel.playSoundEffect(2);
            gamePanel.userInterface.currentDialogue = "Heal ur life and mana";
            gamePanel.player.life = gamePanel.player.maxLife;
            gamePanel.player.mana = gamePanel.player.maxMana;
        }
    }
}
